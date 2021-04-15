# 야간 자율 학습 출석부 프로젝트 (PICK)
## 백엔드 (Saturn)
![picklogo](https://user-images.githubusercontent.com/48639421/112571142-c6340280-8e2a-11eb-990d-80cd17f2fcc9.png)  
기존에 종이로 이루어지던 출석부의 단점들을 분석하고 이를 웹 서비스로 해결하려는 프로젝트

## Feature
- 사용자
  - 선생님 인증
  - 선생님 관리
- 출석
  - 학생 출석 현황 관리
  - 외출 현황 조회
  - 출석 현황 빠른 조회
  - 출결 변동 사항 등록
- 일정
  - 감독 선생님 일정 변경
  - 방과 후 일정 변경
- 어드민
  - 동아리 관리
  - 동아리원 관리
  - 일정 데이터 엑셀로 삽입
  - 학생 데이터 엑셀로 삽입

## Project Structure
```
  src
    ㄴmain
    |   ㄴkotlin
    |   |   ㄴcom
    |   |       ㄴdsm
    |   |           ㄴpick
    |   |               ㄴconfiguration
    |   |               ㄴcontroller
    |   |               |   ㄴrequest
    |   |               |   ㄴresponse
    |   |               ㄴdomain
    |   |               ㄴexception
    |   |               ㄴrepository
    |   |               ㄴservice
    |   |               ㄴPickApplication.kt
    |   ㄴresources
    |       ㄴapplication.yml
    ㄴtest
        ㄴkotlin
        |   ㄴcom
        |       ㄴdsm
        |           ㄴpick
        |               ㄴconfiguration
        |               ㄴcontroller - Integration Test
        |               ㄴrepository - Repository Layer Unit Test
        |               ㄴservice - Service Layer Unit Test
        |                   ㄴtest - Test Data Provider
        ㄴresources
            ㄴapplication.yml
            ㄴdata.sql
```
