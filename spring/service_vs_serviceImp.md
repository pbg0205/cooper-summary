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
- Service interface 를 사용하는 이유는 `Loose Coupling` 이다. Loose Coupling 은 객체 간의 결합도는 낮춰 변화에 유연한 개발을 위함이다. 
  한 인터페이스의 여러 구현체를 통해 다형성을 실현할 수 있다. 또한 의존성 방향이 변경되 의존 관계를 줄일 수 있는 장점이 있다.

<br>
  
- 하지만, Service interface 를 선언하는 방법에 대해서도 고민할 필요가 있다. 상위 인터페이스에 로직의 구현 방법이 다양할 경우에는 적합하다. 미리
  인터페이스를 작성해 완벽하게 구현하는 방법이 어렵고, 미리 인터페이스를 정의하여 애번 인터페이스를 계속 수정하는 방법은 오히려 변경 지점을 늘리는 방법일 수도
  있다. 미리 Service interface 와 ServiceImpl class 를 나누어 구현하기 보다 한 인터페이스에 다양한 전략을 사용해야 하는 경우에 대한 고민이 필요하다.

<br>

- 또는 인프라를 의존하는 Adapter 구현체를 분리(DIP) 하는 계층형 또는 헥사고날 아키텍쳐 상에서 인터페이스를 선언하는 방법에서 또한 인터페이스를 적용하는 
좋은 예시이다. (DIP : 상위 모듈은 하위 모듈의 구현에 의존해서는 안된다. 하위의 모듈이 상위 모듈에 정의한 추상 타입에 의존해야 한다.)

<br>

### Reference

- Spring OOP - Service, ServiceImpl 구조에 대한 고찰 : https://www.popit.kr/spring-oop-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%98%88%EC%A0%9C1-service-serviceimpl-%EA%B5%AC%EC%A1%B0%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B3%A0%EC%B0%B0/