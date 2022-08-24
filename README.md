---------------------------------------------------
1. maria 설치 및 테이블 생성(예제에는 id/passwd : root/1234 , 변경은 application.yml에서 하면 됨.! )
---------------------------------------------------
테이블스페이스 : petfriends

테이블생성 Script: create table user_info (
       user_id varchar(255) not null,
        avg_score double precision,
        career double precision,
        login_time datetime(6),
        logout_time datetime(6),
        password varchar(255),
        point_amount double precision,
        tel_no varchar(255),
        use_count double precision,
        user_nm varchar(255),
        user_role varchar(255),
        primary key (user_id)
    ) engine=InnoDB
;


---------------------------------------------------
2. kafka설치
---------------------------------------------------
참고사이트 : http://www.msaschool.io/operation/implementation/implementation-seven/

--------------------------------------------------
3. UserInfo(mariadb) 실행 및 테스트
--------------------------------------------------
1) UserInfo에서 아래와 같이 api 통해 데이터 생성하면, mariadb[userInfo테이블]에 데이터 저장되고, message publish.
    - 데이터생성(postman사용) : POST http://localhost:8082/userInfoes/
                              { "reservedId": "202203271311", "userId": "soya95", "amount": "10000", "payDate": "2019-03-10 10:22:33.102" }

    - 조회 : GET http://localhost:8080/userInfoes/1

--------------------------------------------------
4. 구조
   -controller
   -service
   -repository
   -dto
   -model
   -config : KafkaProcessor.java, WebConfig.java(CORS적용)
--------------------------------------------------

--------------------------------------------------

6. swagger추가 : http://localhost:8080/swagger-ui.html

