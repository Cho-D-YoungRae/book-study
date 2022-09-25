<template>
  <div class="model-container">
    <header>
      <slot name="header"></slot>
    </header>
    <main>

      <slot></slot>
    </main>
    <footer>
      <slot name="footer"></slot>
    </footer>
  </div>
</template>

<script>
export default {
  name: "SlotModalLayout"
};
</script>

<style scoped>
.model-container {
  border: 1px solid #ddd;
}
</style>
