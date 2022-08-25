# 1. hibernate-validator-summary

## (1) dependency(gradle)
```groovy
dependencies {
    implementation 'org.hibernate.validator:hibernate-validator'
}
```

<br>

## (2) validator annotation

- `@NotNull` : null 불허, blank 허용, white space 허용
- `@NotEmpty` : null 불허, blank 불허, white space 허용
- `@NotBlank` : null 불허, blank 불허, white space 불허
- 다른 어노테이션은 [[baeldung] Java Bean Validation Basics](https://www.baeldung.com/javax-validation)을 참고하자.

<br>

## (3) RestController 예외처리 예시

```java
@RestControllerAdvice
public class PostValidationExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<Map<String, String>>> handleRequestValidationException(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        Map<String, String> errors = methodArgumentNotValidException.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage)
                );

        ApiResult<Map<String, String>> apiResult = ApiResult.fail(HttpStatus.BAD_REQUEST, errors);

        return ResponseEntity.badRequest().body(apiResult);
    }

}

```

<br>

## (4) TestCode

```java
@Test
@DisplayName("로드맵의 제목은 ' '을 허용하지 않는다.")
void itShouldNotHaveOnlyWhiteSpace() {
    //given
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    String title = " ";

    //when
    RoadMap roadMap = new RoadMap(title);
    Set<ConstraintViolation<RoadMap>> violations = validator.validate(roadMap);

    //then
    assertThat(violations.size()).isEqualTo(1);
}
```
