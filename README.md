# Time-Deal-Server.  

## Description   
타임딜 이커머스 서비스의 동시성 처리 프로젝트   

## Skills
<div align=left>
<img src="https://img.shields.io/badge/Oracle open jdk 11-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot 2.7.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/gradle -02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">

<br>
<img src="https://img.shields.io/badge/mysql 8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
<img src="https://img.shields.io/badge/sonar cloud-F3702A?style=for-the-badge&logo=sonarcloud&logoColor=white">
<img src="https://img.shields.io/badge/naver cloud-03C75A?style=for-the-badge&logo=naver&logoColor=white">



## Architecture

<img width="800" alt="image" src="https://user-images.githubusercontent.com/57293011/224645406-e9cc4903-bba2-452e-b8e6-007d92d5cc54.png">

## API List 
### 회원

- 회원가입    
POST :  `/api/users`

```java
Request -> 201Created
{
	"uesrname":String
	"password":String
	"role":Enum [USER, ADMIN]
}
```

- 로그인    
POST : `/api/auth/`

```java
Request -> 200OK
{
	"username":String
	"password":String
}
```

- 회원탈퇴    
DELETE : `/api/users`

```java
JSESSIONID:<Id>
```

- 회원조회    
GET : `/api/users`

```java
JSESSIONID:<Id>
```

```java
Response 200OK
{
	"username":String
	"role":Enum [USER, ADMIN]
	"createdAt":"yyyy-MM-ddTHH:mm:ss"
}
```

### 상품

- 상품생성   
POST : `api/products`

```java
Reqeust -> 201Created
Session[role -> ADMIN]
{
	"name":String
	"description":String
	"quantity":long
	"price":long
	"dealtime":"yyyy-MM-ddTHH:mm:ss"
}
```

- 상품상세    
GET: `api/products/<productId>`

```java
Response 200OK
{
	"name":String
	"description":String
	"quantity":long
	"price":long
	"dealtime":"yyyy-MM-ddTHH:mm:ss"
	"createdAt":"yyyy-MM-ddTHH:mm:ss"
	"updatedAt":"yyyy-MM-ddTHH:mm:ss"
}
```

- 상품목록    
GET: `api/products`

```java
Response 200OK
[
	{
		"id":UUID
		"name":String
		"description":String
		"quantity":long
		"price":long
		"dealtime":"yyyy-MM-ddTHH:mm:ss"
		"createdAt":"yyyy-MM-ddTHH:mm:ss"
		"updatedAt":"yyyy-MM-ddTHH:mm:ss"
	}
]
```

- 상품수정    
PATCH: `api/products/<productId>`

```java
Request -> 200OK
Session[role -> ADMIN]
{
	"name":String
	"description":String
	"quantity":long
	"price":long
	"dealtime":"yyyy-MM-ddTHH:mm:ss"
}
```

- 상품삭제    
DELETE: `api/products/<ProductId>`

```java
Session[role -> ADMIN] -> 200OK
```

### 주문

- 주문생성   
POST: `api/orders/<productID>`

```java
JSESSIONID : <id> role:USER -> 201Created
```

- 유저의 주문목록    
GET: `api/orders`

```java
Response 200OK 
[
	{
	  "orderId":UUID
    "productId":UUID
    "productName":String
	}
]
```

- 상품 주문유저목록   
GET: `api/orders/<productId>`

```java
Response 200OK
[
	{
	  "orderId":UUID
    "userId":UUID
    "username":String
	}
]
```
## Wireframe
## ERD
## Performence & Improvement
## History
