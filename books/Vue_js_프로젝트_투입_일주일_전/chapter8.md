# 8. 컴포넌트 심화 학습

## 8.1 컴포넌트 안에 다른 컴포넌트 사용하기

### 8.1.2 부모 컴포넌트에서 자식 컴포넌트로 데이터 전달하기: Props

> [PageTitle](vue-project-practice/src/components/PageTitle.vue)  
> [NestedComponent](vue-project-practice/src/views/NestedComponent.vue)

- `props`에는 부모 컴포넌트로 전달받은 데이터가 저장

#### 8.1.2 정적/동적 prop 전달

- `v-bind`를 싸용해서 prop에 동적인 값을 전달할 수 있음

```vue
<template>
<PageTitle :title="title"></PageTitle>
</template>

<script>
import PageTitle from "@/components/PageTitle";
export default {
  components: {PageTitle},
  data() {
    return {
      title: "동적 페이지 타이틀"
    }
  }
};
</script>
```

#### 8.1.2.2 숫자형(Number) 전달

- 숫자 값을 `prop`에 전달하기 위해서는 `v-bind`를 통해서만 가능

```vue
<template>
<!--  42는 정적이지만, v-bind 를 사용함으로써 전달되는 데이터가 JS 표현식이됨-->
<blog-post :likes="42"></blog-post>
<!--  변수 값에 동적으로 할당-->
<blog-post :likes="post.likes"></blog-post>
</template>
```

#### 8.1.2.3 논리 자료형(Boolean) 전달

- 논리 자료형 역시 `v-bind`를 사용해야 함

```vue
<template>
<!--  true는 정적이지만, v-bind 를 사용함으로써 전달되는 데이터가 JS 표현식이됨-->
<blog-post :is-published="true"></blog-post>
<!--  변수 값에 동적으로 할당-->
<blog-post :is-published="isShow"></blog-post>
</template>
```

#### 8.1.2.3 논리 자료형(Boolean) 전달

- 논리 자료형 역시 `v-bind`를 사용해야 함

```vue
<template>
<!--  true는 정적이지만, v-bind 를 사용함으로써 전달되는 데이터가 JS 표현식이됨-->
<blog-post :is-published="true"></blog-post>
<!--  변수 값에 동적으로 할당-->
<blog-post :is-published="isShow"></blog-post>
</template>
```

#### 8.1.2.4 배열(Array) 전달

- 배열 자료형 역시 `v-bind`를 사용해야 함

```vue
<template>
<!--  배열은 정적이지만, v-bind 를 사용함으로써 전달되는 데이터가 JS 표현식이됨-->
<blog-post :comment-ids="[234, 266, 273]"></blog-post>
<!--  변수 값에 동적으로 할당-->
<blog-post :comment-ids="post.commentIds"></blog-post>
</template>
```

#### 8.1.2.5 객체(Object) 전달

- 객체 역시 `v-bind`를 사용해야 함

```vue
<template>
<!--  객체가 정적이지만, v-bind 를 사용함으로써 전달되는 데이터가 JS 표현식이됨-->
<blog-post :author="{name: 'Veronica'}"></blog-post>
<!--  변수 값에 동적으로 할당-->
<blog-post :author="post.author"></blog-post>
</template>
```

#### 8.1.2.7 Props 유효성 검사

- 자식 컴포넌트에서 props 옵션을 정의할 때, 전달받는 데이터 타입, 기본 값(default), 필수 여부(required) 그리고 유효성 검사 함수(validator)인 함수를 통해서 유효성을 검사할 수 있습니다.

```vue
<script>
export default {
  props: {
    // 기본 타입 체크 ('null'과 'undefined'는 모든 타입 유효성 검사를 통과합니다.)
    propA: Number,  // Number 타입 체크
    propB: [String, Number],  // 여러 타입 허용
    propC: {  // 문자형이고 부모 컴포넌트로부터 반드시 데이터가 필수로 전달되어야 함
      type: String,
      required: true
    },
    propD: {  // 기본 값(100)을 갖는 숫자형
      type: Number,
      default: 100
    },
    propE: {  // 기본 값을 갖는 객체 타입
      type: Object,
      // 객체나 배열의 기본값은 항상 팩토리 함수로부터 반환되어야 함
      default: function() {
        return {message: 'hello'};
      }
    },
    propF: {  // 커스텀 유효성 검사 함수
      validator: function(value) {
        // 값이 아래 세 문자열 중 하나와 일치해야 함
        return ['success', 'warning', 'danger'].indexOf(value) !== -1;
      }
    },
    propG: {  // 기본 값을 갖는 함수
      type: Function,
      // 객체나 배열과 달리 아래 표현은 팩토리 함수가 아닙니다.
      // 기본 값으로 사용되는 함수입니다.
      default: function() {
        return 'Default function';
      }
    }
  }
};
</script>
```

### 8.1.3 부모 컴포넌트에서 자식 컴포넌트의 이벤트 직접 발생시키기

> [ParentComponent](vue-project-practice/src/views/ParentComponent.vue)   
> [ChildComponent](vue-project-practice/src/views/ChildComponent.vue)

- HTML 태그에 ref="id"를 지정하면 Vue 컴포넌트의 함수에서 this.$refs 를 통해 접근 가능

### 8.1.4 부모 컴포넌트에서 자식 컴포넌트의 함수 직접 호출하기

> [ParentComponent2](vue-project-practice/src/views/ParentComponent2.vue)   
> [ChildComponent2](vue-project-practice/src/views/ChildComponent2.vue)

### 8.1.5 부모 컴포넌트에서 자식 컴포넌트의 데이터 옵션 값 직접 변경하기

> [ParentComponent3](vue-project-practice/src/views/ParentComponent3.vue)   
> [ChildComponent3](vue-project-practice/src/views/ChildComponent3.vue)

### 8.1.6 자식 컴포넌트에서 부모 컴포넌트로 이벤트/데이터 전달하기(커스텀 이벤트)

> [ParentComponent4](vue-project-practice/src/views/ParentComponent4.vue)   
> [ChildComponent4](vue-project-practice/src/views/ChildComponent4.vue)

- 자식 컴포넌트에서 부모 컴포넌트로 이벤트/데이터를 전달하기 위해서는 `$emit`을 사용합니다.

### 8.1.7 부모 컴포넌트에서 자식 컴포넌트의 데이터 옵션 값 동기화 하기

> [ParentComponent5](vue-project-practice/src/views/ParentComponent5.vue)   
> [ChildComponent5](vue-project-practice/src/views/ChildComponent5.vue)

- 부모 컴포넌트에서 `computed`를 이용하면 자식 컴포넌트에 정의된 데이터 옵션 값의 변경 사항을 항상 동기화시킬 수 있습니다.
