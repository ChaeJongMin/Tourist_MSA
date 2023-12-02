# 광주 관광지 앱 (게인)

광주의 관광지를 네이버 지도 API를 통해 방문 수에 따라 아이콘을 달리 표현하여 어떤 관광지가 유명한지 알 수 있으며 관광지에 대한 간단한 서비스를 제공하는 프로젝트입니다.

## 기간
2023.09 ~ 2023.11

## 기능

1. 로그인 / 회원가입 / 마이페이지<br>
2. 관광지 지도에 표시 / 관광지 정보 / 관광지의 리뷰

## 실행영상

[![Video Label](http://img.youtube.com/vi/eQr5fW61atY/0.jpg)](https://youtu.be/eQr5fW61atY)

## 사용 기술

1. **언어** : Java, JS , HTML5/CSS3
2. **DB** : MySQL 8.0.3
4. **프레임워크** :SpringBoot 3.1.3
5. **웹 개발 및 마이크로서비스** : Spring cloud Gateway / Config / Bus / Netflix(Service Discovery/Client), JPA
6. **메시징 및 모니터링** : Kafka 2.13 - 3.5.0 , prometheus 2.45.0 , grafana 10.0.1
7. **보안** : Spring Security , JWT
8. **OS** : Windows10
9. **기타** : FeginClient

## 서비스 설명

1. User-Service : 로그인, 회원가입, 유저 정보 변경 제공
2. Map-Service : 지도 생성 및 관광지 표시 , 관광지에 대한 설명/리뷰, 유저가 리뷰 쓴 관광지 제공
3. Review-Service : 리뷰 생성/수정/삭제 제공
4. Auth-Service : 액세스/리프레쉬 토큰 발급, 액세스 토큰 재발급
5. Gateway-Service : 클라이언트 요청을 라우팅, 필터 제공
6. Config-Server : 외부 저장소에서 서비스에게 환경정보 제공 

## 개발 일지
+ ### 9월
 2023.09 Service Discovery 개발<br/>
 2023.09 User-servcie의 로그인, 회원가입 개발<br/>
 2023.09 Map-Service의 지도에 관광지 표시<br/>
 2023.09 Map-Service의 관광지 상세 정보 및 리뷰 개발 <br/>
+ ### 10월 
 2023.10 Review-Service 개발<br/>
 2023.10 Map-Service의 관광지에 대한 리뷰 및 리뷰 작성 개발<br/>
 2023.10 User-servcie의 마이페이지 개발<br/>
 2023.10 gateway-service 개발<br/>
 2023.10 Auth-servcie 개발<br/>
+ ### 11월
 2023.11 config-server 개발<br/>
 2023.11 모니터링 기능 개발<br/>

+ ※ 현재 추가 기능 개발 예정 중 

## 기타

자세한 정보는 notion 링크로 확인 가능합니다. 
https://scythe-jeep-25f.notion.site/9c541f2a790c4f95a57877334f7f8e5c?pvs=4
