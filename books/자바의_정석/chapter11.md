# Chapter 11. 컬렉션 프레임웍(Collection Framework)

## Enumeration

- `Enumeration` 은 컬렉션프레임웍이 만들어지기 이전에 사용하던 것으로 `Iterator`의 구버전
- `Iterator`를 사용하자

## ListIterator

- `Iterator`는 단방향으로만 이동할 수 있는 데 반해 `ListIterator` 는 양방향으로 이동이 가능하다.
- `List` 를 구현한 컬렉션에서만 사용할 수 있다.

## Arrays

### `setAll()`

배열을 채우는데 사용할 함수형 인터페이스를 매개변수로 받는다.

### `asList()`

- 배열을 리스트에 담아서 반환한다.
- 매개변수의 타입이 가변인수라서 배열을 생성없이 저장할 요소들만 나열하는 것도 가능하다.
- 반환한 `List`의 크기를 변경할 수 없다. 즉, 추가 또는 삭제가 불가능 하다.
- 저장된 내용의 변경은 가능하다. 크기를 변경할 수 있는 `List`가 필요하다면 다음과 같이 하면 된다.

```java
List list = new ArrayList(Arrays.asList(1, 2, 3, 4, 5));
```

