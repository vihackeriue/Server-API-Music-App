
## API

#### Recomend song

```http
  http://localhost:8081/api/song/recommend
```
#### request

| header | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `token` | `string` | **Required**. Your API key |

#### response
| body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `recommendedSongsRate` | `List<Song>` | [{song1}, {song2}, {song3}] |
| `recommendedSongsView` | `List<Song>` | [{song1}, {song2}, {song3}] |

#### increaseView

```http
  http://localhost:8081/api/song/{id}
```
#### request
| header | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `token`      | `string` | **Required** |

#### response



 
 #### Server-API-Music-App

#### 1.database

#### mở mysql -> tạo 1 database tên 'music_app'-> config trong resources/application.properties -> đổi 'spring.jpa.hibernate.ddl-auto=none' sang 'spring.jpa.hibernate.ddl-auto=create'để tạo database và none khi đã tạo

#### API
#### Genre
#### GET /api/genre: Lấy danh sách thể loại.
#### GET /api/genre?page= 1&limit= 2: Lấy danh sách theo phân trang
#### POST /api/genre: thêm dữ liệu
#### PUT /api/genre/id: Cập nhật thông tin theo id.
#### DELETE /api/genre: Xóa nhiều genre dựa trên mảng ID.
[Doc API](https://docs.google.com/document/d/1-_9_M78e4PWEmv-6XJbai7f_JmMI1DaOv4NGQ_jam7U/edit?usp=sharing)
#### Song
#### GET /api/song: Lấy danh sách bài hát.
#### GET /api/song?page= 1&limit= 2: Lấy danh sách theo phân trang
#### POST /api/song: thêm dữ liệu
#### PUT /api/song/id: Cập nhật thông tin theo id.
#### DELETE /api/song: Xóa nhiều song dựa trên mảng ID.

