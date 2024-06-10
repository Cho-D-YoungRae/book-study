# 아이템 8. finalizer와 cleaner 사용을 피하라

- 자바의 두 가지 객체 소멸자: `finalizer`, `cleaner`
- `finalizer` 는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요
- `cleaner` 는 `finalizer` 보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요
- 즉시 수행된다는 보장이 없음
- 자바 언어 명세는 `finalizer`나 `cleaner`의 수행 시점뿐 아니라 수행 여부조차 보장하지 않음
