<template>
<div>
  <input type="number" v-model.number="numberModel" />
</div>
</template>

<script>
export default {
  name: "DataBindingInputNumber",
  data() {
    return {
      numberModel: 3
    };
  }
};
</script>

<style scoped>

</style>
