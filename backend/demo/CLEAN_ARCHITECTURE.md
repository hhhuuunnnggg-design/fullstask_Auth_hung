# Clean Architecture - Hướng Dẫn Tổ Chức Code

## 📋 Mục Lục

1. [Tổng Quan](#tổng-quan)
2. [Cấu Trúc Layers](#cấu-trúc-layers)
3. [Dependency Rules](#dependency-rules)
4. [Chi Tiết Từng Layer](#chi-tiết-từng-layer)
5. [Flow Dữ Liệu](#flow-dữ-liệu)
6. [Ví Dụ Thực Tế](#ví-dụ-thực-tế)

---

## 🎯 Tổng Quan

Dự án này được tổ chức theo **Clean Architecture** (Kiến trúc Sạch) của Robert C. Martin, với mục tiêu:

- ✅ **Độc lập với Framework**: Business logic không phụ thuộc vào Spring, JPA, etc.
- ✅ **Testable**: Dễ dàng test từng layer độc lập
- ✅ **Maintainable**: Dễ bảo trì và mở rộng
- ✅ **Independent of UI**: Có thể thay đổi UI mà không ảnh hưởng business logic
- ✅ **Independent of Database**: Có thể thay đổi database mà không ảnh hưởng business logic

---

## 🏗️ Cấu Trúc Layers

```
backend/demo/src/main/java/com/example/demo/
│
├── domain/                    # 🟢 DOMAIN LAYER (Core Business Logic)
│   ├── entity/                # Domain Entities (Pure Java Objects)
│   │   ├── User.java
│   │   ├── Role.java
│   │   └── Permission.java
│   ├── port/                  # Ports (Interfaces - Dependency Inversion)
│   │   ├── UserRepository.java
│   │   ├── RoleRepositoryPort.java
│   │   └── PermissionRepositoryPort.java
│   ├── Enum/                  # Domain Enums
│   └── exception/             # Domain Exceptions
│
├── application/               # 🟡 APPLICATION LAYER (Use Cases)
│   ├── dto/                   # Data Transfer Objects
│   │   ├── request/           # Request DTOs (Input)
│   │   └── response/          # Response DTOs (Output)
│   ├── mapper/                # DTO Mappers (DTO ↔ Domain)
│   │   ├── UserDtoMapper.java
│   │   ├── RoleDtoMapper.java
│   │   └── PermissionDtoMapper.java
│   └── usecase/               # Use Cases (Business Logic)
│       ├── user/
│       ├── role/
│       ├── permission/
│       └── auth/
│
├── infrastructure/            # 🔵 INFRASTRUCTURE LAYER (External Concerns)
│   ├── config/                # Configuration
│   ├── persistence/           # Database Implementation
│   │   ├── adapter/           # Adapters (Implement Ports)
│   │   │   ├── UserRepositoryAdapter.java
│   │   │   ├── RoleRepositoryAdapter.java
│   │   │   └── PermissionRepositoryAdapter.java
│   │   ├── entity/            # JPA Entities (Database Mapping)
│   │   │   ├── UserEntity.java
│   │   │   ├── RoleEntity.java
│   │   │   └── PermissionEntity.java
│   │   ├── mapper/            # Entity Mappers (Domain ↔ JPA)
│   │   │   ├── UserEntityMapper.java
│   │   │   ├── RoleEntityMapper.java
│   │   │   └── PermissionEntityMapper.java
│   │   └── repository/        # JPA Repositories
│   │       ├── UserJpaRepository.java
│   │       ├── RoleJpaRepository.java
│   │       └── PermissionJpaRepository.java
│
└── presentation/             # 🔴 PRESENTATION LAYER (API Layer)
    └── controller/            # REST Controllers
        ├── UserController.java
        ├── RoleController.java
        ├── PermissionController.java
        └── AuthController.java
```

---

## 🔒 Dependency Rules

### Quy Tắc Vàng: **Dependency Inversion**

```
Presentation → Application → Domain ← Infrastructure
```

**Chi tiết:**

- ✅ **Domain** không phụ thuộc vào bất kỳ layer nào (Pure Java)
- ✅ **Application** chỉ phụ thuộc vào Domain (qua Ports)
- ✅ **Infrastructure** implement các Ports từ Domain
- ✅ **Presentation** chỉ phụ thuộc vào Application (Use Cases)

### Dependency Flow:

```
┌─────────────────────────────────────────┐
│  PRESENTATION LAYER                      │
│  (Controllers)                          │
│  ↓ depends on                            │
└─────────────────────────────────────────┘
           ↓
┌─────────────────────────────────────────┐
│  APPLICATION LAYER                       │
│  (Use Cases, DTOs)                      │
│  ↓ depends on                            │
└─────────────────────────────────────────┘
           ↓
┌─────────────────────────────────────────┐
│  DOMAIN LAYER                           │
│  (Entities, Ports)                      │
│  ← implemented by                       │
└─────────────────────────────────────────┘
           ↑
┌─────────────────────────────────────────┐
│  INFRASTRUCTURE LAYER                    │
│  (Adapters, JPA Entities)               │
└─────────────────────────────────────────┘
```

---

## 📚 Chi Tiết Từng Layer

### 🟢 1. DOMAIN LAYER (Core)

**Mục đích:** Chứa business logic thuần túy, không phụ thuộc framework.

#### Domain Entities (`domain/entity/`)

- **Pure Java Objects** (không có JPA annotations)
- Chứa business logic và validation
- **Ví dụ:** `User.java`, `Role.java`, `Permission.java`

```java
// domain/entity/User.java
public class User {
    Long id;
    String email;
    String password;
    Long roleId;  // Reference, không phải entity

    public boolean isBlocked() {
        return is_blocked != null && is_blocked;
    }
}
```

#### Ports (`domain/port/`)

- **Interfaces** định nghĩa contracts cho external services
- Domain định nghĩa "cần gì", Infrastructure implement "làm như thế nào"
- **Ví dụ:** `UserRepository.java`

```java
// domain/port/UserRepository.java
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    // ...
}
```

**✅ Đặc điểm:**

- Không import bất kỳ layer nào khác
- Framework-agnostic
- Dễ test (mock interfaces)

---

### 🟡 2. APPLICATION LAYER (Use Cases)

**Mục đích:** Chứa use cases và orchestration logic.

#### Use Cases (`application/usecase/`)

- Mỗi use case = 1 business operation cụ thể
- Sử dụng Ports để giao tiếp với Infrastructure
- **Ví dụ:** `CreateUserUseCase.java`

```java
@Service
public class CreateUserUseCase {
    private final UserRepository userRepository;  // Port, không phải implementation

    public ResCreateUserDTO execute(CreateUserRequest request) {
        // Business logic here
        User user = UserDtoMapper.toDomain(request);
        User saved = userRepository.save(user);
        return UserDtoMapper.toResCreateUserDTO(saved);
    }
}
```

#### DTOs (`application/dto/`)

- **Request DTOs:** Dữ liệu từ frontend
- **Response DTOs:** Dữ liệu trả về frontend
- **Ví dụ:** `CreateUserRequest.java`, `ResUserDTO.java`

#### DTO Mappers (`application/mapper/`)

- Chuyển đổi giữa **DTOs ↔ Domain Entities**
- **Naming:** `*DtoMapper` (ví dụ: `UserDtoMapper`)

```java
// application/mapper/UserDtoMapper.java
public class UserDtoMapper {
    // Request: DTO → Domain
    public static User toDomain(CreateUserRequest request) { ... }

    // Response: Domain → DTO
    public static ResUserDTO toResUserDTO(User user, Role role) { ... }
}
```

**✅ Đặc điểm:**

- Chỉ phụ thuộc Domain (qua Ports)
- Không phụ thuộc Infrastructure hay Presentation
- Dễ test (mock Ports)

---

### 🔵 3. INFRASTRUCTURE LAYER (External)

**Mục đích:** Implement các external concerns (Database, File Storage, etc.)

#### Adapters (`infrastructure/persistence/adapter/`)

- **Implement** các Ports từ Domain
- Chuyển đổi Domain ↔ Infrastructure entities
- **Ví dụ:** `UserRepositoryAdapter.java`

```java
@Component
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository jpaRepository;  // JPA Repository

    @Override
    public User save(User user) {
        UserEntity entity = UserEntityMapper.toEntity(user, roleRepo);
        UserEntity saved = jpaRepository.save(entity);
        return UserEntityMapper.toDomain(saved);
    }
}
```

#### JPA Entities (`infrastructure/persistence/entity/`)

- **JPA-annotated** entities cho database mapping
- **Ví dụ:** `UserEntity.java`

```java
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    RoleEntity role;  // JPA relationship
}
```

#### Entity Mappers (`infrastructure/persistence/mapper/`)

- Chuyển đổi giữa **Domain Entities ↔ JPA Entities**
- **Naming:** `*EntityMapper` (ví dụ: `UserEntityMapper`)

```java
// infrastructure/persistence/mapper/UserEntityMapper.java
public class UserEntityMapper {
    // Đọc DB: JPA → Domain
    public static User toDomain(UserEntity entity) { ... }

    // Ghi DB: Domain → JPA
    public static UserEntity toEntity(User domain, RoleJpaRepository roleRepo) { ... }
}
```

**✅ Đặc điểm:**

- Implement Ports từ Domain
- Có thể thay đổi implementation mà không ảnh hưởng Domain/Application
- Phụ thuộc vào JPA, Spring, etc.

---

### 🔴 4. PRESENTATION LAYER (API)

**Mục đích:** Xử lý HTTP requests/responses.

#### Controllers (`presentation/controller/`)

- Nhận HTTP requests
- Gọi Use Cases
- Trả về HTTP responses
- **Ví dụ:** `UserController.java`

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;  // Use Case

    @PostMapping("/add-user")
    public ResponseEntity<ResCreateUserDTO> createUser(
            @RequestBody CreateUserRequest request) {
        ResCreateUserDTO response = createUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}
```

**✅ Đặc điểm:**

- Chỉ phụ thuộc Application (Use Cases)
- Không chứa business logic
- Dễ thay đổi (REST → GraphQL, etc.)

---

## 🔄 Flow Dữ Liệu

### Flow Hoàn Chỉnh: Create User

```
1. Frontend Request
   POST /api/v1/users/add-user
   Body: { "email": "...", "password": "..." }
   ↓
2. Presentation Layer (UserController)
   - Nhận CreateUserRequest
   - Gọi CreateUserUseCase.execute()
   ↓
3. Application Layer (CreateUserUseCase)
   - Validate business rules
   - UserDtoMapper.toDomain(request) → User (domain)
   - Gọi UserRepository.save(user)
   ↓
4. Infrastructure Layer (UserRepositoryAdapter)
   - UserEntityMapper.toEntity(user) → UserEntity (JPA)
   - UserJpaRepository.save(entity) → Database
   - UserEntityMapper.toDomain(saved) → User (domain)
   ↓
5. Application Layer (CreateUserUseCase)
   - UserDtoMapper.toResCreateUserDTO(user) → ResCreateUserDTO
   ↓
6. Presentation Layer (UserController)
   - Trả về ResponseEntity<ResCreateUserDTO>
   ↓
7. Frontend Response
   { "id": 1, "email": "...", ... }
```

### Visual Flow:

```
Frontend
  ↓ (HTTP Request)
[Presentation] UserController
  ↓ (CreateUserRequest)
[Application] CreateUserUseCase
  ↓ (User domain entity)
[Infrastructure] UserRepositoryAdapter
  ↓ (UserEntity JPA)
[Database] MySQL/PostgreSQL
  ↑ (UserEntity)
[Infrastructure] UserRepositoryAdapter
  ↑ (User domain entity)
[Application] CreateUserUseCase
  ↑ (ResCreateUserDTO)
[Presentation] UserController
  ↑ (HTTP Response)
Frontend
```

---

## 💡 Ví Dụ Thực Tế

### Ví Dụ 1: Tạo User

**1. Frontend gửi request:**

```json
POST /api/v1/users/add-user
{
  "email": "user@example.com",
  "password": "123456",
  "firstName": "John",
  "lastName": "Doe"
}
```

**2. Controller nhận và gọi Use Case:**

```java
// presentation/controller/UserController.java
@PostMapping("/add-user")
public ResponseEntity<ResCreateUserDTO> createUser(
        @RequestBody CreateUserRequest request) {
    ResCreateUserDTO response = createUserUseCase.execute(request);
    return ResponseEntity.ok(response);
}
```

**3. Use Case xử lý business logic:**

```java
// application/usecase/user/CreateUserUseCase.java
public ResCreateUserDTO execute(CreateUserRequest request) {
    // Validate
    if (userRepository.existsByEmail(request.getEmail())) {
        throw new IdInvalidException("Email đã tồn tại");
    }

    // Convert DTO → Domain
    User user = UserDtoMapper.toDomain(request);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Save via Port (không biết implementation)
    User saved = userRepository.save(user);

    // Convert Domain → DTO
    return UserDtoMapper.toResCreateUserDTO(saved);
}
```

**4. Adapter implement Port:**

```java
// infrastructure/persistence/adapter/UserRepositoryAdapter.java
@Override
public User save(User user) {
    // Convert Domain → JPA Entity
    UserEntity entity = UserEntityMapper.toEntity(user, roleRepo);

    // Save to database
    UserEntity saved = jpaRepository.save(entity);

    // Convert JPA Entity → Domain
    return UserEntityMapper.toDomain(saved);
}
```

---

## 🎯 Lợi Ích Clean Architecture

### 1. **Testability**

- Domain và Application dễ test (không cần database)
- Mock Ports để test Use Cases

### 2. **Maintainability**

- Mỗi layer có trách nhiệm rõ ràng
- Dễ tìm và sửa bug

### 3. **Flexibility**

- Thay đổi database không ảnh hưởng business logic
- Thay đổi UI không ảnh hưởng business logic

### 4. **Scalability**

- Dễ thêm features mới
- Dễ refactor từng phần

---

## 📝 Best Practices

### 1. **Naming Conventions**

- **DTO Mappers:** `*DtoMapper` (ví dụ: `UserDtoMapper`)
- **Entity Mappers:** `*EntityMapper` (ví dụ: `UserEntityMapper`)
- **Use Cases:** `*UseCase` (ví dụ: `CreateUserUseCase`)
- **Adapters:** `*Adapter` (ví dụ: `UserRepositoryAdapter`)

### 2. **Dependency Rules**

- ✅ Domain không import gì ngoài domain
- ✅ Application chỉ import Domain
- ✅ Infrastructure implement Domain Ports
- ✅ Presentation chỉ import Application

### 3. **Mappers**

- **Application Mappers:** DTOs ↔ Domain
- **Infrastructure Mappers:** Domain ↔ JPA Entities
- **Không trộn lẫn:** Mỗi mapper có vai trò riêng

### 4. **Ports & Adapters Pattern**

- Domain định nghĩa **Ports** (interfaces)
- Infrastructure implement **Adapters**
- Dependency Inversion: Domain không phụ thuộc Infrastructure

---

## 🔍 Checklist Clean Architecture

- [x] Domain layer không phụ thuộc layer khác
- [x] Application layer chỉ phụ thuộc Domain
- [x] Infrastructure implement Domain Ports
- [x] Presentation chỉ phụ thuộc Application
- [x] Mappers được tách biệt rõ ràng (DtoMapper vs EntityMapper)
- [x] Use Cases độc lập với framework
- [x] Dễ test từng layer

---

## 📚 Tài Liệu Tham Khảo

- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture (Ports & Adapters)](https://alistair.cockburn.us/hexagonal-architecture/)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)

---

## ❓ FAQ

### Q: Tại sao cần 2 loại mapper (DtoMapper và EntityMapper)?

**A:**

- `DtoMapper`: Chuyển đổi giữa DTOs (frontend) và Domain (business logic)
- `EntityMapper`: Chuyển đổi giữa Domain và JPA Entities (database)
- Tách biệt để dễ maintain và test

### Q: Domain Entity và JPA Entity khác nhau như thế nào?

**A:**

- **Domain Entity:** Pure Java object, không có JPA annotations, chứa business logic
- **JPA Entity:** Có JPA annotations (@Entity, @Table, @ManyToOne), mapping với database

### Q: Ports và Adapters là gì?

**A:**

- **Ports:** Interfaces định nghĩa contracts (trong Domain)
- **Adapters:** Implementations của Ports (trong Infrastructure)
- Pattern này giúp Domain không phụ thuộc Infrastructure

---

**Tác giả:** Generated for Clean Architecture Documentation  
**Ngày cập nhật:** 2025
