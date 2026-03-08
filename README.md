# CMS REST API

해당 프로젝트는 Spring Boot 기반 REST API 서버로, 사용자 인증을 포함한 간단한 콘텐츠 관리 기능을 구현한 백엔드 서비스입니다.

프로젝트 설계 과정에서 유지보수성과 확장성을 고려하여 DDD 기반 4계층 아키텍처를 적용하였으며,

인증/인가에는 Spring Security 와 JWT 기반 토큰 인증 방식을 사용했습니다.

또한 데이터 접근 계층에서는 JPA와 QueryDSL을 함께 사용하여 조회 로직을 구현하였습니다.

**주요 목표**

- Spring Boot 기반 REST API 설계
- Spring Security + JWT 인증/인가 구조 구현
- DDD 기반 4계층 아키텍처 적용
- JPA + QueryDSL 을 활용한 데이터 조회 구조 설계
- 콘텐츠 조회 성능 및 유지보수성을 고려한 Repository 구조 설계

<br>

# ⚙️ 기술 스택
- Java 25
- Spring Boot 4.0.3
- Spring Security
- Spring Data JPA
- QueryDSL
- H2 Database
- Lombok
- Jwt

<br>

# 🧩 프로젝트 실행 방법

### 프로젝트 실행
```
./gradlew bootRun


./gradlew build
java -jar build/libs/*.jar
```
### H2 Console 접속
```
http://localhost:8080/h2-console.html
```

### 초기 데이터
| 아이디 | 비밀번호 | 권한 |
| --- | --- | --- |
| admin | test1234 | ADMIN |
| user | test1234 | USER |


<br>

# 🔧 Package Structure
Domain Driven Design(DDD) 기반 4계층 아키텍처를 적용하여 설계하였습니다.

도메인 중심으로 패키지를 분리하고, 각 계층의 역할을 명확히 구분하여 비즈니스 로직의 응집도를 높이고 기술 의존성을 분리 하는 것을 목표로 잡았습니다.

DDD 구조를 적용함으로써 다음과 같은 장점을 얻을 수 있습니다.
- **비즈니스 로직을 도메인 중심으로 관리** 가능
- **기술 구현(JPA, QueryDSL등)과 도메인 로직 분리**
- 서비스 확장 시 **변경 영향 범위 최소화**

프로젝트는 Presentation -> Application -> Domain -> Infrastructure 의 4계층 구조로 구성했습니다.

| Layer | 역할 |
| --- | --- | 
| Presentation | Controller, Request/Response DTO |
| Application| Service, 트랜잭션 관리 |
| Domaion | Entity, Repository Interface, ErrorCode | 
| Infrastructure | JPA, QueryDSL, Repository 구현|

### Repository 설계
Repository 는 도메인 의존성을 낮추기 위해 역할별로 분리했습니다.

**<u>Domain Repository</u>**
```
# 도메인 계층에서 필요한 기능만 인터페이스로 정의
ContentRepository
```

<u>**Infrastructure Repository**</u>
```
ContentJpaRepository     # Spring Data JPA 기반 기본 CRUD 및 단순 조회
ContentQueryRepository   # 동적 조건 조회, 페이징, 복잡한 조회 
ContentRepositoryImpl    # 도메인 Repository 인터페이스를 구현하며 JPA, QueryDSL를 조합하여 실제 데이터 접근을 수행
```

#### 이렇게 분리한 이유
> - 도메인 계층이 Spring Data JPA에 직접 의존하지 않도록 하기 위해,
> - 기술 구현(JPA/QueryDSL)을 인프라 계층으로 분리하기 위해
> - 단순 CRUD와 복잡 조회의 책임을 나누기 위해
> - 유지보수 시 변경 범위를 줄이기 위해


### Package

