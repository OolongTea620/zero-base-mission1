## 프로젝트 명
JSP와 공공 API를 이용한 내 위치와 가까운 와이파이 조회하기

## 목적
데이터 CRUD 경험,
공공 API를 사용함으로써 REST API 경험

## 프로젝트 설명
### 개발 환경 
OS : Windows 10     
Language : Java 8       
Web : JSP, Servlet, tomcat 8.5  

### 구현 기능, 설명서

### 구현 내용
**javascript , jsp, html 경험**
- javascript로 외부 라이브러리를 사용하여 위치 정보(GPS) 가져오기
- jsp, html 가볍게 활용하기, 웹 파라미터와 데이터 흐름 파악하기

**WIFI API 사용하고 WIFI 정보 다루기** 
- [x] API 데이터 가져오기, json 데이터 가공해보기
- [x] 나와 가까운 와이파이 위치 가져오기

**Location 정보 CRUD** 
- [x] 내 위치 정보 Create, Read, Delete 기능 만들기

### 개발 이슈

#### 1. 늦은 개발 시작
개인적인 사정이 있어서 개발에 시간을 많이 투자 못해서 아쉬움

#### 2. GPS -> 주소로 변환 해주는 kakao API를 사용하려다 만 이슈
-> 시간의 문제와 Wifi 정보 정책이 복잡해져서 사용 X

#### 3. 데이터 구조 클래스를 미리 만들어 놓으면 Gson으로 맵핑이 가능