<template>
  <input type="text" v-focus>
</template>

<script>
export default {
  name: "CustomDirective",
  directives: {
    focus: {
      mounted(el) {
        el.focus();
      }
    }
  }
};
</script>

<style scoped>

</style>
