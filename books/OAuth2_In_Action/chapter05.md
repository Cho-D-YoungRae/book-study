# Chapter 05. 간단한 OAuth 인가 서버

## 5.2 클라이언트 인가

### 5.2.2 클라이언트 인가

#### 사용자 인증

- 사용자를 인증하는 데에는 복잡한 작업 대부분을 처리할 수 있는 다양한 미들웨어를 이용하는 등 여러 가지 방법을 사용할 수 있음
- 인가 서버가 리소스 소유자 인증을 처리하지만, OAuth 프로토콜에서는 리소스 소유자를 인증하는 방법에 대해서는 구체적으로 정의하지 않음
- OpenID Connect 와 같은 OAuth 기반의 인증 프로토콜을 사용해 리소스 소유자가 인가 서버에 로그인하게 만들 수도 있음

## 5.3 토큰 발급

### 5.3.1 클라이언트 인증

- 클라이언트 ID 와 클라이언트 시크릿을 HTTP Basic 인증으로 전달하는 방법
- 폼의 파라미터로 전달하는 방법
