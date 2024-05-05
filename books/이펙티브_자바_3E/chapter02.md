# 2장. 객체 생성과 파괴

## 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라

### 장점

1. 이름을 가질 수 있다
   - 생성자는 이름을 가질 수 없다
2. 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다
   - ie) Boolean.valueOf(boolean)
3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다
4. 입력 매개변수에 따라 매번 다른 클래스 객체를 반환할 수 있다
5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다

### 단점

1. 상속을 하려면 public 이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다
   - 이 제약은 상속보다 컴포지션을 사용하도록 유도하고 불변 타입으로 만들려면 이 제약을 지켜야 한다는 점에서 오히려 **장점**으로 받아들일 수도 있다
2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다
   - 생성자처럼 API 설명에 명확히 드러나지 않으니 사용자는 정적 팩터리 메서드 방식 클래스를 인스턴스화할 방법을 알아내야 한다

### 정적 팩터리 메서드에 흔히 사용하는 명명 방식

- `from`: 매개변수 하나를 받아서 해당 타입의 인스턴스를 반환하는 형 변환 메서드
  - `Date d = Date.from(instant);`
- `of`: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드
  - `Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);`
- `valueOf`: from 과 of 의 더 자세한 버전
  - `BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);`
- `instance` 혹은 `getInstance`: (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만 같은 인스턴스임을 보장하지는 않는다.
  - `StackWalker luke = StackWalker.getInstance(options);`
- `create` 혹은 `newInstance`: instance 혹은 getInstance 와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.
  - `Object newArray = Array.newInstance(classObject, arrayLen);`
- `getType`: getInstance 와 같으나, 새로 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type" 은 팩터리 메서드가 반환할 객체의 타입이다.
  - `FileStore fs = Files.getFileStore(path);`
- `newType`: newInstance 와 같으나, 새로 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type" 은 팩터리 메서드가 반환할 객체의 타입이다.
  - `BufferedReader br = Files.newBufferedReader(path);`
- `type`: getType 과 newType 의 간결한 버전
  - `List<Complaint> litany = Collections.list(legacyLitany);`
