# Chapter 03. 간단한 OAuth 클라이언트

## 3.2 인가 코드 그랜트 타입(Ahthorization Code Grant Type)을 이용해 토큰 얻기

### 3.2.2 인가 요청에 대한 응답 처리

- authorization_code 를 토큰 엔드 포인트로 전송할 때 redirect_url 포함해야 하는 이유
  - OAuth 스펙에 따르면, 인가 요청에 리다이렉트 URI 가 포함돼 있었다면 토큰을 요청할 때도 그것과 동일한 URI 를 함께 전달해야 함
  - 그렇게 함으로써 공격자가 침해된 리다이렉트 URI 와 세션에 인가 코드를 삽입하는 것을 방지할 수 있음

### 3.2.3 크로스 사이트 공격을 방지하기 위한 state 파라미터 추가

- state 가 없다면 누군가 `http://localhost:9000/callback(redirect_uri)` 를 방문할 때마다 입력 값을 가져와 그것을 다시 서버 인가 서버에 전달하려 할 것임
- 이로 인해 공격자가 클라이언트를 이용해 유효한 인가 코드를 얻기 위한 공격을 수행할 수 있음
- 그로 인해 클라이언트와 서버의 자원이 낭비되고 잠재적으로는 클라이언트가 실제로 요청하지도 않은 토큰을 가져오게 만들수도 있음
- 이를 방지하기 위해 state 라는 optional OAuth 파라미터를 이용할 수 있음
- state 값은 redirect_uri 가 호출됐을 때 state 값을 확인할 수 있도록 애플리케이션에 저장돼야 함
- 인가 엔드포인트로 전달되는 파라미터에 state 파라미터를 추가해야 함

## 3.3 보호된 리소스에 접근하기 위한 토큰 사용

### Bearer 토큰을 전달하는 방법

> OAuth Bearer 토큰의 사용법을 정의한 스펙에서 설명하는 세 가지 방법의 토큰 전달 방법  
> 다른 두 가지 방법은 한계가 있기 때문에 가능하면 Authorization 헤더를 이용하는 것을 권장

1. HTTP Authorization 헤더를 이용하는 방법
   - 세 가지 방법 중 가장 큰 유연성과 보안성 제공
   - 일부 클라이언트에서는 사용하기 어려울 수 있다는 단점
2. 폼 인코딩된 요청 파라미터로 전달하는 방법
   - 보호된 리소스의 입력 유형이 폼 인코딩된 파라미터로 제한됨
   - POST 전송만 가능
   - API 가 그렇게 사용하도록 정해졌다면, 쿼리 파라미터로 전달할 때 발생할 수 있는 동일한 보안 이슈는 발생하지 않음
3. URL 인코딩된 쿼리 파라미터로 전달하는 방법
   - 액세스 토큰이 URL 쿼리에 포함되기 때문에 실수로 서버의 로그에 액세스 토큰 값이 유출될 수 있음