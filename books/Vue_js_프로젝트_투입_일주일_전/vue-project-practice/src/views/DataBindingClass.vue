<template>
<div
    class="container"
    :class="{ active: isActive, 'text-red': hasError }"
>Class Binding</div>
</template>

<script>
export default {
  name: "DataBindingClass",
  data() {
    return {
      isActive: true,
      hasError: false
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
