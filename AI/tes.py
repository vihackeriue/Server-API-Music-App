import pandas as pd
from sklearn.neighbors import NearestNeighbors
import requests
from flask import Flask, request, jsonify
import json
# Gọi API để lấy dữ liệu

interactions_response = requests.get('http://localhost:8081/api/song/interactions')


interactions = interactions_response.json()

# Chuyển đổi dữ liệu thành DataFrame

interactions_df = pd.DataFrame(interactions)

# Tạo dataset cho mô hình gợi ý
dataset = interactions_df[['userID', 'songID', 'rating', 'views']]

# Lưu dataset thành CSV để tiện sử dụng sau này
dataset.to_csv('music_data.csv', index=False)


data = pd.read_csv('music_data.csv')

# Dữ liệu ví dụ

df = pd.DataFrame(data)

# Xây dựng ma trận tiện ích
#utility_matrix = df.pivot_table(values='rating', index='user_id', columns='song_id', fill_value=0)

utility_matrix = data.pivot_table(index='userID', columns='songID', values='rating').fillna(0)

# Tạo mô hình KNN
model_knn = NearestNeighbors(metric='cosine', algorithm='brute')
model_knn.fit(utility_matrix.values)

def recommend_songs(user_id, n_recommendations=5):
    user_index = utility_matrix.index.tolist().index(user_id)
    distances, indices = model_knn.kneighbors([utility_matrix.iloc[user_index, :].values], n_neighbors=n_recommendations + 1)
    
    recommendations = []
    for i in range(1, len(distances.flatten())):
        song_id = utility_matrix.columns[indices.flatten()[i]]
        recommendations.append(song_id)
    
    return recommendations

def recommend_songs_with_views(user_id, n_recommendations=5):
    user_index = utility_matrix.index.tolist().index(user_id)
    distances, indices = model_knn.kneighbors([utility_matrix.iloc[user_index, :].values], n_neighbors=n_recommendations + 1)
    
    recommendations = []
    for i in range(1, len(distances.flatten())):
        song_id = utility_matrix.columns[indices.flatten()[i]]
        song_views = df[df['songID'] == song_id]['views'].mean()
        recommendations.append((song_id, song_views))
    
    # Sắp xếp gợi ý dựa trên lượt xem
    recommendations.sort(key=lambda x: -x[1])
    
    return [song_id for song_id, _ in recommendations]

# Gợi ý bài hát cho người dùng 1
print("Recommendations for user 1:")
print(recommend_songs(5, 3))

# Gợi ý bài hát cho người dùng 1 với số lượt xem
print("\nRecommendations for user 1 with views:")
print(recommend_songs_with_views(1, 3))

app = Flask(__name__)


@app.route('/recommend/<int:user_id>', methods=['GET'])
def recommend(user_id):
    try:
        if user_id not in utility_matrix.index:
            return jsonify({'error': 'User ID not found'}), 404

        # Lấy đề xuất bài hát cho người dùng
        recommendations_rate = recommend_songs(user_id, 2)
        #recommended_songs_rate = recommendations_rate.astype(int)

        recommendations_view = recommend_songs_with_views(user_id, 2)
        #recommended_songs_view = recommendations_view.astype(int)

        recommended_songs_rate = [int(song_id) for song_id in recommendations_rate]
        recommended_songs_view = [int(song_id) for song_id in recommendations_view]

        recommend = {
            'user_id': user_id,
            'recommended_songs_rate': recommended_songs_rate,
            'recommended_songs_view': recommended_songs_view
        }
        
        return jsonify(recommend)

    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)