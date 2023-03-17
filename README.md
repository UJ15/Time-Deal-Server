# Time-Deal-Server.  

## Description   
타임딜 이커머스 서비스의 동시성 처리 프로젝트   

Git-Hub Flow 적용   
Feat branch : 기능 개발 브랜치
Dev branch : 로컬 환경, 개발 코드, 테스트, 병합 브랜치
Main branch : 배포 브랜치

1. feat -> dev 로 개발 후 pull Request
2. sonarcloud 기반 정적 코드 분석, 테스트 커버리지 확인
3. dev 브랜치 병합 후 main 브랜치로 push, merge
4. main브랜치 코드 

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

<img width="800" alt="image" src="https://user-images.githubusercontent.com/57293011/224645406-e9cc4903-bba2-452e-b8e6-007d92d5cc54.png">.  
<img width="800" alt="image" src="https://user-images.githubusercontent.com/57293011/225609309-d55cdf76-536d-4d69-be53-0668e41b5a21.png">. 
<img width="800" alt="image" src="https://user-images.githubusercontent.com/57293011/225609363-a091357b-a85d-4184-b50f-c801b290501b.png">. 

## API List 

### BASE URL   
https://ujtimedeal-api.n-e.kr

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
가이드 예시를 기반으로 구축
![image](https://user-images.githubusercontent.com/57293011/225600026-06993179-daf9-4e23-b36f-96e052c464eb.png)

## ERD

![image](https://user-images.githubusercontent.com/57293011/225600220-dc254692-36cf-41c9-8c34-c76d355eb8ad.png)

## Performence & Improvement
- 현재 synchronized 키워드를 이용한 동시성 처리가 되어있습니다.
이후에 Optimistic Lock, Pessimistic Lock, Redis를 이용한 Lock으로 각각 성능을 테스트 해볼 계획인데
ngrinder controller와 agent를 같은 서버에 두니 CPU에 무리가 가서 제대로된 테스트가 되지 않는것으로 예상됩니다.
서버 분리 이후 성능 측정 및 개선을 해나갈 것 같습니다.

### 상품 목록 조회
![image](https://user-images.githubusercontent.com/57293011/225610261-716fdfaa-646f-4252-994b-cae7edb04516.png)
![image](https://user-images.githubusercontent.com/57293011/225610331-c35776e3-066e-4d2f-9956-ae39e5abaa23.png)


### 회원가입 -> 로그인 -> 주문
- 재고가 1억개인 상품에 다수의 이용자가 멀티스레드 환경에서 동시 요청
![image](https://user-images.githubusercontent.com/57293011/225621449-e864be0a-955f-4688-9573-b018e4c10fb2.png)   
![image](https://user-images.githubusercontent.com/57293011/225621276-54a2d75e-246d-4184-9dd3-19ac0e78e3de.png)  
![image](https://user-images.githubusercontent.com/57293011/225621636-0d514bc2-4586-4c12-8fde-dcb55f82f461.png)





## Trouble
- 1. 멀티 스레드 환경에서 하나의 상품에 다수의 주문이 들어올때 DataBase에서 DeadLock이 발생 -> 원인 분석 -> Mysql DB Lock 때문인가? DataBase Isolation Level을 Read Committed로 변경하였음 -> 해결 안됨 -> 주문 로직에 상품 재고를 JPA 더티체킹으로 Query를 날리는 부분이 있었음 -> 주문 Insert문과 상품 update문이 충돌하며 DeadLock이 발생하는 것이었음 -> 재고 변경 후 save이후에 주문객체를 save함 -> 문제 해결 


## 남은 과제
- [ ] ngrinder 서버 분리 (controller, agent)   
- [ ] 동시성 처리 방법 변경 후 성능 테스트   
	- [ ] JPA Optimistic Lock 활용   
	- [ ] Redis Lock 활용   
- [ ] 추가 구현   
