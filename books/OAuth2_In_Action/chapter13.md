# Chapter 13. OAuth2.0 에서의 사용자 인증

- OAuth 2.0 스펙은 웹 기반의 애플리케이션과 API 네트워크 간의 인가 결정을 전달하는 데 유용한 권한 위임 프로토콜
- OAuth 2.0 은 인증 프로토콜이 아님

## 13.1 OAuth 2.0 이 인증 프로토콜이 아닌 이유

- `인증`
  - 현재 사용자가 누구인지 그리고 현재 사용자가 애플리케이션을 사용하고 있는지 여부를 애플리케이션에게 알려주는 것
  - 일반 적으로 자격 증명(사용자 이름과 비밀번호와 같은)을 애플리케이션에 제공함으로써 사용자가 현재 주장하는 자신임을 알리는 보안 아키텍처의 일부분이라고 할 수 있음
- OAuth 2.0 은 자체적으로 사용자에 대해 어떤 것도 알려주지 않을 뿐만 아니라 사용자가 자신의 존재를 어떻게 증명했는지 또는 존재했는지에 대해서도 알려주지 않음

### 13.1.1 인증 VS 인가

- OAuth 2.0 은 여러 가지 보안 아키텍처를 위한 다재다능한 재료
  - RESTful API 와 웹 리소스를 보호하는 데 사용할 수 있음
  - 웹 서버상의 클라이언트 뿐만 아니라 네이티브 애플리케이션도 사용 가능
  - 제한된 권한 위임을 위해 사용자가 사용하거나 백 채널 데이터를 전송하기 위해 신뢰된 애플리케이션이 사용할 수도 있음
  - 식별과 인증 API 를 만드는 데에도 사용될 수 있음
- 인증이 적절하고 안전하게 작동되도록 몇 가지 중요한 구성 요소와 과정이 올바른 방법으로 통합돼야 하며, 그런 구성 요소와 과정에는 다양한 선택사항이 있음
  - 사용자는 기기를 휴대하거나, 비밀번호를 암기하거나, 생체 인식 샘플을 제시하거나, 다른 원격 서버에 로그인 할 수 있음을 증명하거나 그 외 다른 방법이 요구될 수 있음
  - 이를 위해 시스템은 PKI(Public Key Infrastructure) 와 인증서, 연합된 신뢰 프레임워크, 웹 브라우저 쿠키 또는 심지어 전용 하드웨어와 소프트웨어를 사용할 수도 있음 =
  - OAuth 2.0은 이와 같은 기술 요소 중 하나일 수 있지만, 그렇지 않을 수도 있음
  - 다른 요소 없이 OAuth 2.0 은 사용자 인증을 수행하기에는 충분하지 않음
- OAuth 로도 OAuth 기반의 인증 프로토콜을 만들기 위한 패턴이 있음
- OpenID 커넥트처럼 여러 서로 다른 제공자를 위해 오픈 표준으로 만들어진 것도 있음

## 13.3 OAuth 2.0 인증 방법

- OAuth 2.0 프로토콜을 통해 사용자의 자격 증명이 클라이언트 애플리케이션(RP)에 전달되는 지점이 없다는 것을 활용할 수 있음
- 인가 프로토콜 위에 인증 프로토콜을 구축함으로써 사용자 동의가 실시간으로 수행될 수 있음

## 13.4 인증을 위해 OAuth 2.0 을 사용하는 데 있어서 일반적인 함정

### 13.4.1 인증의 증거로서 액세스 토큰

- 토큰 자체는 인증과 관련된 어떤 정보도 전달하지 않으며, 실제로 인증이 수행됐는지 여부를 나타내지 않음
- 클라이언트가 토큰에서 어떤 사용자 정보를 추출할 필요가 있을 수 있지만, OAuth 2.0 에서는 액세스 토큰이 클라이언트에게 그 내용이 불투명하도록 설계됨
- 토큰은 리소스 서버에 전달되기 때문에 그 중 일부는 식별과 관련이 없을 수도 있고, 리소스 서버가 사용자의 로그인 이벤트에 대한 민감한 정보를 아는 것이 잠재적으로 문제가 될 수 있음
  - 이와 같은 한계를 극복하기 위해 OpenID 커넥트와 페이스북 커넥트의 Signed Response 와 같은 프로토콜에서는 클라이언트에게 인증 정보를 직접 전달하기 위해 액세스 토큰과 더불어 또 하나의 토큰을 추가로 제고 
  - 즉, 액세스 토큰은 기존의 OAuth 와 동일하게 클라이언트에게 불투명하게 만들고, 인증 토큰은 클라이언트가 제대로 해석할 수 있도록 정의해 만듦

