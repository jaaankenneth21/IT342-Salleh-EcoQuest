# 🌿 EcoQuest

EcoQuest is a full-stack web and mobile application that encourages users to adopt eco-friendly habits by completing environmental challenges and earning eco points. Users can register, log in, track their points, and manage their profile.

---

## 🛠 Technologies Used

### Backend
| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.5.11 | Backend framework |
| Spring Security | Authentication and authorization |
| JSON Web Token (JWT) | Stateless auth tokens |
| Spring Data JPA / Hibernate | ORM and database operations |
| BCrypt | Password encryption |
| Supabase (PostgreSQL) | Cloud relational database |
| Maven | Dependency management and build tool |

### Frontend (Web)
| Technology | Purpose |
|---|---|
| React 18 | UI framework |
| Vite | Frontend build tool |
| React Router v6 | Client-side routing |
| Axios | HTTP client for API calls |
| React Context API | Global auth state management |
| CSS3 | Custom styling |

### Mobile
| Technology | Purpose |
|---|---|
| Kotlin | Programming language |
| Jetpack Compose | Android UI framework |
| Retrofit 2 | API calls to backend |
| SharedPreferences | JWT token persistence |
| Jetpack Navigation | Mobile screen navigation |

---

## 📁 Project Structure
```
ecoquest/
├── src/main/java/edu/cit/salleh/ecoquest/
│   ├── config/            ← Security configuration
│   ├── controller/        ← REST controllers
│   ├── dto/               ← Request/Response objects
│   ├── entity/            ← JPA entities
│   ├── repository/        ← Database repositories
│   ├── security/          ← JWT filter and utility
│   └── service/           ← Business logic
├── frontend/              ← React web app
│   └── src/
│       ├── components/    ← Reusable components
│       ├── context/       ← Auth context
│       ├── pages/         ← Page components
│       └── services/      ← Axios API service
└── README.md
```

---

## 🗄️ Database Setup

1. Go to **https://supabase.com** and sign up for free
2. Click **New Project** and name it **ecoquest**
3. Set a database password and save it somewhere safe
4. Go to **SQL Editor** and run this to add the created_at column:
```sql
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT NOW();
```
5. Go to **Table Editor** to verify the users table

---

## 🚀 Steps to Run the Backend

### 1. Configure Supabase Credentials
Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?user=postgres.yourprojectid&password=YOUR_PASSWORD
spring.datasource.username=postgres.yourprojectid
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.application.name=ecoquest

jwt.secret=ecoquest_jwt_secret_key_must_be_at_least_32_characters_long
jwt.expiration=86400000
```

### 2. Run the Backend
```bash
mvn clean package -DskipTests
java -jar target/ecoquest-0.0.1-SNAPSHOT.jar
```

### 3. Verify
Wait until you see:
```
Started EcoQuestApplication on port 8080
```
The backend API is now running at: **http://localhost:8080**

---

## 🌐 Steps to Run the Web App

> Make sure the backend is already running first!
```bash
cd frontend
npm install
npm run dev
```

Open your browser and go to: **http://localhost:5173**

You should see the EcoQuest Login page. Click **Create one** to register and start using the app.

---

## 📱 Steps to Run the Mobile App

> Make sure the backend is already running first!

### 1. Open the project in Android Studio

### 2. Update the API Base URL
In the Retrofit configuration set the base URL to:
```
http://10.0.2.2:8080/api
```
> Use 10.0.2.2 instead of localhost when running on Android emulator

### 3. Run on Emulator
- Open Android Studio
- Start an Android emulator (API 24 or higher)
- Click the Run button

---

## 📡 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Auth Endpoints (Public)

| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| `POST` | `/auth/register` | Register a new user | `{ name, email, password }` |
| `POST` | `/auth/login` | Login and receive JWT token | `{ email, password }` |

#### Example: Register
```json
POST /api/auth/register
{
  "name": "Kenn Salleh",
  "email": "kenn@example.com",
  "password": "secret123"
}
```

#### Example: Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "name": "Kenn Salleh",
  "email": "kenn@example.com",
  "points": 0
}
```

---

### User Endpoints (Protected — requires JWT)

> Include this header in all requests:
> `Authorization: Bearer <your_jwt_token>`

| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| `GET` | `/user/profile` | Get current user's profile | None |
| `PUT` | `/user/profile` | Update user's name | `{ name }` |

#### Example: Get Profile Response
```json
{
  "id": 1,
  "name": "Kenn Salleh",
  "email": "kenn@example.com",
  "points": 0
}
```

---

## 🔐 How Authentication Works
```
1. User registers or logs in
2. Backend validates credentials against Supabase database
3. Backend returns a JWT token (valid 24 hours)
4. Frontend stores token in localStorage (web) or SharedPreferences (Android)
5. Every protected request includes: Authorization: Bearer <token>
6. Backend JwtAuthFilter validates the token on each request
7. If token is invalid or expired → 401 Unauthorized → redirect to login
```

---

## 👤 Default User Entity

| Field | Type | Description |
|---|---|---|
| `id` | Long | Auto-generated primary key |
| `name` | String | User's full name |
| `email` | String | Unique email address |
| `password` | String | BCrypt-hashed password |
| `points` | Integer | Eco points (default: 0) |
| `created_at` | Timestamp | Account creation date |