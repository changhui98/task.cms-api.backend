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

**콘텐츠 목록 조회(삭제된 게시글 조회 X) :: GET /api/v1/contents?page=0&size=5**

**콘텐츠 목록 조회(삭제된 게시글 조회 O) :: GET /api/v1/contents/admin?page=0&size=5**

```
QUERY PARAMETERS
page = 0 
size = 5 

# 쿼리파라미터 값 주지 않은 경우 기본값 page 0, size 10으로 기본 페이징 처리 되어 있음.

RESPONSE 

200 OK 

{
    "content": [
        {
            "id": 30,
            "title": "테스트 데이터입니다.",
            "createdBy": "changhee",
            "viewCount": 0,
            "createdDate": "2026-03-08T17:23:13.272586"
        },
        {
            "id": 29,
            "title": "테스트 데이터입니다.",
            "createdBy": "changhee",
            "viewCount": 0,
            "createdDate": "2026-03-08T17:23:13.079948"
        },
        {
            "id": 28,
            "title": "테스트 데이터입니다.",
            "createdBy": "changhee",
            "viewCount": 0,
            "createdDate": "2026-03-08T17:23:12.933827"
        },
        {
            "id": 27,
            "title": "테스트 데이터입니다.",
            "createdBy": "changhee",
            "viewCount": 0,
            "createdDate": "2026-03-08T17:23:12.781426"
        },
        {
            "id": 26,
            "title": "테스트 데이터입니다.",
            "createdBy": "changhee",
            "viewCount": 0,
            "createdDate": "2026-03-08T17:23:12.669513"
        }
    ],
    "empty": false,
    "first": true,
    "last": false,
    "number": 0,
    "numberOfElements": 5,
    "pageable": {
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 5,
        "paged": true,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "unpaged": false
    },
    "size": 5,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "totalElements": 29,
    "totalPages": 6
}
```

**콘텐츠 상세 조회 :: GET /api/v1/content/{contentId}**

```
REQUEST

PathVariable : 1 
```

```
RESPONSE

200 OK 

{
    "id": 2,
    "title": "테스트 게시글 입니다.",
    "description": "테스트 게시글 입니다.",
    "viewCount": 1,
    "createdBy": "changhee",
    "createdAt": "2026-03-08T17:18:17.093625",
    "updatedBy": "changhee",
    "updatedAt": "2026-03-08T17:18:17.093633"
}

404 NOT FOUND

{
    "errorCode": "C001",
    "message": "조회하려는 게시글이 존재하지 않습니다."
}

```

**콘텐츠 수정 :: PATCH /api/v1/content/{contentId}**

```
REQUEST

PathVariable : 3

{
 "title" : "테스트 데이터 수정하기!",
 "description" : "수정하였음!"
}
```

```
RESPONSE

200 OK 

{
    "id": 3,
    "title": "테스트 데이터 수정하기!",
    "description": "수정하였음!",
    "lastModifiedBy": "changhee",
    "lastModifiedDate": "2026-03-08T17:23:08.326283"
}

403 FORBIDDEN

{
    "errorCode": "C003",
    "message": "수정 | 삭제 권한이 없습니다."
}

404 NOT FOUND

{
    "errorCode": "C001",
    "message": "조회하려는 게시글이 존재하지 않습니다."
}
```

**콘텐츠 삭제 :: DELETE /api/v1/contents/{contentId}**

```
REQUEST

PathVariable : 31
```

```
RESPONSE

204 NO_CONTENT {}

403 FORBIDDEN

{
    "errorCode": "C003",
    "message": "수정 | 삭제 권한이 없습니다."
}


404 NOT FOUND 

{
    "errorCode": "C001",
    "message": "조회하려는 게시글이 존재하지 않습니다."
}
```
