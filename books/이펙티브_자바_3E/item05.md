# 아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

- 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다
- 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식을 사용
- 의존 객체 주입이라 하는 이 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 기막히게 개선해준다
