# 11. Vuex(v4.x)

## 11.2 Vuex 란?

- 컴포넌트 수가 많아지면 컴포넌트 간의 데이터 전달이 어려워지는 문제 해결
- 중앙집중식 저장소 역할

## 11.2 Vuex 설치

```bash
npm install vuex@next --save
```

## 11.3 시작하기

- Vuex 저장소가 일반 전역 개체와 다른 점
  1. Vuex store 는 반응형
     - state 를 검색할 때 값의 변경 여부를 바로 알 수 있음
  2. 저장소의 상태를 직접 변경 불가능
    - 명시적인 커밋을 이용하여 변경 가능
    - 모든 상태에 대한 추적이 가능한 기록이 남음
    - 툴을 사용하여 앱을 더 잘 이해할 수 있음

## 11.4 State

- 프로젝트 전체에서 공통으로 사용할 변수를 정의하는 곳
- computed 속성을 이용해서 변경사항을 항상 추적 가능

## 11.5 Getters

- State 의 변수를 쉽게 가져올 수 있도록

## 11.6 Mutations

- Vuex 에서는 state 에 정의된 변수를 직접 변경하는 것을 허용하지 않음
- 반드시 `mutations` 을 이용해서 변경
- `mutations` 는 비동기(async)가 아니라 동기(sync) 처리를 통해 state 에 정의된 변수의 변경사항을 추적할 수 있게 함

## 11.7 Actions

- `mutations` 와 매우 유사한 역할
- `actions` 을 통해 `mutations` 에 정의된 함수 실행
- `actions` 에 정의된 함수 안에서는 여러 개의 `mutations` 실행 가능
- `mutations`와 달리 비동기 작업 가능
