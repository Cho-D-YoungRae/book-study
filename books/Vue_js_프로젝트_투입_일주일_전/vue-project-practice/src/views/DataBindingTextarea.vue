<template>
<div>
  <textarea v-model="message"></textarea>
</div>
</template>

<script>
export default {
  name: "DataBindingTextarea",
  data() {
    return {
      message: "여러줄을 입력할 수 있는 textarea 입니다."
    };
  }
};
</script>

<style scoped>

</style>
