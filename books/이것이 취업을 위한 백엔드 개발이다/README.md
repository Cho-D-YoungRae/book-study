# 이것이 취업을 위한 백엔드 개발이다

- [x] 2025/03/29 ~ 2025/03/29

## 내용 정리

### Chapter 11. 상품 관리 애플리케이션에 데이터베이스 연동하기

Application Runner 를 통해서 커넥션 풀을 초기화

> 커넥션 풀은 처음 사용되는 시점에 생성됨.
> 커넥션 풀을 초기화해 주지 않는다면, 애플리케이션 시작 후 처음 받는 요청은 커넥션 풀이 생성되는 시간만큼 지연되어 클라이언트가 응답을 느리게 받게 될 것.

```java
@Bean
public ApplicationRunner runner(DataSource dataSource) {
    return args -> {
        Connection connection = dataSource.getConnection();
    }
}
```
