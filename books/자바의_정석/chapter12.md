# Chapter 12. 지네릭스, 열거형, 애너테이션

## 애너테이션에 여러개 인자

```java
@SuppressWarnings("unchecked")
@SuppressWarnings({"deprecation", "unchecked"})
@Target({TYPE, FIELD, METHOD, PARAMETER, ...})
```

- 배열에서처럼 중괄호{}를 추가로 사용해야한다.

## 메타 애너테이션

- 애너테이션을 위한 애너테이션
- 애너테이션에 붙이는 애너테이션

### `@Target`

```java
@Target({TYPE, FIELD, METHOD, PARAMETER, ...})
```

- 애너테이션이 적용가능한 대상을 지정하는데 사용된다.

|대상 타입|의미|
|---|---|
|`ANNOTATION`|애너테이션|
|`CONSTRUCTOR`|생성자|
|`FIELD`|필드(멤버변수, enum상수)|
|`LOCAL_VARIABLE`|지역변수|
|`METHOD`|메서드|
|`PACKAGE`|패키지|
|`PARAMETER`|매개변수|
|`TYPE`|타입(class, interface, enum)|
|`TYPE_PARAMETER`|타입 매개변수|
|`TYPE_USE`|타입이 사용되는 모든 곳|

> 표의 값들은 `java.lang.annotation.ElementType` 열거형에 지정

### `@Retention`

- 애너테이션이 유지(retention)되는 기간 지정

|유지 정책|의미|
|---|---|
|`SOURCE`|소스 파일에만 존재. 클래스파일에는 존재하지 않음.|
|`CLASS`|클래스 파일에 존재. 실행시에 사용불가. 기본값|
|`RUNTIME`|클래스 파일에 존재. 실행시에 사용가능.|

### `@Ingerited`

- 애너테이션이 자손 클래스에 상속되도록 한다.

```java
@Inherited
@interface SupperAnno { }

@SupperAnno
class Parent { }

// Child에 애너테이션이 안 붙었지만, 붙은 것으로 인식
class Child extends Parent { }
```

### `@Repeatable`

- 보통은 하나의 대상에 한 종류의 애너테이션을 붙이는데, `@Repeatable`이 붙은 애너테이션은 여러 번 붙일 수 있다.

```java
@interface ToDos {  // 여러 개의 ToDo 애너테이션을 담을 컨테이너 애너테이션 ToDos
    ToDo[] value(); // ToDo 애너테이션 배열타입의 요소 선언. 이름이 반드시 value이어야 함
}

@Repeatable(ToDos.class)
@interface ToDo {
    String value();
}

@ToDo("delete test codes.")
@ToDo("override inherited methods")
class MyClass {
    ...
}
```

## 애너테이션 타입 정의하기

```java
@interface 애너테이션이름 {
    타입 요소이름() default 기본값;
}
```

```java
@interface  TestInfo {
    int count();
    String testedBy();
    String[] testTools();
    TestType testType();    // enum TestType { FIRST, FINAL }
    DataTime testDate();    // 자신이 아닌 다른 애너테이션 포함 가능
}

@interface DateTime {
    String yymmdd();
    String hhmmss();
}

@TestInfo(
    count = 3,
    testedBy = "Kim",
    testTools = {"JUnit", "AutoTester"},
    testType = TestType.FIRST,
    testDate = @DateTime(yymmdd = "160101", hhmmss = "235959)
)
public class NewClass {
    ...
}
```

- 애너테이션의 요소는 반환값이 있고 매개변수는 없는 추상 메서드의 형태를 가지며, 상속을 통해 구현하지 않아도 된다.
- 애너테이션을 적용할 때 이 요소들의 값을 빠짐없이 지정해주어야 한다.
- 요소의 이름도 같이 적어주므로 순서는 상관없다.

```java
@interface TestInfo {
    int count() default 1;  // 기본값을 1로 지정
}

@TestInfo   // @TestInfo(count = 1)과 동일
class NewClass { ... }
```

```java
@interface TestInfo {
    String value();
}

@TestInfo("passed") // @TestInfo(value="passed")와 동일
class NewClass { ... }
```

- 애너테이션 요소가 오직 하나뿐이고 이름이 value인 경우, 애너테이션을 적용할 때 요소의 이름을 생략하고 값만 적어도 된다.

```java
@interface TestInfo {
    String[] testTools();
}

@TestInfo(testTools = {"JUnit", "AutoTester"})  // 값이 여러 개인 경우
@TestInfo(testTools = "JUnit")                  // 값이 하나일 때는 중괄호{} 생략가능
@TestInfo(testTools = {})                       // 값이 없을 때는 중괄호{} 반드시 필요
```

- 요소의 타입이 배열인 경우, 중괄호{}를 사용해서 여러 개의 값을 지정할 수 있다.

```java
@interface TestInfo {
    String[] info1() default {"aaa", "bbb"};    // 기본값이 여러개 인 경우.
    String[] info2() default "ccc";             // 기본값이 하나인 경우 중괄호{} 생략가능
}
```

### 애너테이션 요소의 규칙

- 요소의 타입은 기본형, String, enum, 애너테이션, Class만 허용된다.
- () 안에 매개변수를 선언할 수 없다.
- 예외를 선언할 수 없다.
- 요소를 타입 매개변수로 정의할 수 없다.