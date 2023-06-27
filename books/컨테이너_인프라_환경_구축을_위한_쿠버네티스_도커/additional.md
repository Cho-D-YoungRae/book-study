# 쿠버네티스 추가 학습

## 오브젝트(Object)

- 쿠버네티스 시스템에서 영속성을 가지는 엔티티
- 클러스터의 상태를 나타내기 위해 이용
- 하나의 “의도를 담은 레코드”
- 오브젝트를 생성하게 되면, 쿠버네티스 시스템은 그 오브젝트 생성을 보장하기 위해 지속적으로 작동
- 오브젝트를 생성함으로써, 크러스터의 워크로드를 어떤 형태로 보이고자 하는지에 대해 효과적으로 쿠버네티스 시스템에 전함 -> 클러스터에 대해 의도한 상태
- 거의 모든 쿠버네티스 오브젝트는 오브젝트 구성을 결정해주는 두 개의 중첩된 오브젝트 필드 포함
  - `spec`: 을 가진 오브젝트는 오브젝트를 생성할 때 리소스에 원하는 특징(의도한 상태)에 대한 설명을 제공해서 설정
  - `status`: 는 쿠버네티스 시스템과 컴포넌트에 의해 제공되고 업데이트된 오브젝트의 현재 상태를 설명. 쿠버네티스 컨트롤 플레인은 모든 오브젠트의 실제 상태를 사용자가 의도한 상태와 일치시키기 위해 끊임없이 그리고 능동적으로 관리

## 커스텀 리소스(Custom Resources)

- `리소스`는 쿠버네티스 API 에서 특정 종류는 API 오브젝트 모음을 저장하는 엔드포인트
  - ex) 빌트인 파드 리소스에는 파드 오브젝트 모음이 포함
- `커스텀 리소스`는 쿠버네티스 API 의 익스텐션
- 기본 쿠버네티스 설치에서 반드시 사용할 수 있는 것은 아님
- 커스텀 리소스가 설치되면 사용자는 파드와 같은 빌트인 리소스와 마찬가지로 kubectl 을 사용하여 해당 오브젝트를 생성하고 접근 가능

### 커스텀 컨트롤러(Custom controllers)

- 자체적으로 커스텀 리소스를 사용하면 구조화된 데이터를 저장하고 검색 가능
- 커스텀 리소스를 커스텀 컨트롤러와 결합하면, 커스텀 리소스가 진정한 선언적(declarative) API 를 제공하게 됨
- `오퍼레이터 패턴`은 커스텀 리소스와 커스텀 컨트롤러를 결합

### 커스텀리소스데피니션(CustomResourceDefinitions)

- 커스텀리소스데피니션 API 리소스를 사용하면 커스텀 리소스를 정의할 수 있음
- CRD 오브젝트를 정의하면 지정한 이름과 스키마를 사용하여 새 커스텀 리소스가 만들어짐
- 쿠버네티스 API는 커스텀 리소스의 스토리지를 제공하고 처리
- CRD 오브젝트의 이름은 유효한 DNS 서브도메인 이름이어야 함
- 커스텀 리소스를 다루기 위해 자신의 API 서버를 작성하지 않아도 되지만, API server aggregation 보다 유연성이 떨어짐

## 컨트롤러(Controllers)

- `컨트롤 루프`: 시스템 상태를 조절하는 종료되지 않는 루프
- 클러스터의 상태를 관찰한 다음 필요한 경음, 필요한 경우에 생성 또는 변경을 요청하는 `컨트롤 루프`
- 현재 클러스터 상태를 의도한 상태에 가깝게 이동

## 오퍼레이터 패턴(Operator pattern)

- `오퍼레이터(Operator)`는 커스텀 리소스를 사용하여 애플리케이션 및 해당 컴포넌트를 관리하는 쿠버네티스의 소프트웨어 익스텐션
- 쿠버네티스에서 제공하는 리소스 이외에 사용자가 새롭게 정의한 리소스를 관리해주는 패턴
- `컨트롤 루프`를 따름

### 오퍼레이터 배포

- 가장 일반적인 방법은 CRD 와 그와 연관된 컨트롤러를 클러스터에 추가하는 것
- 컨트롤러는 일반적으로 컨트롤 플레인 외부에서 작동
  - 클러스터에서 컨트롤러를 디플로이먼트로 실행할 수 있음

## 참고

- [오브젝트 문서](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/)
  - [한글](https://kubernetes.io/ko/docs/concepts/extend-kubernetes/api-extension/custom-resources/)
- [커스텀 리소스 문서](https://kubernetes.io/docs/concepts/extend-kubernetes/api-extension/custom-resources/)
  - [한글](https://kubernetes.io/ko/docs/concepts/extend-kubernetes/api-extension/custom-resources/)
- [컨트롤러](https://kubernetes.io/docs/concepts/architecture/controller/)
  - [한글](https://kubernetes.io/ko/docs/concepts/architecture/controller/)
- [오퍼레이터 패턴](https://kubernetes.io/docs/concepts/extend-kubernetes/operator/)
  - [한글](https://kubernetes.io/ko/docs/concepts/extend-kubernetes/operator/)
  - <https://velog.io/@kubernetes/Kubernetes-Operator-란>
