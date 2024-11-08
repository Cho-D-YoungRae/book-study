# RxJava 의 기본

## 1.1 RxJava 와 리액티브 프로그래밍

### 1.1.1 RxJava 란

- 이벤트 처리와 같은 비동기 처리에 최적화
- Reactive Streams 사양을 구현
  - `Reactive Streams`: 어떤 라이브러리나 프레임워크라도 데이터 스트림을 비동기로 처리하는 공통 매커니즘을 인터페이스로 제공

### 1.1.2 리액티브 프로그래밍이란

- 리액티브 프로그래밍이란 필요한 데이터를 직접 가져와 처리하는 것이 아니라 보내온 데이터를 받은 시점에 반응해 이를 처리하는 프로그램을 만드는 것
- 부가가치세를 계산하는 프로그램을 생각할 때, 리스너를 이용하는 것과는 차이
  - 상품가격이 변동될 때 리스너가 반응하면서 강품가격에 해당하는 부가가치세를 다시 계산해 표시하는 것은 리액티브 프로그래밍 X
  - 리스너가 반응하면서 부가가치세 항목에 새로운 데이터가 전달되고 부가가치세 항목에서 계산 프로그램을 실행해 결과를 부가가치세로 표시한다면 리액티브 프로그래밍 O
- 리액티브 프로그래밍에서 데이터를 생산하는 측은 데이터를 전달하는 것까지 책임
  - 데이터를 소비하는 측이 전달받은 데이터로 무엇을 하는지는 몰라도 됨
  - 데이터를 생산하는 측은 데이터를 소비하는 측에서 무엇을 하든지 관계가 없으므로 소비하는 측의 처리를 기다릴 필요 X
  - 데이터를 통지한 후 데이터를 소비하는 측에서 데이터를 처리하는 도중이라도 데이터를 생산하는 측은 바로 다음 데이터를 처리할 수 있음

### 1.1.4 RxJava 의 특징

- 옵저버 패턴을 잘 확장
  - 옵저버 패턴: 감시 대상 객체의 상태가 변하면 이를 관찰하는 객체에 알려주는 구조
  - 옵저버 패턴에 완료와 에러 통지를 할 수 있어서 모든 데이터 통지가 끝나거나 에러가 발생하는 시점에 별도로 대응할 수도 있음
- 쉬운 비동기 처리
  - Reactive Streams 규칙의 근간이 되는 Observable 규약이라는 RxJava 개발 가이드라인을 따른 구현이라면 직접 스레드를 관리하는 번거로움에서 해방될 뿐만 아니라 구현도 간단하게 가능
- 동기 처리나 비동기 처리나 구현 방법에 큰 차이가 없음

## 1.2 Reactive Streams

### 1.2.1 Reactive Streams

- 라이브러리나 프레임워크에 상관없이 데이터 스트림을 비동기로 다룰 수 있는 공통 매커니즘

### 1.2.2 Reactive Streams 의 구성

- 데이터를 만들어 통지하는 `Publisher`
- 통지된 데이터를 받아 처리하는 `Subscriber`
- `Publisher` 가 데이터를 통지한 후 `Subscriber` 가 이 데이터를 받을 때까지의 데이터 흐름
  - `Publisher`는 통지 준비가 끝나면 이를 `Subscriber`에 통지(onSubscribe)
    - 해당 통지를 받은 `Subscriber`는 받고자 하는 데이터 개수를 요청
    - 이때 `Subscriber`가 자신이 통지 받을 데이터 개수를 요청하지 않으면 `Publisher`는 통지해야 할 데이터 개수 요청을 기다리게 되므로 통지를 시작할 수 없음
  - 그 다음 `Publisher`는 데이터를 만들어 `Subscriber`에 통지(onNext)
  - 이 데이터를 받은 `Subscriber`는 받은 데이터를 사용해 처리 작업을 수행
  - `Publisher`는 요청받은 만큼의 데이터를 통지한 뒤 `Subscriber` 로부터 다음 요청이 올 때까지 데이터 통지 중단
  - 이후 `Subscriber`가 처리 작업을 완료하면 다음에 받을 데이터 개수를 `Publisher`에 요청
    - 이 요청을 보내지 않으면 `Publisher`는 요청 대기 상태가 돼 `Subscriber`에 데이터를 통지할 수 없음
  - `Publisher`는 `Subscriber`에 모든 데이터를 통지하고 마지막으로 데이터 전송이 완료돼 정상종료 됐다고 통지(onComplete)
  - 완료 통지를 하고나면 `Publisher`는 이 구독 건에 대해 어떤 통지도 하지 않음
  - `Publisher`는 처리 도중에 에러가 발생하면 `Subscriber`에 발생한 에러 객체와 함께 에러를 통지(onError)

| 프로토콜        | 설명                  |
|-------------|---------------------|
| onSubscribe | 데이터 통지가 준비됐음을 알리는 통지 |
| onNext      | 데이터 통지              |
| onError     | 에러(이상 종료) 통지        |
| onComplete  | 완료(정상 종료) 통지        |

|인터페이스|설명|
|---|---|
|Publisher|데이터를 생성하고 통지하는 인터페이스|
|Subscriber|통지된 데이터를 전달받아 처리하는 인터페이스|
|Subscription|데이터 개수를 요청하고 구독을 해지하는 인터페이스|
|Processor|Publisher와 Subscriber의 기능이 모두 있는 인터페이스|

