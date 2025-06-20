# 단위 테스트 정리

## Chapter 1. 단위 테스트의 목표

커버리지 지표는 좋은 부정지표이지만 나쁜 긍정 지표다.

믿을 만한 방법은 스위트 내 각 테스트를 하나씩 따로 평가하는 것 뿐이다.  
테스트 스위트가 얼마나 좋은지 자동으로 확인할 수 없고, 개인의 판단에 맡겨야 한다.

성공적인 테스트 스위트는 다음과 같은 특성을 갖고 있다.

- 개발 주기에 통합돼 있다.
- 코드베이스에서 가장 중요한 부분만을 대상으로 한다.
  - 비즈니스 로직 테스트가 시간 투자 대비 최고의 수익을 낼 수 있다.
  - 다른 모든 부분은 세 가지 범주로 나눌 수 있다.
    - 인프라 코드
    - 데이터베이스 서드파티 시스템과 같은 외부 서비스 및 종속성
    - 모든 것을 하나로 묶는 코드
- 최소한의 유지비로 최대의 가치를 끌어낸다.

테스트 스위트 내에 가치 있는 테스트만 남기고 나머지는 모두 제거하라. 애플리케이션과 테스트 코드는 모두 자산이 아니라 부채다.

## Chapter 2. 단위 테스트란 무엇인가

단위 테스트 속성

- 작은 코드 조각(단위)을 검증
- 빠르게 수행
- 격리된 방식으로 처리하는 자동화된 테스트

> 격리 문제는 단위 테스트의 고전파와 런던파를 구분할 수 있게 해주는 근원적 차이

`런던파`

- 테스트 대상 시스템을 협력자에게서 격리
- 하나의 클래스가 다른 클래스 또는 여러 클래스에 의존하면 이 모든 의존성을 테스트 대역으로 대체
- 이점
  - 입자성이 좋다. 테스트가 세밀해서 한 번에 한 클래스만 확인한다.
  - 서로 연결된 클래스의 그래프가 커져도 테스트하기 쉽다.
  - 테스트가 실패하면 어떤 기능이 실패했는지 확실히 알 수 있다.
- 상호 작용을 검사한다. 호출해야 하는 메서드뿐만 아니라 호출 횟수까지 검증할 수 있다.

`고전파`

- 코드를 꼭 격리하는 방식으로 테스트해야 하는 것은 아니다.
- 단위 테스트는 서로 격리해서 실행해야 한다. -> 테스트를 어떤 순서(병렬, 순차 등)로든 가장 적합한 방식으로 실행할 수 있으며 서로의 결과에 영향을 미치지 않는다.
  - 여러 클래스가 모두 메모리에 상주하고 공유 상태에 도달하지 않는 한, 여러 클래스를 한 번에 테스트해도 괜찮다.
- 테스트 대역을 사용할 수 있지만, 보통 테스트 간에 공유 상태를 일으키는 의존성에 대해서만 사용한다.

> 공유 의존성을 대체하는 또 다른 이유는 테스트 실행 속도를 높이는 데 있다.

||격리 주체|단위의 크기|테스트 대역 사용 대상|
|---|---|---|---|
|런던파|단위|단일 클래스|불변 의존성 외 모든 의존성|
|고전파|단위 테스트|단일 클래스 또는 클래스 세트|공유 의존성|

테스트는 코드 단위를 검증해서는 안 된다. 오히려 동작의 단위, 즉 문제 영역에 의미가 있는 것, 이상적으로는 비즈니스 담당자가 유용하다고 인식할 수 있는 것을 검증해야 한다. 동작 단위를 구현하는 데 클래스가 얼마나 필요한지는 상관없다.

상호 연결된 클래스의 크고 복잡한 그래프를 테스트할 방법을 찾는 대신, 먼저 이러한 클래스 그래프를 갖지 않는 데 집중해야 한다. 목을 사용하는 것은 문제를 감추기만 할 뿐 원인을 해결하지 못한다.

