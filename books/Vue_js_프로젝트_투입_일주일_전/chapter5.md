# 컴포넌트 Basic

## 5.2 컴포넌트 구조 이해하기

### 5.2.1 컴포넌트 기본 구조

```html
<template>
  <div></div>
</template>

<script>

export default {
  name: '', // 컴포넌트 이름. 등록하지 않아도 사용하는 것에는 지장 없음
  components: {}, // 다른 컴포넌트 사용 시 컴포넌트 import하고 배열로 저장
  data() {  // html과 자바스크립트 코드에서 사용할 데이터 변수 선언
    return {
        sampleData: ''
    };
  },
  setup() {},   // 컴포지션 API
  created() {},   // 컴포넌트가 생성되면 실행
  mounted() {},   // template에 정의된 html 코드가 랜더링된 후 실행
  unmounted() {},   // unmount가 완료된 후 실행 -> 컴포넌트를 빠져나갈 때
  methods: {},   //  컴포넌트 내에서 사용할 메서드 정의
}
</script>
```

### 5.2.4 Lifecycle Hooks

![view lifecycle](view_lifecycle.png)

- 모든 컴포넌트는 생성될 때 초기화 단계를 거침
- 데이터의 변경사항 감시를 위한 설정
- 템플릿 컴파일
- 인스턴스를 DOM에 마운트하고, 데이터가 변경되면 DOM을 업데이트
- Vue컴포넌트 개발 시 각 라이프사이클 훅에 따라 프로그램을 적절히 배치하면 화면 로딩 시간을 개선할 수 있음
  - 제일 먼저 보여줘야 하는 데이터 영역의 경우 `created()`에 정의해서 서버로부터 미리 받아옴
  - 화면 로딩 이후에 삽입되어도 되는 데이터 혹은 HTML 객체 부분은 `mounted()` 훅에 정의

## 5.3 데이터 바인딩

- Vue는 양방향 데이터 바인딩을 지원
  - 양방향 데이터 바인딩: 모델에서 데이터를 정의한 후 뷰와 연결하면 모델과 뷰 중 어느 한쪽에 변경이 일어났을 때 다른 한쪽에 자동으로 반영

### 5.3.1 문자열 데이터 바인딩

- [DataBinding](vue-project-practice/src/views/DataBinding.vue)

### 5.3.2 raw HTML 데이터 바인딩

>[DataBindingHtml](vue-project-practice/src/views/DataBindingHtml.vue)

### 5.3.3 Form 입력 데이터 바인딩

- `v-model` 디렉티브를 사용하여 양방향 데이터 바인딩 생성 가능
- `v-model`은 내부적으로 서로 다른 속성을 사용하고 서로 다른 입력 요소에 대해 서로 다른 이벤트를 전송

#### 5.3.3.1 Input type = text

> [DataBindingInputText](vue-project-practice/src/views/DataBindingInputText.vue)

#### 5.3.3.2 Input type = number

> [DataBindingInputNumber](vue-project-practice/src/views/DataBindingInputNumber.vue)

- `v-model.number` 을 사용하면 프로그램 코드 내에서 사용자가 입력한 값은 문자가 아니라 숫자로 관리됨

#### 5.3.3.3 Textarea

> [DataBindingTextarea](vue-project-practice/src/views/DataBindingTextarea.vue)

#### 5.3.3.4 Select

> [DataBindingSelect](vue-project-practice/src/views/DataBindingSelect.vue)

#### 5.3.3.5 체크박스(input type=checkbox)

> [DataBindingCheckbox](vue-project-practice/src/views/DataBindingCheckbox.vue)  
> [DataBindingCheckbox2](vue-project-practice/src/views/DataBindingCheckbox2.vue)

- 체크박스의 경우 input type=text, select 와 다르게 `v-model` 은 내부적으로 체크박스의 checked 속성을 사용
- value 속성에 데이터 바인딩을 하려면 `v-bind:value`를 사용해야 함

#### 5.3.3.5 라디오(input type=radio)

> [DataBindingRadio](vue-project-practice/src/views/DataBindingRadio.vue)

- 체크박스와 마찬가지로 `v-model`은 내부적으로 checked 속성과 바인딩이 이루어짐
- value 속성에 데이터 바인딩을 하려면 `v-bind:value`를 사용해야 함

### 5.3.4 속성(Attribute)

- value 를 제외한 HTML 객체의 속성(attribute)에 데이터를 바인딩하기 위해서 `v-bind:` 디렉티브 사용
- `v-bind:` 디렉티브는 v-bind 을 생략하고 `:` 으로 사용할 수도 있음

#### 5.3.4.1 Img 객체의 src

> [DataBindingAttribute](vue-project-practice/src/views/DataBindingAttribute.vue)

#### 5.3.4.2 button 객체의 disabled

> [DataBindingButton](vue-project-practice/src/views/DataBindingButton.vue)

### 5.3.5 클래스 바인딩

> [DataBindingClass](vue-project-practice/src/views/DataBindingClass.vue)  
> [DataBindingClass2](vue-project-practice/src/views/DataBindingClass2.vue)

