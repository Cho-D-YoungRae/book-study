# Chapter 05. 레디스를 캐시로 사용하기

## 캐시란?

> 캐시란 데이터의 원본보다 더 빠르고 효율적으로 액세스 할 수 있는 임시 데이터 저장소

- 애플리케이션이 다음 조건을 만족시킨다면 캐시를 도입했을 때 성능을 효과적으로 개선 가능
  - 원본 데이터 저장소에서 원하는 데이터를 찾기 위해 **검색하는 시간이 오래 걸리거나**, 매번 **계산을 통해** 데이터를 가져와야 함
  - 캐시에서 데이터를 가져오는 것이 원본 데이터 저장소 데이터를 요청하는 것보다 **빨라야 함**
  - 캐시에 저장된 데이터는 **잘 변하지 않는** 데이터
  - 캐시에 저장된 데이터는 **자주 검색**되는 데이터
- 장점
  - 원본 데이터 저장소에서 데이터를 읽는 **커넥션을 줄일 수 있음**
  - 캐시를 적절하게 배치함으로써 **애플리케이션의 확장 가능**
  - 원본 데이터 저장소에서 데이터를 가져올 떄 CPU 와 메모리 등의 리소스를 많이 사용했다면 캐시를 사용함으로 **애플리케이션 리소스를 줄일 수 있음**
  - 서비스의 구성에 따라 다르겠지만, 중요한 데이터를 캐시에 올려두고 사용할 떄 원본 데이터 저장소에 장애가 발생해 접근할 수 없는 상황이 발생하더라도 캐시에서 데이터를 가지고 올 수 있기 때문에 **장애 시간을 줄일 수 있음**

## 캐시로서의 레디스

- 사용이 간단
- 데이터를 검색하고 반환하는 것이 빠름
- 고가용성 기능 지님
- 캐시의 스케일 아웃 쉬움

## 캐싱 전략

### 읽기 전략 - look aside

> 가장 일반적으로 배치하는 방법

1. 애플리케이션은 찾고자 하는 데이터가 먼저 캐시에 있는지를 확인
2. 캐시에 데이터가 있으면 캐시에서 데이터를 읽어옴 - 캐시 히트
3. 찾고자 하는 데이터가 없을 때는 원본 데이터베이스에서 데이터를 읽어옴 - 캐시 미스

- 레디스에 문제가 생겨 접근을 할 수 없는 상황이 발생하더라도 바로 서비스 장애로 이어지지 않고 데이터베이스에서 데이터를 가져올 수 있음
  - 기존에 애플리케이션에서 레디스를 통해 데이터를 가져오는 연결이 매우 많았다면 모든 커넥션이 한꺼번에 원본 데이터베이스로 몰려 많은 부하를 발생시키고, 이로 인해 원본 데이터베이스의 응답이 느려지거나 리소스를 많이 차지하는 등의 이슈 발생해 애플리케이션의 성능에 영향을 미칠 수 있음
- 기존에 사용 중인 서비스에 처음 레디스를 투입하거나 데이터베이스에만 새로운 데이터를 저장한다면 애플리케이션은 데이터를 찾기 위해 레디스에 매번 먼저 접근할 것이고, 그때마다 캐시 미스가 일어나 데이터베이스와 레디스에 재접근 하는 과정을 통해 지연이 초래돼 성능에 영향을 미칠 수 있음
  - 캐시 워밍 작업을 해줄 수 있음

### 쓰기 전략과 캐시의 일관성

#### 1. write through

> 데이터베이스에 업데이트할 때마다 매번 캐시에도 데이터를 함께 업데이트시키는 방식

- 캐시는 항상 최신 데이터를 가지고 있을 수 있다는 장점
- 데이터는 매번 2개의 저장소에 저장돼야 하기 때문에 데이터를 쓸 때마다 시간이 많이 소요될 수 있다는 단점
- 다시 사용되지 않을 데이터일 수도 있는데 무조건 캐시에도 저장될 수 있으므로 이 방식을 사용할 경우 데이터를 저장할 때 만료시간을 사용할 것을 권장

#### 2. cache invalidation

> 데이터베이스에 값을 업데이트할 때마다 캐시에서는 데이터를 삭제하는 전략

- 저장소에서 특정 데이터를 삭제하는 것이 새로운 데이터를 저장하는 것보다 훨씬 리소스를 적게 사용하기 때문에 `wrtie through` 단점을 보완한 방법

#### 3. write behind(write back)

> 먼저 데이터를 빠르게 접근할 수 있는 캐시에 업데이트한 뒤, 이후에는 건수나 특정 시간 간격 등에 따라 비동기적으로 데이터베이스에 업데이트

- 쓰기가 빈번하게 발생하는 서비스라면 고려
- 데이터베이스에 대량의 쓰기 작업이 발생하면 이는 많은 디스크 I/O 를 유발해 성능 저하가 발생 가능
- 저장되는 데이터가 실시간으로 정환한 데이터가 아니어도 되는 경우 유용
- 캐시에 문제가 생겨 데이터가 날아갈 경우 특정 시간 동안의 데이터가 날아갈 수 있는 위험성