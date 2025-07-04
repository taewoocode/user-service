## Commit Convention

다음과 같은 커밋 타입을 사용하여 작업 내역을 효율적으로 관리합니다.

| **타입**     | **설명**               |
|------------|----------------------|
| `feat`     | 새로운 기능 추가            |
| `fix`      | 버그 수정                |
| `docs`     | 문서 변경                |
| `style`    | 코드 스타일 변경 (로직 수정 없음) |
| `refactor` | 리팩토링 (기능 변경 없음)      |
| `test`     | 테스트 추가/수정            |
| `chore`    | 빌드, 환경 설정 등 기타 수정    |

**커밋 메시지 규칙**:

- 커밋 메시지는 `[타입]:` 위와 같은 형식으로 작성합니다.
- 형식: `D-DAY[타입]: <작업 내용>`
- 예시:
    - `D-DAY[feat]: 사용자 로그인 기능 구현`
    - `D-DAY[fix]: 잘못된 입력 값 처리 오류 수정`

---

## 개발 환경 및 설정

본 프로젝트는 아래의 기술 스택과 환경을 기반으로 개발되었습니다.

- **Framework**: Spring Boot `3.5.3`
- **Programming Language**: Java `21`
- **Build Tool**: Gradle







