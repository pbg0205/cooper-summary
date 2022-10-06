## [Introduction-to-hibernate]

1. 하이버네이트의 필요성

1. JDBC를 사용할 때는 DB S/W 에 의존적인 코드가 많다.
- 만약 MySQL 에서 다른 DB로 변경함다면 쿼리가 정상 동작하지 않을 수 있다.(dialect)

2. JDBC 가 동작하는 과정에서 DB를 변경하는 비용이 상당했다.
3. JDBC 코드는 여러 DB S/W 에서 이식 가능한 코드가 아니다.
4. JDBC 에서는 예외처리가 필수이기 때문에 예외처리에 관한 내용을 알고 있어야 한다.
5. JDBC 로 작업하는 동안 지원되는 객체 레벨의 관계를 지원하지 않는다.
   (There is no support Object-level relationship)
6. 보일러플레이트 코드가 많아 코드 길이가 늘어나고 가독성이 떨어진다.


이와 같은 문제점을 극복하기 위해서 ORM framework 가 도입되었다.

## [About Hibernate Framework]

1. 하이버네이트는 영속성 로직을 개발하는데 사용하는 Java ORM Framework 이다.
2. 하이버네이트는 DB Connection, 쿼리 작성과 같은 로직을 추상화하여 프로그래머가 구현에 걱정할 필요가 없는 장점이 있다. 

### 하이버네이트 장점

1. 오픈 소스(open source) : hibernate 프레임워크는 누구나 사용할 수 있는 장점
2. 경량화(light-level) : 설치 패키지가 크지 않고 실행을 위해 무거운 컨테이너가 필요하지 않다.
3. 비침투적(Non-invasive) : hibernate API 와 관련하여 느슨한 결합을 하고 있기 때문에 hibernate를 개발 클래스와 느슨한 결합을 하고 있다. (Hibernate 어플리케이션을 개발하는 과정에서 사용하는데는 큰 영향이 없다.)

## [Functionalities supported by Hibernate framework]

1. Auto DDL
2. AUto Primary key generation
3. HQL을 지원해서 DB와 독립적인 프레임 워크이다.
4. 예외 처리가 필수 사항이 아니다. (JDBC에서는 예외처리가 필수이다.)
5. 하이버네이트는 캐시 메모리를 지원한다.
6. ORM tool 을 제공한다.


<br>

### Refernces

- https://www.geeksforgeeks.org/introduction-to-hibernate-framework/
- 