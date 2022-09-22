<template>
<div>
  <img v-bind:src="imgSrc" :alt="imgAlt"/>
</div>
</template>

<script>
export default {
  name: "DataBindingAttribute",
  data() {
    return {
      imgSrc: "https://v3.ko.vuejs.org/logo.png",
      imgAlt: "Vue.js logo"
    };
  }
};
</script>

<style scoped>

</style>
