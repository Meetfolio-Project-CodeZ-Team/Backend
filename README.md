## 📑 Kobert, GPT AI를 통한 자기소개서 피드백 : 경험 분석 및 공유 서비스

<img width="650" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/f4f6c461-9371-4e9d-a914-ee7207aba434">

> WEB URL : http://www.meetfolio.kro.kr:60005/

> 작성자 : 김현겸 (kylo-dev)

<br/>

<p align=center>
<div align=center>
    <a href="https://hits.seeyoufarm.com"><img src="https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FMeetfolio-Project-CodeZ-Team%2FFrontend&count_bg=%23002681&title_bg=%234191E0&icon=metrodeparis.svg&icon_color=%23FFFFFF&title=hits&edge_flat=false"/></a>
</div>

## ✏️프로젝트 소개

<img width="650" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/d22f69c7-686f-4dfa-b6fa-4741069b130a">

### 서비스 기획 이유

기존 채용 웹 서비스에서 제공하는 AI 자기소개서 서비스는 간단한 피드백으로 컨텐츠가 부족하고, 자기소개서 작성 방향성을 잡아주지 않는 아쉬움이 있었습니다. 
또한, 기존 대학생 커뮤니티 서비스에서는 공모전, 스터디, 대외활동 등 팀원을 구하는 게시글을 조회할 수 있지만, 다른 사용자의 정보를 알 수 없어 관심사가 같은지, 어떤 경험을 했는지 파악하기 어려워 나에게 맞는 팀원을 모집하는데 어려움이 있었습니다.

### 서비스의 차별점
이러한 문제점들을 해결하기 위해 Meetfolio 서비스는 2가지 주요 기능을 기획하여 문제점들을 해결하고자 하였습니다.

1. 첫 번째 기능은 개인 경험을 바탕으로 경험카드를 작성하여 체계적으로 자기소개서 초안을 작성하는 것입니다. 이를 통해 자기소개서 작성 방향성을 잡을 수 있도록 도움을 주고, KoBERT, GPT AI 서비스를 통해 자기소개서 피드백을 제공합니다. 
또한, 지원한 직무와 얼마나 연관성 있는지 직무 역량을 분석해주는 서비스를 기획하였습니다.


2. 두 번째 기능은 가천대학교 학우들 간의 취업 정보 및 경험을 교류할 수 있는 커뮤니티를 형성하여, 취업 정보 수집과 그룹원 모집의 어려움을 해소하려고 하였습니다. 
사용자들이 작성한 경험카드와 공개된 자기소개서를 다른 사용자가 조회할 수 있도록 하여, 비슷한 진로를 가진 사람인지, 포트폴리오 준비를 어떻게 하고 있는지 등의 정보를 공유할 수 있는 서비스를 기획하였습니다.


## 👨‍💻팀원 구성

