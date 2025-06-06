# Chapter 6. 서비스 디스커버리

> 서비스 디스커버리가 마이크로서비스와 클라우드 기반 애플리케이션에서 중요한 이유

- `수평 확장`: 대개 이 패턴은 애플리케이션 아키텍처에서 클라우드 서비스 내 더 많은 서비스 인스턴스 및 컨테이너를 추가하는 것과 같은 조정을 요구한다.
- `회복성`: 이 패턴은 비즈니스에 영향을 주지 않고 아키텍처와 서비스 내부의 문제로 충격을 흡수하는 능력을 의미한다. 마이크로서비스 아키텍처에서는 한 서비스의 문제가 전체로 확산되어 서비스 소비자에게 전파되는 것을 막는데, 매우 신중해야 한다.

## 6.2 클라우드에서 서비스 디스커버리

> 클라우드 기반 마이크로서비스 환경에서 다음과 같은 특성을 가진 서비스 디스커버리 메커니즘 사용

- `고가용성`: 서비스 디스커버리는 서비스 디스커버리 클러스터 노드 간 서비스 검색 정보가 공유되는 핫(hot) 클러스터링 환경을 지원할 수 있어야 한다. 한 노드가 가용하지 않으면 클러스터 내 다른 노드가 그 역할을 대신해야 한다. 클러스터는 서버 인스턴스들의 그룹으로 정의할 수 있다. 이 경우 모든 인스턴스는 고가용성, 안정성, 확장성을 제공하고자 동일한 구성을 갖고 협업한다. 로드 밸런서와 통합된 클러스터는 서비스 중단을 방지하는 페일오버와 세션 데이터를 저장하는 세션 복제 기능을 제공할 수 있다.