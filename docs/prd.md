## 🐾 기본 요구사항

- 콘텐츠 추가
- 콘텐츠 목록 조회(반드시 페이징)
- 콘텐츠 상세 조회
- 콘텐츠 수정
- 콘텐츠 삭제

<br>

## 🐾 추가기능

- 콘텐츠 목록 조회 (관리자)
- 콘텐츠 조회수 증가
- 동일 사용자 24시간 조회수 중복 증가 방지
- Soft Delete 기반 게시글 삭제 처리
- 인증/인가 (JWT)
- 전역 예외 처리
- 전역 에러 코드
- API Docs (Swagger)
- DDD 기반 4계층 아키텍처 적용


<br>

## 🐾 MEMBER (회원)

**회원가입**

- 회원가입 시 `username`은 회원 아이디로 사용되며 중복될 수 없습니다.
- 중복된 `username`이 존재할 경우 회원가입이 불가능합니다.

**아이디(username) 규칙**

- 영문만 사용 가능
- 특수문자 사용 불가

**비밀번호(password) 규칙**

- 최소 8자 이상
- 대문자 + 소문자 + 특수문자 최소 1개 이상 포함

<br>

## 🐾 CONTENT (콘텐츠)

**접근 권한**

- 비회원은 게시글 조회가 불가능합니다.
- 게시글 조회는 인증된 사용자만 가능합니다.

**게시글 목록 조회**

- 일반 사용자 : 삭제된 게시글은 조회할 수 없습니다.
- 관리자 : 관리자 전용 API에서 삭제된 게시글까지 조회할 수 있습니다.

**게시글 상세 조회**

- 일반 사용자 : 삭제된 게시글은 조회할 수 없습니다.
- 관리자 : 삭제된 게시글도 내용 조회가 가능합니다.

**조회수 정책**

게시글 조회수는 다음 규칙을 따릅니다.

- 동일 사용자가 동일 게시글을 조회할 경우 24시간 동안 조회수는 1회만 증가합니다.
- 동일 사용자가 24시간 이후 다시 조회할 경우 조회수가 증가합니다.

**게시글 수정**

- 게시글 수정은 작성자만 가능합니다.

**게시글 삭제**

- 게시글 삭제는 작성자만 가능합니다.
- 관리자는 모든 게시글 삭제 및 수정이 가능합니다.

**삭제 정책 (Soft Delete)**

게시글 삭제는 Soft Delete 방식으로 구현되었습니다.

- 실제 데이터를 삭제하지 않고 논리 삭제 방식으로 처리합니다.

Base Entity 필드

- deletedAt : 삭제된 시점
- deletedBy : 삭제한 사용자

이 필드를 기준으로 삭제 여부를 판단합니다.

<br>

## 🚦 HTTP Status Codes

| Status | Description |
|------|-------------|
| 200 | 요청 성공 |
| 201 | 리소스 생성 성공 |
| 400 | 잘못된 요청 |
| 401 | 인증 필요 |
| 403 | 권한 없음 |
| 404 | 리소스 없음 |
| 409 | 중복 데이터 |
| 500 | 서버 내부 오류 |

<br>

## ⚠️ ErrorCode Enum

프로젝트에서는 예외 상황을 Enum 기반으로 관리하여 일관된 에러 응답을 반환하도록 설계했습니다.

ErrorCode는 다음 정보를 포함합니다.
- HttpStatus
- 에러 코드
- 사용자에게 전달할 메시지

```
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "DUPLICATE_USERNAME", "이미 존재하는 아이디입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
```
이 구조를 통해 다음과 같은 목차들이 가능했습니다.
- 에러 메시지를 중앙에서 관리
- 서비스 코드에서 일관된 예외 처리 가능
- API 응답 형식을 통일

<br>

## 🛠 Global Exception Handling

프로젝트에서는 @RestControllerAdvice를 사용하여 전역 예외 처리를 구현했습니다.

이를 통해 서비스 계층에서 발생한 예외를 일관된 API 응답 형식으로 변환합니다.

```
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleUserException(AppException e) {

        ErrorCode errorCode = e.getErrorCode();

        ApiResponse response = new ApiResponse(
            errorCode.getCode(),
            errorCode.getMessage()
        );

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
```

이 구조의 장점

- Controller 에서 try - catch 제거
- 예외 처리 로직 중앙 집중화
- API 응답 형식 일관성 유지

- - - 
