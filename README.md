![](docs/title.png)
---
#  📝 프로젝트 개요
- ### 프로젝트 이름: 봄넷
- ### 프로젝트 설명: 농업 생산자를 위한 종합 데이터 분석 서비스

# 💻 주요 기능
- **로그인**:
  - 소셜인증 후 JWT기반 인증 관리
 
- **회원가입**:
  - 소셜인증 성공 후 자동 가입
  - 일반/농업 사용자 등록 후 최종 사용 승인

- **챗봇**:
  - 지역 예보 확인
  - 품목 가격 검색
  - 목표 경락가 알림 등록
  - 기타 농업 질문

- **시각화된 가격정보**:
  - 년/월/일 단위의 가격추이
  - 실시강 경락가
  - 품질(특,상,중,등외)별 가격
  - 지역별 가격
  - 품종별 생산지 -> 판매지 흐름 분석

- ****:
  - 연동된 계좌에서 대상 계좌로 송금할 수 있습니다.

- **푸시 알림**:
  - 기상 특보
  - 목표 경락가 도달

# 📱 주요 화면 구성

![](docs/asset/openbanking.png)
![](docs/asset/settlement.png)


# 💁‍♂️ 팀원
|                     PM/TL                      |                      Data                      |                     Dev                      |                     Infra                      |
|:----------------------------------------------:|:----------------------------------------------:|:--------------------------------------------:|:----------------------------------------------:|
| ![](https://github.com/klolarion.png?size=120) | ![](https://github.com/why-arong.png?size=120) | ![](https://github.com/ostar11.png?size=120) | ![](https://github.com/hansususu.png?size=120) |
|      [김재근](https://github.com/klolarion)       |      [김필모](https://github.com/why-arong)       |      [조정완](https://github.com/ostar11)       |      [한수빈](https://github.com/hansususu)       |
<br/>


# ⚙ 기술 스택

### Frontend
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?logo=react&logoColor=white)
![ReactQuery](https://img.shields.io/badge/ReactQuery-FF4154?logo=reactquery&logoColor=white)
![ContextAPI](https://img.shields.io/badge/ContextAPI-61DAFB?logoColor=white)

### Backend
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?logo=springboot&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-6DB33F?&logoColor=white)
![FastAPI](https://img.shields.io/badge/FastAPI-6DB33F?&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-FF4438?logo=redis&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?logo=mariadb&logoColor=white)

### Infra
![Helm](https://img.shields.io/badge/Helm-0F1689?logo=helm&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?logo=kubernetes&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?logo=jenkins&logoColor=white)
![ArgoCD](https://img.shields.io/badge/ArgoCD-D24939?logo=argo&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?logo=prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800?logo=grafana&logoColor=white)

![AWS](https://img.shields.io/badge/AWS-FF9900?logoColor=white)
![EKS](https://img.shields.io/badge/EKS-FF9900?logoColor=white)


### Data
![GCP](https://img.shields.io/badge/GCP-8C4FFF?logoColor=white)
![AirFlow](https://img.shields.io/badge/AirFlow-8C4FFF?logoColor=white)
![DBT](https://img.shields.io/badge/DBT-8C4FFF?logoColor=white)

### Tools
![Github](https://img.shields.io/badge/Github-181717?logo=github&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?logo=slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?logo=notion&logoColor=white)
![DrawIO](https://img.shields.io/badge/DrawIO-F08705?logo=diagramsdotnet&logoColor=white)
![GoogleDrive](https://img.shields.io/badge/GoogleDrive-4285F4?logo=googledrive&logoColor=white)


### API
![NaverSMS](https://img.shields.io/badge/NaverSearch-03C75A?logo=naver&logoColor=white)
![KAMIS](https://img.shields.io/badge/KAMIS-0052CC?logoColor=white)
![KMA](https://img.shields.io/badge/KMA-0052CC?logoColor=white)
![MAFRA](https://img.shields.io/badge/MAFRA-0052CC?logoColor=white)


# 📦 프로젝트 구조
```plaintext

BomNet/                      # 프로젝트 메인
├── airflow/                 
├── frontend/               
├── backend/
│   ├── auth                 # 인증서버
│   ├── core                 # 코어서버
│   ├── llm                  # 챗봇서버
│   └── notification         # 알림서버
├── terraform/              
├── fluent_bit/              
├── kibana/                  
├── jenkins/                
├── docs/                    # 각종 프로젝트 문서
└── README.md                # 프로젝트 개요

BomArgo/                     # ArgoCD 배포
├── auth/                 
├── core/            
├── llm/            
├── notification/            
├── redis/            
├── front/            
└── README.md                

BomLocust/                   # 부하 테스트
└── locust.py

elt-pipeline/                # 데이터 파이프라인
├── airflow-local/                 
├── gcp-terraform/            
├── mafra/            
├── schemas/            
└── README.md                

```

<br/>


## Github 브랜치 관리
- dev → 개인브랜치(작업용) → dev 병합
- 개인브랜치 명명 규칙
  - 이름(닉네임)-작업할 모듈(Frontend, Core-Server)-작업목적
    - ex) JK-Frontend-상품페이지 구현

---

## Github 커밋 규칙
- 목적/해당 모듈-이름(닉네임)
  - ex) feat/AuthService-JK
- 목적 리스트
  - feat/ - 신규 개발
  - up/ - 기존 코드 수정
  - fix/ - 오류 수정
  - docs/ - 문서 관련 작업

---

PR 템플릿

✨ 작업 개요 (Overview)

🔑 주요 변경 사항 (Changes)

📸 스크린샷 (Optional)

📝 기타 (Notes)


# 🛠️ 프로젝트 아키텍처
![](docs/시스템 아키텍처.png)

<br/>
