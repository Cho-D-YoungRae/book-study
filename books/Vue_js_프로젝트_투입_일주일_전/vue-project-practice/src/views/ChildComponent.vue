<template>
  <!--  자식 컴포넌트의 버튼 객체에 ref="btn"로 접근할 수 있도록-->
  <!--  HTML 태그에 ref="id"를 지정하면 Vue 컴포넌트의 함수에서 this.$refs를 통해 접근 가능-->
  <button type="button" @click="childFunc" ref="btn">click</button>
</template>

<script>
export default {
  name: "ChildComponent",
  methods: {
    childFunc() {
      console.log('부모 컴포넌트에서 직접 발생시킨 이벤트');
    }
  }
};
</script>

<style scoped>

</style>