### 1.2.3 Reactive Streams 의 규칙

- 기본 규칙
  - 구독 시작 통지(onSubscribe)는 해당 구독에서 한 번만 발생
  - 통지는 순차적으로
  - null 을 통지하지 않음
  - Publisher 의 처리는 완료(onComplete) 또는 에러(onError)를 통지해 종료
- Subscription 의 메서드는 동기화된 상태로 호출. 즉, Subscription 의 메서드를 동시에 호출해서는 안됨
- RxJava 를 사용할 때는 각 통지 메서드와 Subscription 의 메서드를 호출할 때 동기화가 이뤄지므로 처리 자체가 스레드 안전한지는 특히 신경

## 1.3 RxJava 기본 구조

### 1.3.1 기본 구조

| 구분                   | 생산자        | 소비자        |
|----------------------|------------|------------|
| Reactive Streams 지원  | Flowable   | Subscriber |
| Reactive Streams 미지원 | Observable | Observer   |

- Observable 과 Observer 구성은 Reactive Streams 를 구현하지 않아서 Reactive Streams 인터페이스를 사용하지 않지만, 기본적인 매커니즘은 Flowable 과 Subscriber 구성과 거의 같음
- 다만 Observable 과 Observer 구성은 통지하는 데이터 개수를 제어하는 배압 기능이 없기 때문에 데이터 개수를 요청하지 않음
  - 그러므로 Subscription 을 사용하지 않고, Disposable 이라는 구독 해지 메서드가 있는 인터페이스를 사용
  - Observable 과 Observer 같에 데이터를 교환할 때 Flowable 과 Subscriber 처럼 데이터 개수 요청은 하지 않고 데이터가 생성되자마자 Observer 에 통지

### 1.3.2 연산자

- 연산자를 설정한 시점에서 그 처리가 실행되는 것이 아니라 통지 처리가 시작되고 통지를 받는 시점에 설정한 처리가 실행
- 빌더패턴과 유사하나, RxJava 의 메서드 체인은 빌더 패턴과 달리 연산자를 설정한 순서가 실행하는 작업에 영향을 미침
- 함수형 프로그래밍의 영향을 많이 받아 메서드 대부분이 함수형 인터페이스를 인자로 받음
  - 그래서 이 함수형 인터페이스의 구현은 함수형 프로그래밍의 원칙에 따라 같은 입력을 받으면 매번 같은 결과를 반환하며, 기본으로 그 입력값과 처리는 외부 객체나 환경에 의해 변화가 일어나지 않아야 함.
- 데이터를 통지하고 소비자가 전달받기까지는 부가 자굥의 발생을 피하는 것이 좋음
  - 부가 작용: 전달받은 데이터의 상태를 변경하거나 처리 작업의 외부에 어떤 변화를 주는 것
  - 부가 작용이 발생하는 처리작업은 객체의 상태를 변경해 외부에서도 참조 가능한 객체에 어떤 변화를 주거나 파일이나 데이터베이스의 내용을 변경하는 것
- 부가 작용이 밣생하는 처리는 메서드 체인 도중이 아니라 최종으로 데이터를 받아 처리하는 소비자 측에서 이루어지는 것이 좋음
- 함수형 인터페이스 처리에 대해 부가 작용이 발생하지 않으면 여러 스레드에서 공유하는 객체가 없게 돼 스레드 안전을 보장할 수 있음
- 함수형 프로그래밍은 데이터 처리 시점에도 영향을 미침

```kotlin
import io.reactivex.rxjava3.core.Flowable
// 인자로 데이터를 직접 지정 -> 지정한 시점에 평가도니 값을 받음
val flowable: Flowable<Long> = Flowable.just(System.currentTimeMillis())
 
// 함수형 인터페이스를 인자로 지정 -> 해당 메서드가 처리를 수행하는 시점에 정의된 처리를 실행해 값을 가져옴
val flowable: Flowable<Long> = Flowable.fromCallable { System.currentTimeMillis() }
```

### 1.3.3 비동기 처리

- 데이터를 통지하는 측의 처리 범위와 데이터를 받는 측의 처리 범위를 분리할 수 있게 설계되서 각각의 처리를 서로 다른 스레드에서 실행 가능
  - 데이터를 지하는 측이 무엇을 하더라도 데이터를 받는 측의 처리가 받은 데이터에 의해서만 바뀌게 된다면 비동기로 쉽게 전환 가능
- 직접 스레드를 관리하지 않게 각 처리 목적에 맞춰 스레드를 관리하는 스케줄러 제공
- 스케줄러는 데이터를 생성해 통지하는 부분과 데이터를 받아 처리하는 부분에 지정할 수 있음
- 데이터를 통지하는 측과 받는 측은 데이터를 통지 시에만 데이터를 주고 받아야 하며 그 이외의 요인으로 서로의 행동에 영향을 주지 않아야 함]
- 비동기로 처리할 때는 생산자에서 소비자까지의 처리가 노출되지 않게 폐쇄적으로 개발하면 어느적으로 위험을 줄일 수 있고, 기본적으로는 생산자가 외부에서 데이터를 받아 데이터를 생성할 때와 소비자가 받은 데이터를 처리하고 이를 외부에 반영할 때만 외부 데이터를 참조하는 것이 좋음 