- 반드시 적용해야 하는 클래스는 기존 html 에서 사용하던 방식처럼 class 속성에 클래스명 입력
- 조건에 따라 바인딩할 클래스의 경우 `v-bind:class` 사용
- 다른 속성의 경우 하나의 속성만을 이용해서 바인딩 해야하지만 클래스의 경우 기본 클래스와 데이터 바인딩 처리를 하는 클래스를 공존해서 사용 가능

### 5.3.6 인라인 스타일 바인딩

> [DataBindingStyle](vue-project-practice/src/views/DataBindingStyle.vue)  
> [DataBindingStyle2](vue-project-practice/src/views/DataBindingStyle2.vue)

## 5.4 리스트 랜더링(v-for)

> [DataBindingList](vue-project-practice/src/views/DataBindingList.vue)

```vue
<tag v-for="(item, index) in items"></tag>
```

## 5.5 랜더링 문법(v-if, v-show)

### 5.5.1 v-if

> [RenderingVIf](vue-project-practice/src/views/RenderingVIf.vue)

### 5.5.2 v-show

```vue
<h1 v-show="bShow">bShow가 true이면, 현재 블록이 화면에 보이게 됩니다.</h1>
```

### 5.5.3 v-if VS v-show

- `v-if`: 조건을 만족하면 html 블록 생성, 만족하지 않으면 html 블록 삭제
- `v-show`: 조건 만족 여부에 상관없이 html 블록이 생성되며, 조건을 만족하면 css의 display를 이용해서 화면에 보이게 됨
  - 조건이 만족하지 않더라도 html 블록을 생성한다는 단점
- `v-if`는 toggle이 일어날 때, `v-show`보다 더 많은 자원을 사용하게 됨
  - `v-if` 는 실제로 해당 블록 전체를 생성했다가 삭제하기 때문
  - toggle이 자주 일어나면 `v-show` 아니면 `v-if`

## 5.6 이벤트 처리(v-on)

- Vue 컴포넌트에서 이벤트를 처리할 때는 `v-on` 디렉티브 사용
- `v-on` 디렉티브는 심볼 `@`로도 사용 가능

### 5.6.1 클릭 이벤트(v-on:click)

> [EventClick](vue-project-practice/src/views/EventClick.vue)

### 5.6.2 Change 이벤트

> [EventChange](vue-project-practice/src/views/EventChange.vue)

### 5.6.3 Key 이벤트

예시

```vue
<input @keyup.enter="submit" />
```

- Vue 에서는 자주 사용되는 Key 이벤트를 제공
  - `.enter`
  - `.tab`
  - `.delete`
  - `.esc`
  - `.space`
  - `.up`
  - `.down`
  - `.left`
  - `.right`

Control, Shift, Alt 키와 같이 다른 키와 같이 사용되는 특수 키에 대해서

```vue
<!--Alt + Enter-->
<input @keyup.alt.enter="clear">

<!--Ctrl + Click-->
<div @click.ctrl="doSomthing">Do Something</div>
```

## 5.7 computed 와 watch

- computed 와 watch 둘다 Vue 인스턴스 내의 정의된 데이터 값에 변경이 일어나는지를 감시하고, 변경될 때마다 정의된 함수 실행

### 5.7.1 computed

> [Computed](vue-project-practice/src/views/Computed.vue)

#### computed 사용 안 했을 때

```vue
<template>
<h1>{{firstName + ' ' + lastName}}</h1> <!--첫 번째 방식-->
<h1>{{getFullName()}}</h1>              <!--두 번째 방식-->
</template>

<script>
export default {
  data() {
    return {
      firstName: 'Youngrae',
      lastName: 'Cho'
    };
  },
  methods: {
    getFullName() {
      return this.firstName + ' ' + this.lastName;
    }
  }
};
</script>
```

- 위 두 가지 방식 모두 이름을 한 곳이 아니라 화면 내 여러 곳에서 보여줘야 한다면 데이터 결합과 함수 호출 이라는 연산을 화면에 보여주는 수 만큼 해야함

#### computed 사용

- `computed` 에 정의된 함수명은 동시에 Vue 인스턴스의 데이터 키 값
- 데이터 변경이 발생하면 함수가 자동 실행 후 데이터 값 갱신
- 데이터를 화면 내 여러 곳에서 사용하더라도 연산은 한 번만 발생
- 일반 함수를 이용할 경우 데이터의 변경을 감지할 수 없음

### 5.7.2 watch

- `watch` 도 `computed` 처럼 Vue 인스턴스에 정의된 데이터 값이 변경이 일어나는지를 감시하고, 변경이 일어나면 지정된 함수를 실행시킬 수 있음
- `computed` 의 경우 기존에 정의된 데이터 값을 기반으로 새로운 데이터 값을 활용하기 위해 사용
- `watch` 는 `watch`에 정의된 데이터 값 하나만을 감시하기 위한 용도
- `watch` 는 `computed` 와 다르게 실제 데이터 변경이 일어나기 전까지는 실행되지 않음
