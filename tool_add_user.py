import requests
import random
import string

API_URL = 'http://localhost:8081/api/register'  # Thay thế bằng URL API thực tế của bạn

def generate_random_user():
    random_email = ''.join(random.choices(string.ascii_lowercase + string.digits, k=8)) + '@gmail.com'
    random_full_name = 'Nguyen vi ' + str(random.randint(1, 100))
    random_phone_number = ''.join(random.choices(string.digits, k=7))
    random_url_avatar = 'data/user/image/anhAvatar.jpg'
    random_user_preferences = random.choice(['Pop', 'Rock', 'RnB', 'Classical'])
    random_status = random.choice([True, False])

    user_data = {
        'email': random_email,
        'password': '12345',
        'fullName': random_full_name,
        'phoneNumber': random_phone_number,
        'urlAvatar': random_url_avatar,
        'userPreferences': random_user_preferences,
        'status': random_status
    }

    return user_data

def add_random_user():
    user_data = generate_random_user()

    try:
        response = requests.post(API_URL, json=user_data)
        if response.status_code == 200:
            print("User added successfully!")
            print(response.json())  # In thông tin người dùng được trả về từ API (nếu có)
        else:
            print(f"Failed to add user. Status code: {response.status_code}")
            print(response.text)  # In thông tin lỗi từ phản hồi API (nếu có)
    except requests.exceptions.RequestException as e:
        print(f"An error occurred: {e}")

# Example usage:
if __name__ == "__main__":
    for _ in range(100):
        add_random_user()