고전적인 방식이면, 오작동하는 클래스를 참조하는 클라이어트를 대상으로 하는 테스트도 실패해서 하나의 버그가 전체 시스템에 걸쳐 테스트 실패를 야기할 수 있으며 이는 문제의 원인을 찾기 어렵게 할 수 있다. 하지만 테스트를 정기적으로 실행하면 마지막으로 한 수정이 무엇인지 알기 때문에 버그의 원인을 알아낼 수 있다. 또한 실패한 테스트를 모두 볼 필요는 없고 하나를 고치면 다른 것들도 자동으로 고쳐진다. 게다가 이런 테스트 스위트 전체에 걸쳐 계단식으로 실패하는 테스트는 방금 고장 낸 코드 조각이 전체 시스템에 의존한다는 것을 나타내주며 큰 가치가 있다.

통합 테스트는 공유 의존성, 프로세스 외부 의존성뿐 아니라 조직 내 다른 팀이 개발한 코드 등과 통합돼 작동하는 지도 검증하는 테스트다. 엔드 투 엔드 테스트도 통합 테스트의 일부인데, 차이점은 엔드 투 엔드 테스트가 일반적으로 의존성을 더 많이 포함하고 최종 사용자의 관점에서 시스템을 검증한다.

## Chapter 3. 단위 테스트 구조

### 3.1 단위 테스트를 구성하는 방법

AAA 패턴 사용

검증 구절 혹은 준비 구절로 구분된 여러 개의 실행 구절을 보면, 여러 개의 동작 단위를 검증하는 테스트를 뜻하고 이는 더 이상 단위 테스트가 아니라 통합 테스트다.

테스트 내 if 문 피하기

> if 문은 테스트가 한 번에 너무 많은 것을 검증한다는 표시다.

일반적으로 준비 구절이 세 구절(AAA) 중 가장 크다. 같은 테스트 클래스 내 비공개 메서드 또는 별도의 팩토리 클래스로 도출할 수 있다.

실행 구절은 보통 코드 한 줄이다. 두 줄 이상인 경우 SUT의 공개 API에 문제가 있을 수 있다.

단일 동작 단위는 여러 결과를 낼 수 있으며, 하나의 테스트로 그 모든 결과를 평가하는 것이 좋다. 클래스 내에 적절한 동등 멤버를 정의하는 것도 좋다.

종료는 일반적으로 별도의 메서드로 도출돼, 클래스 내 모든 테스트에서 재사용되고 AAA 패턴에는 이 단계를 포함하지 않는다. 대부분의 단위 테스트는 종료 구절이 필요 없다.

### 3.3 테스트 간 테스트 픽스처 재사용

테스트 생성자에서 픽스처 초기화

- 테스트간의 높은 결합도는 안티 패턴이다. 모든 테스트가 결합되게 된다.
- 테스트 가독성을 떨어뜨리는 생성자 사용. 테스트만 보고는 더 이상 전체 그림을 볼 수 없다.

테스트 클래스에 비공개 팩토리 메서드를 통해 테스트 픽스처 재사용

- 비공개 메서드를 충분히 일반화하는 한 테스트가 서로 결합되지 않는다.

테스트 픽스처 재사용 규칙에 한 가지 예외가 있다. 테스트 전부 또는 대부분에 사용되는 생성자에 픽스처를 인스턴스화할 수 있다. 이는 데이터베이스와 작동하는 통합 테스트에 종종 해당한다.

### 3.4 단위 테스트 명명법

[테스트 대상 메서드]_[시나리오]_[예상 결과]

- 동작 대신 구현 세부 사항에 집중하게끔 부추기기 때문에 분명히 도움이 되지 않는다.

표현력 있고 읽기 쉬운 테스트 이름

- 엄격한 명명 정책을 따르지 않는다. 복잡한 동작에 대한 높은 수준의 설명을 이러한 정책의 좁은 상자 안에 넣을 수 없다.
- 문제 도메인에 익숙한 비개발자들에게 시나리오를 설명하는 것처럼 테스트 이름을 짓자.
- 단어를 underscore로 구분한다.

### 3.5 매개변수화된 테스트 리팩터링 하기

매개변수화된 테스트를 사용해 유사한 테스트를 묶을 수 있다.

- 동작이 너무 복잡하면 매개변수화된 테스트를 사용하지 말라.
