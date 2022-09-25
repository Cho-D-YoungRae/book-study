# 9. Reusability & Composition

## 9.1 Composition API

- Vue 의 '프로젝트 규모가 커질수록 관리하기 힘들다' 는 그동안의 단점을 해결 

### 9.1.2 setup

> [Calculator](vue-project-practice/src/views/Calculator.vue)  
> [CompositionAPI](vue-project-practice/src/views/CompositionAPI.vue)  
> [CompositionAPI2](vue-project-practice/src/views/CompositionAPI2.vue)  
> [CompositionAPI3](vue-project-practice/src/views/CompositionAPI3.vue)  
> [CompositionAPI4](vue-project-practice/src/views/CompositionAPI4.vue)

- setup 은 컴포지션 API 를 구현하는 곳
- 외부 function 에서 반응형 변수를 사용하기 위해 `toRefs`

### 9.1.3 Lifecycle Hooks

| Options API     | Hook inside setup() |
|-----------------|---------------------|
| beforeCreate    ||
| created         ||
| beforeMount     | onBeforeMount       |
| mounted         | onMounted           |
| beforeUpdate    | onBeforeUpdate      |
| updated         | onUpdated           |
| beforeUnmount   | onBeforeUnmount     |
| unmounted       | onUnmounted         |
| errorCaptured   | onErrorCaptured     |
| renderTracked   | onRenderTracked     |
| renderTriggered | onRenderTriggered   |

setup() 은 컴포넌트 라이프사이클의 beforeCreate 와 created 훅 사이에서 실행되기 때문에, onBeforeCreate, onCreated 훅은 필요가 없고, setup() 안에서 코드를 작성하면 됨

```vue
<!--Example-->
<script>
export default {
  setup() {
    // mounted
    onMounted(() => {
      console.log('Component is mounted!');
    });
  }
}
</script>
```

### 9.1.4 Provide/Inject

> [CompositionAPIInject](vue-project-practice/src/views/CompositionAPIInject.vue)  
> [CompositionAPIProvide](vue-project-practice/src/views/CompositionAPIProvide.vue)

## 9.2 믹스인(Mixins)

> [api.js](vue-project-practice/src/api.js)  
> [Mixins](vue-project-practice/src/views/Mixins.vue)

- 일반적인 언어의 공통모듈처럼 메소드를 정의해서 사용할 수 있음
- Vue 라이프사이클 훅까지 사용 가능
- 함수 이름에 $ 라는 prefix 를 사용하는데, 컴포넌트 내에 동일한 메소드명이 있어서 오버라이딩 되는 것을 방지하기 위함
- 믹스인 안에 있는 라이프사이클 훅이 먼저 실행되고, 그 다음 컴포넌트의 mounted 코드가 실행
- main.js 에 전역으로 등록해서 사용 가능 

### 9.3 CustomDirectives

> [CustomDirective](vue-project-practice/src/views/CustomDirective.vue)

```js
const app = createApp(App)
  .directive('focus', {
    mounted(el) {
      el.focus();
    }
  })
```

main.js 에 커스텀 디렉티브를 전역으로 등록할 수도 있음
