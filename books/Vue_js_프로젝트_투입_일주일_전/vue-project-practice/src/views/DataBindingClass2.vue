<template>
  <div
      class="container"
      :class="[activeClass, errorClass]"
  >Class Binding</div>
</template>

<script>
export default {
  name: "DataBindingClass2",
  data() {
    return {
      activeClass: 'active',
      errorClass: 'text-red'
    };
  }
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 200px;
}

.active {
  background-color: yellow;
  font-weight: bold;
}

.text-red {
  color: red;
}
</style>
