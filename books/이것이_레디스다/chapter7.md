# Chapter 7. 레디스 활용 사례

## 7.1 웹 애플리케이션 서버 로그 통합

> [practice code](thisisredis-practice/src/main/java/com/example/thisisredispractice/ch7/redislogger)

### 7.1.3 기능 구현

- 문자열의 값을 조회한 다음 파일에 기록하고 조회한 내용을 문자열 데이터에서 지우는데 이를 레디스로 구현하려면 get과 del이 두 번에 걸쳐서 실행되어야함
  - 동시성 관점에서 get이 실행되고 del이 실행되는 사이에 set이 실행되면 이때 저장된 로그는 사라질 수 있음
  - 따라서 데이터 누락을 방지하기 위해 get과 del명령을 하나의 트랜잭션으로 처리해야함
- 트랜잭션을 보장하기 위한 2가지 방법
  - 레디스에서 지원하는 multi명령
  - getset 명령의 인자로 빈 문자열을 저장
    - getset 명령은 하나의 명령이므로 명령 레벨에서 원자성 보장

### 7.1.5 구현의 문제점

- 문자열 append를 사용해서 구현할 경우 로그가 커질 수록 새로운 배열이 계속 생성되어서 성능이 떨어짐
  - 새로 추가되는 문자열의 크기에 따라서 새로운 배열 생성
  - 새로운 키를 생성하는 방법을 고려할 수 있으나 이 방법은 로그를 저장하는 `LogReceiver`가 어떤 이유로 종료됐다가 다시 시작되면 마지막에 읽은 키 값을 알 수 없기 때문에 다음에 읽어야 할 키를 알지 못함
  - 리스트 데이터형을 사용할 수 있음

## 7.2 페이지 방문 횟수 저장

> [practice code](thisisredis-practice/src/main/java/com/example/thisisredispractice/ch7/visitcount)

### 7.2.5 요구 사항 확장

#### 추가 기능의 문제점과 새로운 구현

- 키만을 사용하여 날짜 데이터를 저장할 경우 시작 날짜와 종료 날짜를 확인할 수 없음
  - 해시 데이터를 사용하여 필드명으로 날짜를 사용하면 해결 가능

## 7.3 장바구니 정보

> [practice code](thisisredis-practice/src/main/java/com/example/thisisredispractice/ch7/cart)

### 7.3.3 기능 구현

- keys 명령은 서비스 중인 레디스에 사용하지 않도록 권장
  - 강력하고 편리한  기능이지만 성능의 저하라는 단점이 있음

### 7.3.5 구현의 문제점

- 단일 명령어를 여러 번에 나누어 전송하는 것은 불필요한 네트워크 왕복 시간 포함
  - pipeline 등 이용

## 7.4 '좋아요' 처리하기

> [practice code](thisisredis-practice/src/main/java/com/example/thisisredispractice/ch7/like)

### 7.4.5 더 살펴볼 내용

`getLikeCountList()` 메서드의 구현을 보면 파이프라인을 통해서 게시물 개수와 동일한 횟수의 scard 명령을 호출한다. 하지만 파이프라인으로 명령하더라도 속도의 한계가 있기 때문에 한 화면에 출력하는 게시물 수를 조절하는 등으로 속도 문제를 해결할 수 있다.

## 7.5 실시간 순 방문자 집계

> [practice code](thisisredis-practice/src/main/java/com/example/thisisredispractice/ch7/uniquevisit)

## 7.6 최근 조회 상품 목록

> [practice code](thisisredis-practice/src/main/java/com/example/thisisredispractice/ch7/recentview)