### 13.4.2 인증의 증거로서의 보호된 API 에 대한 접근

- 클라이언트에게 누가 토큰을 발급한 것인지 알려주는 보호된 리소스를 정의하면 어떻게 될까?
  - 액세스 토큰을 사용자 속성 정보와 교환할 수 있다면 유효한 액세스 토큰을 소유하고 있다는 것만으로 사용자가 인증됐음을 증명하는 데 충분하다고 생각할 가능성이 있음
  - 인가 서버에서 인증되는 사용자의 컨텍스트 내에서 새로 만들어진 액세스 토큰인 경우에만 해당
- 인가 이벤트와 리소스 서버에 대한 토큰 사용 간의 시간 간격이 큰 경우 문제가 발생할 수 있음
  - OAuth 2.0 은 사용자가 현재 없을 때 클라이언트나 인가 서버 모두에서 제대로 동작할 수 있지만, 인증 프로토콜의 대부분이 사용자가 있는지 여부를 확인하기 위한 것이기 때문에 클라이언트는 기능 중인 액세스 토큰의 존재 여부에 의존해 사용자가 있는지를 결정할 수 없음
  - 클라이언트는 토큰이 상대적으로 최신의 것이라는 것을 알 때만 사용자 정보를 확인함으로써 문제를 해결할 수 있음
  - 액세스 토큰만 있으면 사용자 API에 접근할 수 있기 때문에 액세스 토큰이 있다고 해서 사용자가 현재 있다고 가정하면 안됨
  - 서명된 요청이나 ID 토큰의 경우처럼 IdP 에서 직접적으로 받아들여진다는 것을 직접 클라이언트에게 알려줌으로써 문제를 해결할 수도 있음
    - 액세스 토큰과는 다른 라이프사이클을 가지며, 리소스 서버는 그 안에 포함된 추가 정보를 이용할 수 있음

### 13.4.3 액세스 토큰 삽입

- 클라이언트가 토큰 엔드 포인트로 요청을 보내 전달받는 것 외의 소스에서 액세스 토큰을 받아들인다면 또 다른 위협이 발생
  - 특히 URL 해시의 파라미터로 클라이언트에게 직접 토큰이 전달되는 암시적 플로를 사용하는 경우, 문제가 됨
  - 공격자는 다른 애플리케이션을 통해 또는 중간에 가로채 유효한 토큰을 확보할 수 있으며, 토큰을 요청하고 대기 중인 RP 에 그것을 전달할 수 있음
  - 이는 클라이언트가 실제 리소스 소유자의 것이 아닌 다른 리소스에 접근하도록 속일 수 있기 때문에 OAuth 2.0 에서 충분히 문제가 될 수 있음
  - 인증 프로토콜에서는 공격자가 토큰을 복사해 다른 애플리케이션에 로그인할 수 있기 떄문에 보다 엄청난 문제가 초래될 수 있음
- 애플리케이션의 구성 요소 간에 액세서 토큰을 '공유'하기 위해 액세스 토큰이 전달될 때도 문제가 발생할 수 있음
  - 액세스 토큰이 외부의 구성 요소에 의해 애플리케이션에 주입될 가능성이 있고, 애플리케이션 외부로 유출될 수 있는 길이 열리기 때문에 문제가 됨
  - 클라이어늩 애플리케이션이 액세스 토큰을 검증하지 않는다면 유효한 토큰과 공격자의 토큰을 구분할 수 있는 방법이 없음
  - 암시적 플로 대신 인가 코드 플로를 사용하면 해결 가능
  - 클라이언트는 인가 서버의 토큰 엔드 포인트에서 전달된 토큰만 받아들이면 됨
  - state 파라미터를 사용하면 클라이언트는 공격자가 추츨갛ㄹ 수 없는 값을 제공할 수 있음

### 13.4.4 수신자 제한 결여

