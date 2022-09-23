# 7. 서버 데이터 바인딩 실습

## 7.1 서버와의 데이터 통신을 위한 API 호출 메소드 만들기

### 7.1.2 Axios 설치

```bash
npm install axios --save
```

### 7.1.3 Axios 사용법

- `axios.request(config)`
- `axios.get(url[, config])`
- `axios.delete(url[, config])`
- `axios.head(url[, config])`
- `axios.options(url[, config])`
- `axios.post(url[, data[, config]])`
- `axios.put(url[, data[, config]])`
- `axios.patch(url[, data[, config]])`

### 7.1.4 믹스인(mixins) 파일 생성

> [mixins.js](vue-project-practice/src/mixins.js)

- Vue.js 에서는 mixins 을 통해 공통 함수를 구현해서 사용할 수 있음
- mixins 파일을 Vue 컴포넌트에서 사용하기 위해서 main.js 에 등록해야함

## 7.2 서버 데이터 랜더링

### 7.2.2 서버 데이터 호출 및 리스트 랜더링

> [DataBindingList2](vue-project-practice/src/views/DataBindingList2.vue)
