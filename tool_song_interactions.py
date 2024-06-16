import requests
import random

API_URL = 'http://localhost:8081/api/song/rate'  # Thay thế bằng URL API thực tế của bạn

def add_random_rating():
    # Tạo dữ liệu ngẫu nhiên
    rating = random.randint(0, 5)
    user_id = random.randint(1, 109)  # Giả sử userID từ 1 đến 1000
    song_id = random.randint(12, 137)  # Giả sử songID từ 1 đến 1000
    views = random.randint(0, 10000)

    rating_data = {
        'rating': rating,
        'userID': user_id,
        'songID': song_id,
    }
    headers = {
        'token':'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJpYXQiOjE3MTg0NDE3NTksImV4cCI6MTcxODQ4NDk1OX0.trUySc08RRqd6j_jfAR0TaGUaOyD4_JaUvDo1UjAbuDLFO9wpGptiQ8HynT0f090_2_qgc7-gxOc0LpwY_wZjw'
    }
    try:
        response = requests.post(API_URL,headers=headers, json=rating_data)
        if response.status_code == 200:
            print(f"Rating added successfully: {rating_data}")
        else:
            print(f"Failed to add rating. Status code: {response.status_code}")
            print(response.text)
    except requests.exceptions.RequestException as e:
        print(f"An error occurred: {e}")

# Thêm 10 ratings ngẫu nhiên
if __name__ == "__main__":
    for _ in range(500):
        add_random_rating()