|                                         Backend / AI                                         |                                       Backend / AI                                       |                                        Frontend                                         |                                        Frontend                                         |
|:--------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|
|   <img src="https://avatars.githubusercontent.com/u/85955988?v=4 " width=200px alt="서주원"/>   | <img src="https://avatars.githubusercontent.com/u/103489352?v=4" width=200px alt="김현겸"/> | <img src="https://avatars.githubusercontent.com/u/99270060?v=4" width=200px alt="최민규"/> | <img src="https://avatars.githubusercontent.com/u/91466601?v=4" width=200px alt="김낙도"/> |
|                            [@joowojr](https://github.com/joowojr)                            |                         [@kylo-dev](https://github.com/kylo-dev)                         |                      [@Minkyu0424](https://github.com/Minkyu0424)                       |                           [@NAKDO](https://github.com/NAKDO)                            |
|                                           [팀장] 서주원                                           |                                           김현겸                                            |                                           최민규                                           | 김낙도 |

## 🤝역할 분담

|        Backend / AI        |                    Backend / AI                    |                   Frontend                   |             Frontend             |
|:--------------------------:|:-------------------------------------:|:--------------------------------------------:|:--------------------------------:|
|        **[팀장] 서주원**        |                  **김현겸**                    |                 **최민규**                  |            **김낙도**            |
| 회원가입, 로그인/로그아웃, 이메일 인증 <br/> 카카오 페이 API, 포인트 충전 및 관리, <br/> 관리자, 커뮤니티 관리 | 경험 카드, 자기소개서 관리, <br/> 마이페이지, 댓글 관리 <br/> GPT를 통한 AI 자기소개서 피드백, <br/> KoBERT를 통한 AI 직무 역량 분석 | 메인 페이지, 회원가입, 로그인 <br/> 카카오 페이 API 연동, 커뮤니티, <br/> 관리자 | 경험 카드, 자기소개서, <br/> 마이페이지, AI 피드백 결과 |


## 1. ⚙️ 개발 환경

### Backend Stack

* Java 17
* Framework: Springboot 3.2.4, Spring Security 6.3, Spring Data JPA, Swagger 2.2, JWT 0.11.5
* Database: MySQL, Redis
* Cloud : Google Cloud Platform
* CI/CD : Github Actions, Docker, Docker-Compose
* Git: Git, Github, Git Submodule

### Frontend Stack
<table>
    <thead>
        <tr>
            <th>분류</th>
            <th>기술 스택</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
                  <p>프론트엔드</p>
            </td>
            <td>
                  <img src="https://img.shields.io/badge/Next.js-000000?style=flat&logo=Next.js&logoColor=white"/>
                  <img src="https://img.shields.io/badge/typescript-1572B6?style=flat&logo=typescript&logoColor=000000"/>
                  <img src="https://img.shields.io/badge/tailwindcss-1252B6?style=flat&logo=tailwindcss&logoColor=white"/>
                  <img src="https://img.shields.io/badge/recoil-61DAFB?style=flat&logo=recoil&logoColor=000000"/>
            </td>
        </tr>
        <tr>
            <td>
                <p>백엔드</p>
            </td>
            <td>
                <img src="https://img.shields.io/badge/Docker-2496ED?&logo=Docker&logoColor=white">
              <img src="https://img.shields.io/badge/Spring_Boot-%236DB33F?logo=springboot&logoColor=white">
              <img src="https://img.shields.io/badge/Spring_JPA-%236DB33F?logo=spring&logoColor=white">
            </td>
        </tr>
        <tr>
            <td>
                <p>협업</p>
            </td>
            <td>
                <img src="https://img.shields.io/badge/Notion-000000?logo=Notion">
                <img src="https://img.shields.io/badge/Figma-F24E1E?logo=Figma&logoColor=ffffff">
            </td>
        </tr>
    </tbody>

</table>
* Next.js, Tailwind, Recoil

### AI Stack

* Flask, MySQL, Redis, Swagger
* Hugging Face - KoBERT (NLP Model)
* Gpt-3.5-turbo-1106
* Naver Clova Open API

## 2. 채택한 개발 기술과 branch 전략

### Meetfolio 팀의 Git branch 전략

```
'''
Git 분기 전략을 통해 여러 버전의 코드를 동시에 관리하고, 기능 개발 및 배포를 효율적으로 수행하는 데 도움이 되었습니다.
기능 개발, 버그 수정 또는 특정 작업을 위한 개별 브랜치를 생성하여 작업을 격리하여 진행하였습니다.

저희 팀에서는 main, develop, featuer(feat), fix 브랜치를 기반으로 Git 분기 전략을 진행하였습니다.

브랜치 구성
* main : 프로젝트의 안정적이고 배포 가능한 최신 버전을 포함하는 브랜치입니다. 서버 배포에 직접 사용됩니다.
* develop : 기능 개발 및 수정 코드를 통합하는 중간 브랜치입니다. main 브랜치에 병합되기 전에 코드 검증 및 테스트를 진행합니다.
* feature : develop 브랜치로부터 분기하여 각 기능 개발을 진행하는 브랜치입니다. 기능 개발 완료 후 develop 브랜치에 병합됩니다.
* fix : 버그 수정 또는 긴급 문제 해결을 위해 develop 또는 feature 브랜치로부터 분기하는 브랜치입니다. 문제 해결 후 develop 또는 feature 브랜치에 병합됩니다.
'''
```
<details>
  <summary><h2> 3. 프로젝트 구조</h2></summary>
  <div markdown="1">


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
</div>
</details>

## 4. 프로젝트 아키텍처

<img width="650" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/1dadd295-6506-4ff0-a5d0-e42e8739c373">


## 5. 프로젝트 CI/CD 파이프라인

<img width="650" alt="image" src="https://github.com/Meetfolio-Project-CodeZ-Team/Backend/assets/103489352/6a312027-6cc0-467d-b097-91cab3ec1239">


## 6. 개발 기간 및 작업 관리

### 2024.03 ~ 2024.06.07
* 요구 분석 [03/05 ~ 03/18]
* 설계 단계 [03/18 ~ 04/08]
* 구현 단계 [04/08 ~ 05/19]
* 완료 단계(보고서 작성 및 시연) [05/14 ~ 06/07]

## 7. 페이지별 기능


## 8. 프로젝트 후기

| 이름 |  프로젝트 후기 내용|
|:--------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|  서주원   |                                                                                                                                                                                                                                                                                                                                                                                                                                      이러고 저러고                                                                                                                                                                                                                                                                                                                                                                                                                                      |
|  김현겸   | 졸업 프로젝트를 진행하면서 개발 뿐만 아니라 기획, 설계 과정도 팀원들과 함께 개발의 전 과정을 경험할 수 있었습니다. 기획한 서비스를 다른 사람에게 설명하면서 서비스의 필요성과 기존 서비스와의 차별점을 명확히 하는 과정에서 서비스 이해도와 기획을 더욱 탄탄하게 다질 수 있었습니다. 또한, 아이디어 선정부터 서비스 기획, 와이어프레임 제작, 서비스에 맞는 도메인과 객체 추출 등 전 과정을 거치며 개발자로서 필요한 역량을 키울 수 있었습니다. <br/> 백엔드 개발자로서 구현할 API를 먼저 노션에 기록한 후, 프론트 개발자가 테스트할 수 있도록 Swagger에 명시하였습니다. Swagger에 API 사용법과 관련 DTO를 세부적으로 작성하여 프론트엔드 개발자가 API 연동에 어려움이 없도록 하였습니다. 세세하게 작성한 덕분에 프론트엔드 개발자로부터 API 명세서가 이해하기 쉽고 잘 작성되었다는 칭찬을 받았습니다. <br/> 이번 프로젝트를 통해 백엔드 개발자는 API 개발뿐만 아니라 이를 사용하는 프론트엔드 개발자가 편리하게 사용할 수 있도록 API 명세서 작성도 중요하고, 이를 위해 서비스의 온전한 이해와 흐름을 파악하고 있는 것이 중요하다는 것을 배웠습니다. <br/> 마지막으로, 졸업 프로젝트 AI 트랙을 통해 AI 모델링과 AI API 개발을 처음으로 프로젝트에 적용해보는 경험을 하였습니다. 데이터 수집, 전처리, 모델 학습, 배포를 진행하며 각 기능을 함수화하여 AI 서비스를 구현했습니다. 함수화하여 코드를 관리하여 유지보수에 용이함을 느낄 수 있었습니다.프로젝트에 AI 기능을 처음 적용해보았지만, 모델 파이프라인 과정을 하나씩 구현해가며 생각한 기능을 성공적으로 구현할 수 있었습니다. |
|  최민규   |                                                                                                                                                                                                                                                                                                                                                                                                                                      이러고 저러고                                                                                                                                                                                                                                                                                                                                                                                                                                      |
|  김낙도   |                                                                                                                                                                                                                                                                                                                                                                                                                                      이러고 저러고                                                                                                                                                                                                                                                                                                                                                                                                                                      |

