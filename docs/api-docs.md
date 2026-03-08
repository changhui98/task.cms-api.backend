## 🔑 Auth Service API

**회원가입 :: POST /api/v1/auth/sign-up**

```
REQUEST

{
  "username": "changhee",
  "password": "testUser!"
}

```

```
RESPONSE

201 CREATED

{
  "id": 1,
  "username": "changhee",
  "createdAt": "2026-03-07T20:39:58.267153"
}

400 BAD REQUEST

{
  "errorCode": "U002",
  "message": "이미 존재하는 아이디입니다."
}

{
  "code": "VALIDATION_ERROR",
  "message": "username은 영어만 입력 가능합니다."
}

{
  "code": "VALIDATION_ERROR",
  "message": "password는 최소 8자 이상이어야 합니다."
}

{
  "code": "VALIDATION_ERROR",
  "message": "password는 대문자, 소문자, 특수문자를 각각 하나 이상 포함해야 합니다."
}

```

**로그인 :: POST /api/v1/auth/sign-in**
```
REQUEST

{
 "username" : "changhee",
 "password" : "testUser!"
}
```

```
RESPONSE

200 OK { }

400 BAD REQUEST

{
  "code": "U004",
  "message": "아이디 또는 비밀번호가 올바르지 않습니다."
}

```



## 🖥️ Content Service API


**콘텐츠 생성 :: POST api/v1/contents**

```
REQUEST

{
 "title" : "테스트 게시글 입니다.",
 "description" : "테스트 게시글 입니다."
}
```

```
RESPONSE

201 CREATED

{
    "id": 2,
    "title": "테스트 게시글 입니다.",
    "description": "테스트 게시글 입니다.",
    "createdBy": "changhee",
    "createdAt": "2026-03-08T17:18:17.093625",
    "updatedBy": "changhee",
    "updatedAt": "2026-03-08T17:18:17.093633"
}

400 BAD REQUEST

{
    "code": "VALIDATION_ERROR",
    "message": "제목은 필수 입니다."
}

{
    "code": "VALIDATION_ERROR",
    "message": "내용은 필수 입니다."
}

```
