# clean-architecture study

[해당 글은 만들면서 배우는 클린 아키텍처에서 학습한 내용을 다시 한번 복습하는 내용이다.]
- 만들면서 배우는 클린 아키텍쳐 간단 정리 레포지토리 : https://github.com/pbg0205/clean-architecture

## 1. clean-architecture (hexagonal architecture)

### 1. hexagonal architecture??

<img width="656" alt="image" src="https://user-images.githubusercontent.com/48561660/187008400-fc39efe9-21ac-437a-b786-9641dc2d9cb2.png">

- 흔히, 헥사고날 아키텍쳐로 부르며 외부와 통신하는 부분과 핵심 비즈니스 부분을 분리하는 것이 핵심이다. 어플리케이션 코어와 어댑터들이 통신하는 형태를 띄고
있어 `포트와 어댑터(ports-and-adapters)` 아키텍처라고도 불린다.
- 육각형 안에는 도메인 엔티티와 상호작용하는 `유스케이스(usecase)`가 있다. 유스케이스는 어플리케이션 요구사항을 인터페이스를 통해 정의한 컴포넌트다.
 유스케이스를 인터페이스로 의존성의 방향을 변경시켜 DIP를 만족하기 때문에 외부에 의존성을 신경 쓸 필요가 없고 변경에 유연한 구조가 된다.

(Reference : https://masne.medium.com/hexagonal-architecture-part-1-b8357d3ee17d)

<br>

### 2. 컴포넌트 구조

<img width="720" alt="image" src="https://user-images.githubusercontent.com/48561660/187009082-97557c7e-d99d-4a53-bef1-b234ffde6fc2.png">

- 위 그림과 같이 핵심 비즈니스 로직의 의존성 방향은 외부(어댑터)로 향하고 있지 않다. 의존성을 방향을 변경하여 DIP를 만족시켜 외부 어댑터가 비즈니스 로직에
영향을 받지 않아 우리가 원하는 비즈니스 로직에 집중할 수 있는 장점이 있다.
- SendMoneyUseCase 는 주로 우리가 구현하고자 하는 비즈니스 로직에 관한 명세를 하는 부분이고
  주로 비즈니스 로직들의 상호작용을 캡화한 `패서드패턴(Fasade Pattern)` 형태로 로직을 작성한다.
    - 패서드 패턴 : 서브시스템(세부 비즈니스 로직) 위에 상위 인터페이스를 정의해 비즈니스 로직을 캡슐화하는 패턴

<br>

## 2. 헥사고날 아키텍쳐 패키지 구조
<img width="319" alt="image" src="https://user-images.githubusercontent.com/48561660/187010138-ab130ee9-6e11-4681-b8a9-11bb438ff9f6.png">

1. `adapter` :  외부의 요청 또는 응답을 담당하는 계층
    1. `in.web` : 웹 계층에서 데이터 요청이 들어오는 부분을 담당하는 계층
   2. `out.persistence` : 어플리케이션의 데이터를 영속화하기 위한 계층 (데이터의 방향이 application -> persistence이기 때문에 상단에 out이 있음)
2. `application` : 실제 우리가 만들고자 하는 비즈니스 로직을 작성하는 부분
    1. `port` : 외부의 요청을 처리하거나 응답을 반환하기 위한 인터페이스를 모아둔 패키지
       1. `in` : 데이터가 application 계층 내부로 들어올 떄 외부와 통신하기 위한 인터페이스를 모아둔 패키지
      2. `out` : 데이터의 결과를 application 계층 외부로 전송하고자 할 때 외부와 통신하기 위한 인터페이스 모아둔 패키지
   2. `service` : 인터페이스의 구현체를 모아둔 패키지로 비즈니스 로직을 담당하는 부분
   3. `domain` : 어플리케이션의 인스턴스를 모아둔 패키지

<br>




