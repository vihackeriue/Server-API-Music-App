import pandas as pd
import requests
import os
import json
def download_file(url, save_path):
    try:
        response = requests.get(url, stream=True)
        if response.status_code == 200:
            with open(save_path, 'wb') as file:
                for chunk in response.iter_content(chunk_size=8192):
                    file.write(chunk)
            print(f"File downloaded: {save_path}")
            return True
        else:
            print(f"Failed to download file from {url}. Status code: {response.status_code}")
            return False
    except Exception as e:
        print(f"Exception occurred while downloading file from {url}: {e}")
        return False

def create_artist(api_endpoint, artist_info):
    token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJpYXQiOjE3MTg0NDE3NTksImV4cCI6MTcxODQ4NDk1OX0.trUySc08RRqd6j_jfAR0TaGUaOyD4_JaUvDo1UjAbuDLFO9wpGptiQ8HynT0f090_2_qgc7-gxOc0LpwY_wZjw'
    headers = {
        'token': token,
        'Content-Type': 'application/json'
        
    }
    try:
        response = requests.post(api_endpoint, headers=headers, data=json.dumps(artist_info))
        if response.status_code == 200:
            return response.json()
        else:
            print(f"Create artist failed. Status code: {response.status_code}")
            print("Response content:", response.content)
            return None
    except Exception as e:
        print(f"Exception occurred while creating artist: {e}")
        return None

def upload_song_json(api_endpoint, song_info):
    token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMUBnbWFpbC5jb20iLCJpYXQiOjE3MTg0NDE3NTksImV4cCI6MTcxODQ4NDk1OX0.trUySc08RRqd6j_jfAR0TaGUaOyD4_JaUvDo1UjAbuDLFO9wpGptiQ8HynT0f090_2_qgc7-gxOc0LpwY_wZjw'
    headers = {
        'token': token,
        'Content-Type': 'application/json'
    }
    try:
        response = requests.post(api_endpoint, headers=headers,  data=json.dumps(song_info))
        if response.status_code == 200:
            return response.json()
        else:
            print(f"Upload song failed. Status code: {response.status_code}")
            print("Response content:", response.content)
            return None
    except Exception as e:
        print(f"Exception occurred while uploading song: {e}")
        return None

def main():
    artist_api_endpoint = "http://localhost:8081/api/artist"  # Replace with your actual artist API endpoint
    song_api_endpoint = "http://localhost:8081/api/song/tool"  # Replace with your actual song API endpoint

    audio_dir = "data/music/audio/"
    image_dir = "data/music/image/"
    
    os.makedirs(audio_dir, exist_ok=True)
    os.makedirs(image_dir, exist_ok=True)

    try:
        # Step 1: Fetch data from API
        interactions_response = requests.get('https://thantrieu.com/resources/braniumapis/songs.json?fbclid=IwZXh0bgNhZW0CMTAAAR0mVBt6rDocPebomujLwFekXN2FeRRR3FOiu96tbpj_AFoIXcYSvjsXWXY_aem_AeZsz8dEB7FUKGo3PvYgjdsoBrDL_VLoteO4kT32hzPlvR_L1SSSf0nZtOx0foSujuqwgEH5UnHws1rBp-iSB_HT')

        if interactions_response.status_code == 200:
            data = interactions_response.json()
            songs = data.get('songs', [])
            songs_df = pd.DataFrame(songs)

            # Step 2: Create artist for each song
            for _, song in songs_df.iterrows():
                artist_info = {
                    "name": song.get('artist'),  # Assuming JSON has field 'artist_name'
                    "url_avatar": "/..",
                    "idUser": 1
                }
                created_artist = create_artist(artist_api_endpoint, artist_info)
                if created_artist:
                    artist_id = created_artist.get('id')
                    print("Create artist successful:", created_artist)
                else:
                    print(f"Failed to create artist for song with ID: {song.get('id')}")
                    continue

                # Step 3: Download audio file and image
                audio_url = song.get('source')
                image_url = song.get('image')

                audio_filename = os.path.join(audio_dir, os.path.basename(audio_url))
                image_filename = os.path.join(image_dir, os.path.basename(image_url))

                if not download_file(audio_url, audio_filename):
                    print(f"Failed to download audio for song with ID: {song.get('id')}")
                    continue

                if not download_file(image_url, image_filename):
                    print(f"Failed to download image for song with ID: {song.get('id')}")
                    continue

                # Step 4: Upload song JSON data to the new endpoint
                song_info = {
                    "title": song.get('title'),
                    "description": song.get('title'),
                    "url_thumbnail": image_filename,
                    "url_audio": audio_filename,
                    "genreID": 4,
                    "artistID": artist_id,
                    "views": 0
                }
                uploaded_song = upload_song_json(song_api_endpoint, song_info)
                if uploaded_song:
                    print("Upload song successful:", uploaded_song)
                else:
                    print(f"Failed to upload song with ID: {song.get('id')}")
        else:
            print(f"Failed to fetch data. Status code: {interactions_response.status_code}")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()
