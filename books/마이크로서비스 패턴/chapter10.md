# Chapter 10. 마이크로서비스 테스트 2부

## 10.1 통합 테스트 작성

- 인프라 서비스, 타 애플리케이션 서비스와 적절히 연동되었는지 확인하는 테스트
- 종단 간 테스트(end-to-end test) 처럼 전체 서비스를 실행시키지 않음
- 테스트 효과에 영향을 끼치지 않고도 테스트를 간소화하기 위해 두 가지 전략
  - 각 서비스의 어댑터(가능하면 그 어댑터의 지원 클래스까지)를 테스트
  - 계약을 활용하는 전략