- 대부분의 OAuth 2.0 API 는 반환되는 정보에 대해 수신자를 제한하는 어떤 메커니즘도 제공하지 않음
  - 클라이언트가 액세스 토큰이 자신에게 발급된 것인지, 다른 클라이언트를 위해 발급된 것인지 여부를 말할 수 있는 방법이 없음
  - 네이티브 클라이언트에게 다른 클라이언트의 토큰을 전달해 네이티브 클라이언트가 사용자 API 를 호출하게 만드는 것이 가능
  - 리소스 서버는 API 를 호출하는 클라이언트의 식별 정보를 모르기 때문에 토큰에 대한 검증만 수행할 수 있고, 결국 유효한 사용자 정보를 반환하게 될 것
  - 하지만 반환되는 정보는 원래 다른 클라이언트가 사용하기 위한 정보였을 수 있음
  - 심지어 사용자는 해당 네이티브 클라이언트를 인가하지 않음
- 이 문제는 클라이언트가 인식하고 자체적으로 검증할 수 있는 식별자를 인증 정보와 함께 클라이언트에게 전달함으로써 대응할 수 있음
  - 이를 통해 클라이언트는 자기자신에 대한 인증과 다른 애플리케이션에 대한 인증을 구별할 수 있음
  - 또한 OAuth 2.0 이 처리되는 동안 클라이언트에게 인증 정보를 OAuth 2.0 으로 보호된 API 와 같은 부가적인 메커니즘을 통하지 않고 직접 전달하면 클라이언트로 신뢰되지 않은 정보가 삽입돼 전달되는 것을 막을 수 있음

### 13.4.5 잘못된 사용자 정보 삽입

- 공격자가 클라이언트로부터의 호출 중 하나를 가로채거나 선택할 수 있다면 클라이언트 모르게 반환되는 사용자 정보의 내용을 변경할 수 있음
  - 예를 들면, 사용자 정보 API 의 반환 값이나 클라이언트로 전달되는 토큰의 내용을 바꿀 수 있음
  - 이를 통해 공격자는 사용자의 식별자 정보를 교체할 수 있고, 순진한 클라이언트에서 다른 사용자로 가장할 수 있음
- 이 공격을 막으려면 클라이언트로 전달되는 인증 정보를 암호화로 보호하고 검증하면 됨
  - 클라이언트와 인가 서버 사이의 모든 통신은 TLS 로 보호돼야 하고, 클라이언트는 서버에 접속할 때 서버의 인증서를 검증해야 함
  - 추가로, 서버는 사용자 정보나 토큰을 서명할 수 있고, 클라이언트는 그것을 검증할 수 있음
  - 추가 시그니처를 통해 네트워크가 탈최된다고 하더라도 사용자 정보가 변조되거나 삽입되는 것을 막을 수 있음

### 13.4.6 식별 제공자마다 다른 프로토콜 사용

- OAuth 2.0 기반 식별 API 의 가장 큰 문제점 중 하나는 식별 제공자가 표준적인 OAuth 를 기반으로 하더라도 각기 다르게 식별 API 를 구현한다는 것
  - 예를 들면, 사용자의 고유 식별자를 어느 한 식별 제공자에서는 user_id 필드에서 찾을 수 있다면, 다른 식별 제고앚에서는 sub 필드에서 찾을 수 있음
  - 해당 필드들은 모두 의미적으로 동일한 것이지만, 각기 다른 별도의 코드 처리가 필요
  - 각 식별 제공자에서 수행되는 인가는 동일한 방법으로 이뤄지지만, 인증 정보의 전달은 다를 수 있음
- 이 문제는 여기에서 논의된 인증 정보 전달을 위한 메커니즘이 OAuth 2.0 의 범위를 벗어나기 때문에 발생
  - OAuth 2.0 은 토큰의 포맷을 정의하지 않고, 액세스 토큰을 위한 공통적인 권한 범위를 정의하지 않고 리소스 서버가 액세스 토큰을 검증하는 방법을 설명하지 않음
  - 따라서 OAuth 표준 위에 구축된 표준 인증 프로토콜을 이요하면 문제가 해결될 수 있으므로 식별 정보가 어디서 전달되는 동일한 방법으로 전송

## 13.5 OpenID 커넥트: OAuth 2.0 기반의 인증과 식별 표준

> OpenID 커넥트는 2014년 2월에 OpenID 재단에서 공개한 오픈 표준으로서 OAuth 2.0 으로 사용자 인증을 수행하기 위한 상호 운용 방식을 정의한다.

