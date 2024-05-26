## 📑 Kobert, GPT AI를 통한 자기소개서 피드백 : 경험 분석 및 공유 서비스

<img width="850" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/f4f6c461-9371-4e9d-a914-ee7207aba434">

> WEB URL : http://www.meetfolio.kro.kr:60005/

## 프로젝트 소개


## 팀원 구성

|                                         Backend                                          |                                         Backend                                          |                                        Frontend                                         |                                        Frontend                                         |
|:----------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/85955988?v=4 " width=200px alt="서주원"/> | <img src="https://avatars.githubusercontent.com/u/103489352?v=4" width=200px alt="김현겸"/> | <img src="https://avatars.githubusercontent.com/u/99270060?v=4" width=200px alt="최민규"/> | <img src="https://avatars.githubusercontent.com/u/91466601?v=4" width=200px alt="김낙도"/> |
|                            [서주원](https://github.com/joowojr)                             |                            [김현겸](https://github.com/kylo-dev)                            |                          [최민규](https://github.com/Minkyu0424)                           |                             [김낙도](https://github.com/NAKDO)                             |
|                                          1 2 3                                           |                                 1 2 3                                   |                       1 2 3                          |                                1 2 3                                   |

## 1. 개발 환경

### Backend Stack

* Java 17
* Framework: Springboot 3.2.4, Spring Security 6.3, Spring Data JPA, Swagger 2.2, JWT 0.11.5
* Database: MySQL, Redis
* Cloud : Google Cloud Platform
* CI/CD : Github Actions, Docker, Docker-Compose
* Git: Git, Github, Git Submodule

### Frontend Stack

* Next.js

### AI Stack

* Flask, MySQL, Redis, Swagger
* Hugging Face - KoBERT (NLP Model)
* Gpt-3.5-turbo-1106
* Naver Clova Open API

## 2. 채택한 개발 기술과 branch 전략


## 3. 프로젝트 구조

```
📦 
├─ .github
│  ├─ ISSUE_TEMPLATE
│  │  └─ feature_request.md
│  ├─ pull_request_template.md
│  └─ workflows
│     └─ github-actions.yml
├─ .gitignore
├─ Dockerfile
├─ build.gradle
├─ gradle
└─ src.main.java.com.codez4.meetfolio
                  ├─ MeetfolioApplication.java
                  ├─ domain
                  │  ├─ admin
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  └─ service
                  │  ├─ analysis
                  │  │  ├─ Analysis.java
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ board
                  │  │  ├─ Board.java
                  │  │  ├─ EmploymentBoard.java
                  │  │  ├─ GroupBoard.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ comment
                  │  │  ├─ Comment.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ common
                  │  │  └─ BaseTimeEntity.java
                  │  ├─ coverLetter
                  │  │  ├─ CoverLetter.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ dataset
                  │  │  ├─ Dataset.java
                  │  │  └─ repository
                  │  ├─ emailAuth
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  └─ service
                  │  ├─ enum
                  │  │  ├─ Authority.java
                  │  │  ├─ BoardType.java
                  │  │  ├─ Grade.java
                  │  │  ├─ GroupCategory.java
                  │  │  ├─ JobKeyword.java
                  │  │  ├─ PaymentStatus.java
                  │  │  ├─ PointType.java
                  │  │  ├─ ProfileEmoji.java
                  │  │  ├─ ShareType.java
                  │  │  ├─ Status.java
                  │  │  └─ VersionStatus.java
                  │  ├─ experience
                  │  │  ├─ Experience.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ feedback
                  │  │  ├─ Feedback.java
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ like
                  │  │  ├─ Like.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ member
                  │  │  ├─ Member.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ model
                  │  │  ├─ Model.java
                  │  │  ├─ repository
                  │  │  └─ service
                  │  ├─ payment
                  │  │  ├─ Payment.java
                  │  │  ├─ controller
                  │  │  ├─ dto
                  │  │  ├─ repository
                  │  │  └─ service
                  │  └─ point
                  │     ├─ Point.java
                  │     ├─ controller
                  │     ├─ dto
                  │     ├─ repository
                  │     └─ service
                  └─ global
                     ├─ annotation
                     │  ├─ AuthenticationMember.java
                     │  ├─ AuthenticationMemberArgumentResolver.java
                     │  ├─ EnumValid.java
                     │  └─ EnumValidator.java
                     ├─ config
                     │  ├─ CorsConfig.java
                     │  ├─ EmailConfig.java
                     │  ├─ RedisConfig.java
                     │  ├─ SecurityConfig.java
                     │  ├─ SwaggerConfig.java
                     │  └─ WebMvcConfig.java
                     ├─ exception
                     │  ├─ ApiException.java
                     │  └─ ExceptionAdvice.java
                     ├─ jwt
                     │  ├─ JwtAuthenticationEntryPoint.java
                     │  ├─ JwtAuthenticationFilter.java
                     │  ├─ JwtExceptionFilter.java
                     │  ├─ JwtProperties.java
                     │  └─ JwtTokenProvider.java
                     ├─ response
                     │  ├─ ApiResponse.java
                     │  ├─ SliceResponse.java
                     │  └─ code
                     │     ├─ BaseCode.java
                     │     ├─ BaseErrorCode.java
                     │     ├─ ErrorReasonDto.java
                     │     ├─ ReasonDto.java
                     │     └─ status
                     │        ├─ ErrorStatus.java
                     │        └─ SuccessStatus.java
                     ├─ security
                     │  ├─ CustomUserDetailService.java
                     │  ├─ CustomUserDetails.java
                     │  └─ Password.java
                     └─ utils
                        ├─ RedisUtil.java
                        └─ TimeUtils.java
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)

## 4. 프로젝트 아키텍처

<img width="650" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/1dadd295-6506-4ff0-a5d0-e42e8739c373">


## 5. 프로젝트 CI/CD 파이프라인

<img width="650" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/7a484989-d956-4bd2-bd3e-de43cdc631b8">


## 6. 역할 분담


## 7. 개발 기간 및 작업 관리

### 2024.03 ~ 2024.06.07
* 요구 분석 [03/05 ~ 03/18]
* 설계 단계 [03/18 ~ 04/08]
* 구현 단계 [04/08 ~ 05/19]
* 완료 단계(보고서 작성 및 시연) [05/14 ~ 06/07]

## 8. 페이지별 기능


## 9. 트러블 슈팅


## 10. 프로젝트 후기
