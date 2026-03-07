# EcoQuest — Task Checklist

> Last updated: March 7, 2026

---

## ✅ DONE

| # | Task |
|---|---|
| 1 | Initialize Spring Boot project with Maven (`pom.xml`) |
| 2 | Configure `application.properties` (Supabase, JWT, CORS) |
| 3 | Set up Supabase PostgreSQL database |
| 4 | Create `User` JPA entity (id, name, email, password, points, created_at) |
| 5 | Create `UserRepository` with `findByEmail` and `existsByEmail` |
| 6 | Implement `UserService` with `UserDetailsService` |
| 7 | Implement BCrypt password encryption on register |
| 8 | Create `JwtUtil` — token generation and validation |
| 9 | Create `JwtAuthFilter` — per-request token validation |
| 10 | Configure `SecurityConfig` — stateless JWT security |
| 11 | Fix circular dependency in Spring Security config |
| 12 | Create `AuthController` — `/api/auth/register` and `/api/auth/login` |
| 13 | Create `UserController` — `/api/user/profile` GET and PUT |
| 14 | Create all DTOs (RegisterRequest, LoginRequest, AuthResponse, UserResponse, UpdateProfileRequest) |
| 15 | Initialize React project with Vite |
| 16 | Set up React Router v6 with all routes |
| 17 | Create Axios API service with JWT interceptor |
| 18 | Implement `AuthContext` for global auth state |
| 19 | Build `ProtectedRoute` component |
| 20 | Build `Navbar` component with logout |
| 21 | Build `Register` page with form validation |
| 22 | Build `Login` page with JWT storage |
| 23 | Build `Dashboard` page with eco points and quests display |
| 24 | Build `Profile` page with name update feature |
| 25 | Apply custom CSS styling (green eco theme, Syne font) |
| 26 | Test full registration flow (frontend → backend → database) |
| 27 | Test login and JWT authentication flow |
| 28 | Test protected routes (Dashboard, Profile) |
| 29 | Write `README.md` with full project documentation |
| 30 | Push project to GitHub (`IT342-Salleh-EcoQuest`) |
| 31 | Final commit — IT342 Phase 1 – User Registration and Login Completed |

---

## 🔄 IN PROGRESS

| # | Task | Notes |
|---|---|---|
| 32 | Set up Kotlin Android project in Android Studio | Folder structure planned |
| 33 | Build Android Login and Register screens | Depends on task 32 |
| 34 | Implement SharedPreferences for JWT on Android | Depends on task 32 |
| 35 | Connect Android app to Spring Boot backend | Use 10.0.2.2:8080 in emulator |

---

## 📋 TODO

### Mobile App (Kotlin + Jetpack Compose)
| # | Task |
|---|---|
| 36 | Build Android Home/Dashboard screen |
| 37 | Build Android Quests screen |
| 38 | Build Android Leaderboard screen |
| 39 | Build Android Profile screen |
| 40 | Add Jetpack Navigation (bottom navigation bar) |
| 41 | Implement Retrofit 2 for API calls |
| 42 | Test Android app on emulator (API 24+) |
| 43 | Test Android app on physical device |

### Backend Enhancements
| # | Task |
|---|---|
| 44 | Create `Quest` entity and table |
| 45 | Build Quest API endpoints (list, complete) |
| 46 | Implement points system — award points on quest completion |
| 47 | Create `QuestCompletion` entity and table |
| 48 | Build Leaderboard API endpoint |
| 49 | Add input validation error handling (global exception handler) |

### Frontend Enhancements
| # | Task |
|---|---|
| 50 | Add quest completion functionality on Dashboard |
| 51 | Show real-time points update after completing a quest |
| 52 | Build Leaderboard page |
| 53 | Add loading skeletons for better UX |
| 54 | Make the app fully responsive for mobile browsers |

---

## 📊 Progress Summary

| Status | Count |
|---|---|
| ✅ Done | 31 |
| 🔄 In Progress | 4 |
| 📋 Todo | 19 |
| **Total** | **54** |