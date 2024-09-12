# 업체 서비스 API

## 개요
- 이 프로젝트는 업체(Company) 관리 서비스를 위한 RESTful API를 구현합니다.
- API는 업체 CRUD 및 Search 기능을 제공합니다.
- 필요 시 Feign Client로 외부 서버와 통신하여 필요한 데이터를 처리합니다.

## API Docs
| 필드명             | 타입           | 설명       | 제약조건                              |
|-----------------|--------------|----------|-----------------------------------|
| id              | BIGINT       | 업체 ID    | PK                                |
| user_id         | BIGINT       | 사용자 ID   | FK, NOT NULL                      |
| hub_id          | BIGINT       | 관리 허브 ID | FK, NOT NULL                      |
| company_name    | VARCHAR(100) | 업체명      | UK, NOT NULL                      |
| company_type    | ENUM         | 업체 타입    | NOT NULL ('SUPPLIER', 'RECEIVER') |
| company_address | VARCHAR(255) | 업체 주소    | NOT NULL                          |
| is_delete       | BOOLEAN      | 삭제 여부    | NOT NULL, default : false         |
| created_at      | DATETIME     | 생성일      | NOT NULL                          |
| created_by      | BIGINT       | 생성자      | NOT NULL                          |
| updated_at      | DATETIME     | 수정일      | NULL                              |
| updated_by      | BIGINT       | 수정자      | NULL                              |
| deleted_at      | DATETIME     | 삭제일      | NULL                              |
| deleted_by      | BIGINT       | 삭제자      | NULL                              |

## TODO
- [ ] Company Entity 정의
- [ ] Company Entity 영속 처리를 위한 Repository 정의
- [ ] Company CRUD 비즈니스 로직 개발
    - [ ] Company 등록
    - [ ] Company 단일 조회
    - [ ] Company 전체 조회 및 검색 기능 구현 : paging 처리
    - [ ] Company 수정
    - [ ] Company 삭제: 소프트 삭제(soft-delete) 방식
- [ ] Company CRUD API 개발
    - [ ] CompanyController 개발: API 응답 코드 처리 (예: 201 Created, 204 No Content)