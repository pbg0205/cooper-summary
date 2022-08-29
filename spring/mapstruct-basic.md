# MapStruct Basic

## 1. MapStruct 도입 배경

### (1) 이전 DTO 변경 메서드의 단점

```java
@ToString
@Getter
@RequiredArgsConstructor
public class PostCreateRequestDto {

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    @NotNull
    private final String author;

    //DTO 변환 코드
    public PostJpaEntity toEntity() {
        return PostJpaEntity.create(this.title, this.content, this.author);
    }

}

```

- 하나의 객체 타입이 다른 객체로 형변환하거나 여러 객체를 다른 객체로 합치는 일이 빈번하다. 그럴 때마다 매핑을 하는 코드를 작성하면
   **코드 중복이 많이 발생**할 수 있고, **비즈니스 로직에 섞여 로직의 가독성을 해친다.** 또한 사람이 직접 코드를 작성할 경우, **실수를 발생하기 쉬워 결국 생산성을 
   떨어뜨리는 단점**이 존재한다.

<br>

### (2) MapStruct 도입의 장점

```java
@Component
public class PostJpaEntityMapperImpl implements PostJpaEntityMapper {
    public PostJpaEntityMapperImpl() {
    }

    public PostJpaEntity toPostJpaEntity(Post post) {
        if (post == null) {
            return null;
        } else {
            String title = null;
            String content = null;
            String author = null;
            title = post.getTitle();
            content = post.getContent();
            author = post.getAuthor();
            PostJpaEntity postJpaEntity = new PostJpaEntity(title, content, author);
            return postJpaEntity;
        }
    }
}

```

- MapStruct 와 같은 ObjectMapping 라이브러리를 사용하면 다양한 장점이 있다. 우선 **컴파일 오류를 확인할 수 있어 개발자의 실수를 줄일 수 있다.** 또한
  MapStruct는 `Annotation Processor` 를 사용하기 떄문에 **속도와 디버깅에 장점**이 있다. 어노테이션 프로세서는  **컴파일 시에 바이트 코드를 조작해 
  매핑 코드를 생성**한다. 바이트 코드를 조작하는 방법은 **일반적으로 Reflection 보다 속도가 빠르고 디버깅을 쉽게 할 수 있다.** 

<br>

## 3. dependency(by.gradle)

```groovy
dependencies {
    implementation 'org.mapstruct:mapstruct:1.5.2.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.2.Final'
    
    // If you are using mapstruct in test code
    testAnnotationProcessor "org.mapstruct:mapstruct-processor:1.5.2.Final"
    
    // lombok-mapstruct-binding
    annotationProcessor "org.projectlombok:lombok-mapstruct-binding:0.2.0"

}
```

<br>

## 4. 간단 사용법

### (1) 작성한 PostJpaEntityMapper
```java
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostJpaEntityMapper {

    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.content", target = "content")
    @Mapping(source = "post.author", target = "author")
    PostJpaEntity toPostJpaEntity(Post post);
    
}
```

- MapStruct 에서 제공하는 @Mapper annotation을 선언하면 해당 인터페이스의 구현체 Mapper를 생성한다.
- 추가적으로 Spring Bean으로 등록하고 싶은 경우, `componentModel option`에 **spring** 을 명시하도록 하자. 
- 기본 생성자와 파라미터가 있는 생성자 두가지 생성자가 있을 경우, 기본 생성자가 매핑 우선순위를 가진다. 그러므로 만약 파라미터가 존재하는 생성자를 사용하고 
  싶은 경우, `@Mapping` 어노테이션을 선언하도록 하자.

<br>

### (2) 컴파일 이후 생성된 바이트 코드 확인

- 기본 gradle 바이트 코드 생성 디렉토리는 `build/classes` 이므로 선언한 Mapper의 위치 하위에 해당 어노테이션의 구현체를 확인해보자.

```java
@Component
public class PostJpaEntityMapperImpl implements PostJpaEntityMapper {
    public PostJpaEntityMapperImpl() {
    }

    public PostJpaEntity toPostJpaEntity(Post post) {
        if (post == null) {
            return null;
        } else {
            String title = null;
            String content = null;
            String author = null;
            title = post.getTitle();
            content = post.getContent();
            author = post.getAuthor();
            PostJpaEntity postJpaEntity = new PostJpaEntity(title, content, author);
            return postJpaEntity;
        }
    }
}
```

- Mapper 를 생성하는 방법은 interface 에 추상 메서드를 정의하면 해당 파라미터와 리턴타입을 파악해 Mapper 의 구현체를 생성하고 추상 메서드에 매핑 로직을
  컴파일 시점에 자동으로 코드를 추가한다. 또한 다양한 매핑 전략 **(e.g. setter, constructor, builder)** 을 선택할 수 있기 때문에 원하는 방식의 매핑 
  코드를 생성할 수 있다.

<br>

## 5. Further Study

- 다양한 MapStruct의 객체 매핑 (e.g. **서로 다른 속성 매핑**, **객체 합치기**, **@Mapping 속성 무시**, **직접 구현하기**...)
- Annotation Processor 에 관한 작동 원리 학습 및 annotation processor 실습
   (참고 강의 : [더 자바, 코드를 조작하는 다양한 방법](https://www.inflearn.com/course/the-java-code-manipulation))

<br>

## Reference

- MapStruct 1.5.2.Final Reference Guide : https://mapstruct.org/documentation/stable/reference/html/
- [NHN-Cloud] Object Mapping 어디까지 해봤니? : https://meetup.toast.com/posts/213

