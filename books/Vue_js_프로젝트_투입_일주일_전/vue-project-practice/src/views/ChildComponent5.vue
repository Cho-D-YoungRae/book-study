<template>
  <button type="button" @click="childFunc" ref="btn">자식 컴포넌트 데이터 변경</button>
</template>

<script>
export default {
  name: "ChildComponent5",
  data() {
    return {
      msg: '메시지',
      count: 0
    };
  },
  methods: {
    childFunc() {
      this.count += 1;
      this.msg = '변경된 메시지' + this.count;
      console.log(this.msg);
    }
  }
};
</script>

<style scoped>

</style>
