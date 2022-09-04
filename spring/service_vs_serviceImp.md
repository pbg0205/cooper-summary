# 언제 Service interface 를 선언해야 할까? 

### 1. 인터페이스를 선언하는 경우 : 다양한 전략이 필요할 경우 (OCP / Strategy)

```java
public interface ChangePasswordService {
    public void change(MemberId id, PasswordDto.ChangeRequest dto);
}
public class ByAuthChangePasswordService implements ChangePasswordService {
    private MemberFindService memberFindService;
    @Override
    public void change(MemberId id, PasswordDto.ChangeRequest dto) {
        if (dto.getAuthCode().equals("인증 코드가 적합한지 로직 추가...")) {
            final Member member = memberFindService.findById(id);
            final String newPassword = dto.getNewPassword().getValue();
            member.changePassword(newPassword);
            // 필요로직...
        }
    }
}
public class ByPasswordChangePasswordService implements ChangePasswordService {
    private MemberFindService memberFindService;
    @Override
    public void change(MemberId id, PasswordDto.ChangeRequest dto) {
        if (dto.getPassword().equals("비밀번호가 일치하는지 판단 로직...")) {
            final Member member = memberFindService.findById(id);
            final String newPassword = dto.getNewPassword().getValue();
            member.changePassword(newPassword);
        }
    }
}
```

- 추상화를 사용하는 장점은 인터페이스와 구현체를 분리함으로써 구현체의 독립적인 확장과 클라이언트 코드에 영향을 주지 않는다는 것이다. 이는 낮은 결합도를 가질 
  수 있고 OCP(Open Closed Principle)를 만족해 유연한 확장을 도모할 수 있는 장점이 있다.

<br>

- 하지만 무분별한 추상화를 사용하는 관습은 구조를 복잡해하는 단점이 존재한다. 가장 대표적인 예시가 **인터페이스와 구현체가 1:1 관게를 이루는 설계**이다.
  객체는 서비스가 커짐에 따라 확장될 가능성을 가지고 있고 이에 대비해야 한다. 하지만 1:1 관계를 가지는 형태를 추상화의 장점을 살리지 못할 뿐만 아니라 변경 
  포인트가 늘어날 수 있는 단점이 생긴다.

<br>

- 무분별한 추상화가 많아진 이유는 네이밍 컨벤션에 대해 고민해볼 필요가 있다. 주로 XXXService interface의 하위 구현체로 XXXServiceImpl 형태의 Impl
  접미사를 붙인 네이밍을 사용한다. 하지만 이와 같은 방식은 클린코드에 부적합한 네이밍일 뿐만 아니라 1:1 관계의 추상화를 만들 확률이 높은 방식의 네이밍이라고
  생각한다.

<br>

- 이유있는 추상화를 사용하기 위해서는 네이밍을 신경쓰도록 해보자. JCF 에 List 하위 구현체 네이밍을 보면 ArrayListImpl, LinkedListImpl 이 아닌 
  ArrayList, LinkedList 네이밍을 사용하고 있다. 적절한 추상화를 사용하기 위해 Impl 접미사 대신에 Service 접두사에 구체적인 내용을 기입하는 방식에 
  대해 고민해보면 좋을 것 같다는 생각이 들었다.

<br>

- 또한 인터페이스를 사용했던 이유에는 JDK Dynamic Proxy 도 있었다. SpringBoot 1.4 이전 버전에서는 인터페이스 기반의 JDK Dynamic Proxy를 사용했다.
  즉, 프록시를 사용하기 위해서는 Interface를 명세해야 했다. 하지만, SpringBoot 1.4 이후부터는 디폴트로 CGLIB 을 사용하기 때문에 프록시를 사용하기
  위한 별도의 interface 명세가 필요하지 않다.

<br>
  
- Service interface 를 선언하는 방법에 대해서도 고민할 필요가 있다. 상위 인터페이스에 로직의 구현 방법이 다양할 경우에는 적합하다. 미리 인터페이스를 
  작성해 완벽하게 구현하는 방법이 어렵고, 미리 인터페이스를 정의하여 애번 인터페이스를 계속 수정하는 방법은 오히려 변경 지점을 늘리는 방법일 수도 있다. 미리
  Service interface 와 ServiceImpl class 를 나누어 구현하기 보다 한 인터페이스에 다양한 전략을 사용해야 하는 경우에 대한 고민이 필요하다.

<br>

- 또는 인프라를 의존하는 Adapter 구현체를 분리(DIP) 하는 계층형 또는 헥사고날 아키텍쳐 상에서 인터페이스를 선언하는 방법에서 또한 인터페이스를 적용하는 
좋은 예시이다. (DIP : 상위 모듈은 하위 모듈의 구현에 의존해서는 안된다. 하위의 모듈이 상위 모듈에 정의한 추상 타입에 의존해야 한다.)


<br>

### Reference

- Service와 ServiceImpl 구조에 대해서 : https://see-one.tistory.com/1
- Spring OOP - Service, ServiceImpl 구조에 대한 고찰 : https://www.popit.kr/spring-oop-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%98%88%EC%A0%9C1-service-serviceimpl-%EA%B5%AC%EC%A1%B0%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B3%A0%EC%B0%B0/