# 📚 Manga Vision

**Manga Vision** is a modern Android app to explore manga with **offline access**, **paginated content**, and **face recognition unlocking**. Built using **Jetpack Compose**, **Room**, **Paging 3**, and **MediaPipe** – all following Google's **Clean Architecture** guidelines.

---

## 🎯 App Functionality

### 🔐 Authentication Screen  
- Secure RoomDB-based local login  
- Offline-first user management  
![Auth Screen](https://your-image-link.com/auth.png)

### 📖 Manga Screen (Home)  
- Paginated manga list via **Paging 3**  
- Works **offline** (page 1 data + images cached)  
- Displays thumbnail, title, status, and chapters  
![Home Screen](https://your-image-link.com/home.png)

### 👤 Face Detection Screen  
- Uses **front camera** with **MediaPipe** for live face tracking  
- Green box when face detected inside target area  
- Integrated into bottom navigation  
![Face Detection](https://your-image-link.com/face.png)

### 📄 Manga Detail Screen  
- Shows full summary, image, and metadata  
- Opens from the home screen on tap  
![Detail Screen](https://your-image-link.com/detail.png)

---

## 🎥 Demo Video  
▶ [Watch App Demo](https://your-video-link.com/demo.mp4)

---

## 🚀 Features

- ✅ Offline-ready with Room DB and image pre-caching
- 📶 Auto-sync when network is back
- 🔐 RoomDB local authentication
- 🔁 Pagination with **Paging 3**
- 📷 Face detection via **MediaPipe + CameraX**
- 💡 Built with **Jetpack Compose + Hilt DI**
- 🧱 Scalable **MVVM Clean Architecture**

---

## 🧰 Tech Stack

- **UI:** Jetpack Compose, Material 3  
- **DB & Storage:** Room (first-page cache)  
- **Networking:** Retrofit, Gson, Coroutines  
- **Pagination:** Paging 3 (local + remote source)  
- **AI/Camera:** CameraX + MediaPipe (Face Detection)  
- **Image Handling:** Coil (with custom caching)  
- **Architecture:** MVVM + Clean Architecture  
- **DI:** Hilt

---

## 🧑‍💻 Author

**Ashim Khan** – Android Developer  
