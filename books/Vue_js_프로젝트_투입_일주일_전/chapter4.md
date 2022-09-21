# Vue Router 설정

## 4.3 Lazy Load 적용하기 (비동기 컴포넌트)

- Vue CLI를 통해 빌드하면 소스 코드가 하나의 파일로 합쳐지는데, 큰 프로젝트에서는 전체 소스 코드가 하나로 합쳐지면서 파일 용량이 매우 커짐
  - 이 때문에 사용자가 웹사이트에 처음 접속했을 때 한 번에 큰 파일을 다운로드 받느라 초기 랜더링 시간이 오래 거릴 수 있음
  - 한번 로드되고 나서는 페이지 전환이 매우 빠른 이점이 있을 수는 있으나...
- Lazy Load 는 리소스를 컴포넌트 단위로 분리하여 컴포넌트 혹은 라우터 단위로 필요한 것들만 다운받을 수 있게 하는 방법
- prefetch 기능이 사용되는 것 (Vue CLI 에서 prefetch 기능은 기본값 true)
  - 미래에 사용될 수 있는 리소스를 캐시에 저장함으로써, 사용자가 접속했을 때 빠르게 리소스를 내려줄 수 있음
  - 비동기 컴포넌트로 정의된 모든 리소스를 당장 사용하지 않더라도 캐시에 담는 비용 발생
  - 별도로 분리된 chunk 파일 각각에 대한 request가 발생하고, 각각의 파일을 다운로드 받아서 캐시에 저장 -> 잘못 사용하면 오히려 랜더링 시간이 늘어남

### prefetch 끄기

```javascript
module.exports = {
  chainWebpack: config => {
    config.plugins.delete('prefetch'); //prefetch 삭제
  }
}
```

```javascript
// 최신 설정
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack: config => config.plugins.delete('prefetch')
})

```

prefetch 기능을 삭제해도, 비동기 컴포넌트를 사용할 수 있음

```javascript
import(/* webpackPrefetch: true */ './views/About.vue');
```
