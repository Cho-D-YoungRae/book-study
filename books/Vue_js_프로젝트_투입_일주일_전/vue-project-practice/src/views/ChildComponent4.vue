<template>
  <div></div>
</template>

<script>
export default {
  name: "ChildComponent4",
  data() {
    return {
      msg: '자식 컴포넌트로부터 보내는 메시지'
    };
  },
  mounted() {
    this.$emit('send-message', this.msg);
  }
};
</script>

<style scoped>

</style>