- OpenID 커넥트는 OAuth 2.0 위에 직접 구축되며, OAuth 2.0 과 호환
- 정보를 암호화하고 서명해 보낼 때 JOSE 를 이용

### 13.5.1 ID 토큰

- 서명된 JWT 로서 일반적인 OAuth 액세스 토큰과 함께 클라이언트 애플리케이션에게 전달
- 액세스 토큰과 달리, ID 토큰은 RP 로 보내져 그것의 내용이 파싱됨
- ID 토큰은 토큰 엔드 포인트의 응답 데이터 내의 id_token 멤버로 액세스 토크과 함께 전달
- 두 토큰은 각각 의도된 사용자와 사용 목적이 다름
- 토큰이 하나 추가돼 기능을 확장하는 것이기 때문에 액세스 토큰은 기존 OAuth 와 동일하게 클라이언트에게 불투명한 구조로 남을 수 있고, 반면 ID 토큰은 그 내용을 해석할 수 있음
- 두 토큰은 각기 다른 라이프 사이클을 가지며, ID 토큰 유효 기간이 보다 빨리 만료
- ID 토큰은 하나의 인증 이벤트만 나타내며, 다른 외부 서비스로는 절대 전달되지 않으나, 액세스 토큰은 사용자가 떠난 이후에도 리소서 서버에 접속하는 데 사용될 수 있음
- 액세스 토큰을 사용해 처음에 누가 클라이언트를 인가했는지 물어볼 수는 있지만, 그렇다고 해서 사용자가 여전히 있다고 말하는 용도로는 사용할 수 없음
- ID 토큰 자체가 식별 제공자의 키로 서명되기 때문에 토큰이 전달될 때 사용된 TLS 전송 보호 외에도 내부의 클레임 보호를 위한 계층이 하나 더 추가됨
- 인가 서버가 ID 토큰을 서명하므로 인가 코드(c_hash), 액세스 토큰(at_hash)과는 분리된 독립적인 시그니처를 제공할 수 있음
- 클라이언트는 여전히 인가 코드와 액세스 코드의 구조는 알지 못하지만, 그것의 해시 값ㅇ르 검증함으로써 전반적인 삽입 공격을 방지할 수 있음

### 13.5.2 UserInfo 엔드포인트

- ID 토큰에는 인증 이벤트를 처리하기 위해 필요한 모든 정보가 포함되기 때문에 OpenID 커넥트 클라이언트는 성공적인 로그인을 위해 더 이상의 정보를 필요로 하지 않음
- 하지만 현재의 사용자 관련 프로파일 정보를 포함하는 보호된 리소스의 UserInfo 엔드포인트에는 액세스 토큰이 사용될 수 있음
- UserInfo 엔드 포인에 대한 요청은 앞서 설명한 인증 프로세스의 일부분은 아니며, 애플리케이션 개발자에게 인증 프로토콜을 보다 가치있게 만들어줄 수 있는 식별 정보를 제공함
- USerInfo 엔드 포인트에 대한 요청은 액세스 토큰(ID 토큰이 아님)을 이용해 간단한 HTTP GET 이나 POST 를 전달하는 것
- 일반적은 UserInfo 엔드 포인트에 대한 요청에서는 입력 파라미터를 사용하지 않음
- UserInfo 엔드포인트는 사용자별로 각각 다른 리소스 URI 를 사용하지 않고, 동일한 리소스에 대해서는 시스템의 모든 사용자가 동일한 URI 를 사용하도록 하는 설계 원칙을 따름

### 13.5.3 동적 서버 발견과 클라이언트 등록

- OAuth 2.0 은 다양한 형태로 배포가 가능하도록 설계됐지만, 배포를 하는 방식이나 각 구성 요소가 서로 인식하는 방법에 대해서는 구체적으로 지정하지 않음
- OpenID 커넥트는 클라이언트가 특정한 IdP 와 상호 작용하는 데 필요한 정보를 쉽게 얻을 수 있는 프로토콜을 정의
  - 클라이언트는 IdP의 발급자 URL 을 발견하거나 WebFinger 프로토콜을 기반으로 발급자를 발견
    - WebFinger 는 이메일 주소와 같이 친숙하게 입력할 수 있는 사용자 식별자를 이용해 발급자 URI 로 변환하기 위한 규칙 제공
    - `https://example.com/.well-known/webfinger?resource=user@example.com&rel=http://openid.net/specs/connect/1.0/issuer`
  - 발급자가 결정된 후에도 클라이언트는 여전히 인가 엔드 포인트와 토큰 엔드 포인트의 위치와 같은 서버에 대한 필수적인 정보를 필요로 함
    - 발급자 URI 에 `/.well-known/openid-configuration` 을 붙여 만든 URI 로 알아낼 수 있음
