# game-spring-basic-assignment

Spring Boot + JPA + MySQL로 카드 로그라이크 게임 "Crimson Citadel"의 백엔드 API를 구현하는 과제입니다. 완성된 프론트엔드가 `src/main/resources/static`에 포함되어 있어, 서버가 API 명세대로 동작하면 `http://localhost:8080`에서 실제 게임으로 플레이할 수 있습니다.

- 완성된 게임 데모: https://nhahan.github.io/crimson-citadel/
- API 명세: https://f-api.github.io/game-spring-api-docs/basic/api-docs.html
- 상세 요구사항: [ASSIGNMENT.md](ASSIGNMENT.md)

## 진행 현황

필수 기능(Lv 1 ~ Lv 8) 전체 구현 완료.

- [x] Lv 1. 설정 파일 작성: Docker MySQL 연결
- [x] Lv 2. 빈 등록 고치기: 의존성 주입
- [x] Lv 3. RESTful 경로 맞추기: 게임 목록 API
- [x] Lv 4. `@Transactional` 버그 고치기
- [x] Lv 5. 요청 검증과 응답 DTO: 게임 생성
- [x] Lv 6. 보상 카드 선택과 진행 저장
- [x] Lv 7. 목록·상세 조회: 저장된 여정 이어하기
- [x] Lv 8. 변경 감지로 이름 수정, 자식부터 삭제
- [ ] Lv 9 ~ Lv 12 (도전 기능): 미구현

## 기술 스택

- Java 21
- Spring Boot 4.1.0 (Web MVC, Data JPA, Validation)
- MySQL (Docker), Lombok
- Gradle

## 아키텍처

Controller - Service - Repository 3-Layer로 구성했습니다. `Game`과 `RunCard`(카드) 엔티티는 단방향 연관관계만 사용하며, cascade·orphanRemoval·양방향 컬렉션은 쓰지 않습니다. `RunCard`의 조회·저장·삭제는 `RunCardRepository`를 통해 명시적으로 처리합니다.

```
src/main/java/com/gamebasic
├── common
│   ├── dto/ErrorResponse.java
│   └── exception/           # GlobalExceptionHandler 등 전역 예외 처리
├── game
│   ├── controller/GameController.java
│   ├── dto/                 # CreateRequest, ProgressRequest, RenameRequest, GameSummaryResponse, GameDetailResponse
│   ├── entity/               # Game, GamePhase, GameStatus
│   ├── repository/GameRepository.java
│   └── service/GameService.java
└── runcard
    ├── dto/                  # RunCardRequest, CardResponse
    ├── entity/RunCard.java
    └── repository/RunCardRepository.java
```

## 실행 방법

### 1. MySQL 실행 (Docker)

```bash
docker run -d --name crimson-citadel-mysql \
  -e MYSQL_ROOT_PASSWORD=root1234 \
  -e MYSQL_DATABASE=crimsoncitadel \
  -p 3307:3306 \
  mysql:8
```

### 2. 서버 실행

```bash
./gradlew bootRun
```

### 3. 게임 접속

브라우저에서 `http://localhost:8080` 접속.

> 데이터소스 설정은 `src/main/resources/application.properties`에 있습니다. 위 Docker 컨테이너 설정과 다르게 MySQL을 구성했다면 `url`, `username`, `password` 값을 맞춰서 수정하세요.

## 구현한 API

| Method | Path | 설명 |
| --- | --- | --- |
| GET | `/games` | 게임 목록 조회 (id 내림차순) |
| GET | `/games/{gameId}` | 게임 상세 조회 |
| POST | `/games` | 게임 생성 |
| PUT | `/games/{gameId}/progress` | 진행 상황과 전체 덱 저장 |
| PATCH | `/games/{gameId}` | 플레이어 이름 변경 (더티 체킹) |
| DELETE | `/games/{gameId}` | 게임 삭제 (자식 `RunCard` 선삭제 후 `Game` 삭제) |

전체 요청/응답 필드는 [API 명세](https://f-api.github.io/game-spring-api-docs/basic/api-docs.html)를 따릅니다.

## 테스트

```bash
./gradlew test
```

통합 테스트는 H2 인메모리 DB(`src/test/resources/application-test.properties`)를 사용하며, 런타임 서버는 MySQL만 사용합니다.