```
src/main/java
└── com
    └── malgn
        ├── Application.java
        ├── content
        │   ├── application
        │   │   └── service
        │   │       └── ContentService.java
        │   ├── domain
        │   │   ├── ContentErrorCode.java
        │   │   ├── entity
        │   │   │   ├── Content.java
        │   │   │   └── ContentView.java
        │   │   └── repository
        │   │       ├── ContentRepository.java
        │   │       └── ContentViewRepository.java
        │   ├── infrastructure
        │   │   └── repository
        │   │       ├── ContentJpaRepository.java
        │   │       ├── ContentQueryRepository.java
        │   │       ├── ContentRepositoryImpl.java
        │   │       ├── ContentViewJpaRepository.java
        │   │       ├── ContentViewQueryRepository.java
        │   │       └── ContentViewRepositoryImpl.java
        │   └── presentation
        │       ├── controller
        │       │   └── ContentController.java
        │       └── dto
        │           ├── request
        │           │   ├── ContentCreateRequest.java
        │           │   └── ContentUpdateRequest.java
        │           └── response
        │               ├── ContentCreateResponse.java
        │               ├── ContentDetailResponse.java
        │               ├── ContentResponse.java
        │               └── ContentUpdateResponse.java
        ├── global
        │   ├── configure
        │   │   ├── AppConfiguration.java
        │   │   ├── CustomUser.java
        │   │   ├── CustomUserDetailsService.java
        │   │   ├── JpaAuditingConfig.java
        │   │   ├── QueryDslConfig.java
        │   │   ├── SecurityAuditorAware.java
        │   │   └── SwaggerConfig.java
        │   ├── entity
        │   │   ├── AuditingEntity.java
        │   │   └── BaseEntity.java
        │   ├── exception
        │   │   ├── ApiErrorCode.java
        │   │   ├── ApiResponse.java
        │   │   ├── AppException.java
        │   │   ├── ErrorCode.java
        │   │   ├── ErrorResponse.java
        │   │   └── GlobalExceptionHandler.java
        │   └── security
        │       ├── ActuatorSecurityConfiguration.java
        │       ├── AuthenticationFilter.java
        │       ├── FromLoginRequest.java
        │       ├── H2DbSecurityConfiguration.java
        │       ├── jwt
        │       │   ├── JsonAccessDeniedHandler.java
        │       │   ├── JsonAuthenticationEntryPoint.java
        │       │   ├── JwtAuthenticationFilter.java
        │       │   └── JwtTokenProvider.java
        │       ├── SecurityAuditorAware.java
        │       └── SecurityConfiguration.java
        └── member
            ├── application
            │   └── service
            │       └── AuthService.java
            ├── domain
            │   ├── entity
            │   │   ├── Member.java
            │   │   └── MemberRole.java
            │   ├── MemberErrorCode.java
            │   └── repository
            │       └── MemberRepository.java
            ├── infrastructure
            │   └── repository
            │       ├── MemberJpaRepository.java
            │       ├── MemberQueryRepository.java
            │       └── MemberRepositoryImpl.java
            └── presentation
                ├── controller
                │   ├── AuthController.java
                │   └── MemberController.java
                └── dto
                    ├── request
                    │   └── MemberCreateRequest.java
                    └── response
                        └── MemberCreateResponse.java
```

<br>

# 🔑 인증 / 인가
- JWT 기반 Stateless 인증 방식을 사용합니다.
- Spring Security 의 인증 흐름을 활용하기 위해 로그인 요청을 Controller가 아닌 Filter에서 처리하도록 설계했습니다.

### JWT를 선택한 이유

Jwt는 서버에 세션을 저장하지 않는 stateless 인증 방식이라 서버 확장에 유리하고, Authorization 헤더 기반으로 전달되기 때문에

Rest API나 MSA 환경에서 서비스 간 인증 전달이 쉽다는 장점이 있어서 선택했습니다.


### 인증 API
| 기능 | Method | Endpoint |
| --- | --- | --- |
| 회원가입 | POST | api/v1/auth/sign-up |
| 로그인 | POST | api/v1/auth/sign-in |

### 인증 처리 흐름

**회원 가입**
```
Client
 ↓
AuthController
 ↓
AuthService
 ↓
MemberRepository
 ↓
DB
```

**로그인(Filter 기반 인증)**
```
Client
 ↓
Security Filter Chain
 ↓
Authentication Filter
 ↓
AuthenticationManager
 ↓
UserDetailsService
 ↓
DB
```

### JWT 인증 흐름
로그인 이후 요청은 JWT 토큰 기반으로 인증됩니다.
```
Client
 ↓
API Request + JWT
 ↓
JWT Filter
 ↓
토큰 검증
 ↓
SecurityContext 인증 저장
 ↓
Controller
```

**인증 과정**

1.  사용자가 로그인하면 서버가 JWT토큰을 발급합니다.
2. 이후 API 요청 시 HTTP Header에 토큰을 포함합니다.

```
Authorization: Bearer {JWT}
```

3. JWT Filter에서 토큰을 검증합니다.
4. 검증이 성공하면 SecurityContext에 인증 정보를 저장합니다.
5. 인증된 사용자로 Controller 로직이 실행됩니다.

### 보안 적용 사항
- Spring Security 기반 인증 처리
- JWT 토큰 기반 Stateless 인증 방식
- 비밀번호 BCryptPasswordEncoder 암호화 저장
- Security Filter Chain을 통한 인증 처리

<br>

# 📒 API & PRD Docs

[API Docs Click Me !](https://github.com/changhui98/task.cms-api.backend/blob/main/docs/api-docs.md)



<br>

# 👤 AI & Reference
ChatGPT 5.3 | 설계 방향성 확인 및 기존 코드 리팩토링

delivery-management https://github.com/E-driven-idle/delivery-management | Reference

gabojago-logistics https://github.com/GBG-Gaboja-Go/gabojago-logistics | Reference