- 클라이언트가 서버에 대해 알게 되면, 서버는 해당 클라이언트에 대해 알아야 하는데, 이를 위해 OpenID 커넥트는 클라이언트가 새로운 IdP 에게 자신을 알려줄 수 있도록 클라이언트 등록 프로토콜을 정의하고 있음
  - OAuth Dynamic Client Registration 프로토콜 확장은 OpenID 커넥트와 동시에 개발됐으며, 둘은 서로 호환

### 13.5.4 OAuth 2.0 과의 호환성

- OpenID 커넥트와 OAuth2.0 은 호환

### 13.5.5 고급 기능

- OpenID 커넥트 클라이언트는 OAuth 의 전통적인 방식인 클라이언트 시크릿을 공유하는 방식 대신, **서명된 JWT 를 이용해 인증을 수행**할 수 있음
  - 클라이언트가 네트워크로 비밀번호를 전달하지 않아도 되기 때문에 보다 높은 수준의 보안을 제공할 수 있음
- OpenID 커넥트 클라이언트는 폼 파라미터를 이용하는 대신 **서명된 JWT 를 이용해 인가 엔드 포인트에 요청**을 보낼 수도 있음
- OpenID 커넥트 서버는 UserInfo 엔드포인트 등에서 **클라이언트로 전달되는 데이터를 서명하거나 암호화**해 JWT 형태로 만들 수 있음
  - ID 토큰은 서버에서 서명할 뿐만 아니라 암호화를 할 수도 있음
  - 이런 보호 기능을 사용하면 클라이언트는 TLS 연결을 통해 얻을 수 있는 보장 뿐만 아니라 전달되는 데이터가 변조되지 않았다는 것을 확신할 수 있음
- OAuth 2.0 엔드 포인트에 대한 확장으로 여러가지 파라미터가 추가됨
  - 예를 들면, **출력 유형이나 프롬프트 동작 그리고 인증 컨텍스트 레퍼런스**에 관한 것
  - OpenID 커넥트 클라이언트는 인가 서버에 OAuth 2.0 의 경우보다 세부적인 요청을 전달할 수 있음
- **OpenID 커넥트 서버(또는 다른 서드파티)가 로그인 프로세스를 시작**시킬 수 있는 방법을 제공함
  - 기존의 모든 OAuth 2.0 트랜잭션은 클라이언트 애플리케이션에 의해 시작되지만, 이 기능을 사용하면 클라이언트는 특정 IdP 에 대한 로그인 프로세스를 시작하라는 신호를 받을 수 있음
- OpenID 커넥트는 몇 가지 정보(ID 토큰)가 프런트 채널로 전달되고 그 밖의 정보(액세스 토큰)는 백 채널로 전달되는 하이브리드 플로와 같은 몇 가지 다른 토큰 획득 방법을 정의 함
  - 그런 플로를 기존 OAuth 2.0 플로의 단순한 조합이라고 생각하면 안 되고, 다른 애플리케이션을 위한 새로운 기능이라고 생각해야 함
- OpenID 커넥트는 RP 와 IdP 사이 또는 여러 RP 간의 세션 괄니를 윟나 스펙을 제공
  - OAuth 2.0 에는 인가를 위임하는 시점과 별개로 사용자가 존재한다는 개념이 없기 때문에 연합된 인증 라이프 사이클을 처리하기 위한 확장 기능이 필요
  - 사용자가 어느 한 RP 에서 로그아웃한다면 다른 곳에서도 동일하게 로그아웃되길 원할 것이고, RP 는 IdP 에게 이를 알릴 수 있어야 함
  - 그리고 다른 RP 는 로그아웃이 발생했다는 것을 IdP 로 부터 수신해야 한다면 그에 따른 적절한 처리를 수행할 수 있어야 함