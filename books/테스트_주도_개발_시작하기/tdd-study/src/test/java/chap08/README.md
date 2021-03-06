# Chapter 8. 테스트 가능한 설계

## 테스트가 어려운 코드

### 하드 코딩된 경로

- 해당 경로에 반드시 파일이 위치해야 함
- IP 주소, 포트 번호 등도

### 의존 객체를 직접 생성

> 테스트 대상 객체 내에서 의존 객체를 직접 생성

- 이 코드를 테스트 하려면 의존 객체가 올바르게 동작하는 필요한 모든 환경을 구성해야 함

### 정적 메서드 사용

- 해당 정적 메서드가 외부 서버와 통신 등을 하는 경우 이 코드를 테스트 하려면 외부 서버가 필요

### 실행 시점에 따라 달라지는 결과

- 날짜 사용
- Random 사용
- ...

### 역할이 섞여 있는 코드

- 테스트에 무관한 대역을 만들어야 할 수 있음

### 그 외 테스트가 어려운 코드

- 메서드 중간에 소켓 통신 코드가 포함
- 콘솔에서 입력을 받거나 결과를 콘솔에 출력
- 테스트 대상이 사용하는 의존 대상 클래스나 메서드가 final
  - 대역으로 대체가 어려울 수 있음
- 테스트 대상의 소스를 소유하고 있지 않아 수정이 어려움

## 테스트 가능한 설계

테스트가 어려운 주된 이유는 의존하는 코드를 교체할 수 있는 수단이 없기 때문이다.  
상황에 따라 알맞은 방법을 적용하면 의존 코드를 교체할 수 있게 만들 수 있다.

### 하드 코딩된 상수를 생성자나 메서드 파라미터로 받기

- 상수를 교체할 수 있는 기능을 추가
  - 생성자
  - 세터
  - 메서드 파라미터

### 의존 대상을 주입 받기

- 의존 대상을 주입받을 수 있게 되면 실제 구현 대신 대역을 사용 가능

### 테스트하고 싶은 코드를 분리하기

- 기능의 일부만 테스트하고 싶다면 해당 코드를 별도 기능으로 분리해서 테스트를 진행 가능

### 시간이나 임의 값 생성 기능 분리하기

- 별도로 분리하고 분리한 대상을 주입할 수 있게 변경하면 테스트를 원하는 상황으로 쉽게 제어 가능

### 외부 라이브러리는 직접 사용하지 말고 감싸서 사용하기

> 외부 라이브러리가 정적 메서드를 제공하는 경우 등

- 대역으로 대체하기 어려운 외부 라이브러리가 있다면 외부라이브러리를 직접 사용하지 말고 외부 라이브러리와 연동하기 위한 타입을 따로 만들기
- 테스트 대상은 분리한 타입을 사용하도록