<template>
  <child-component ref="child_component" />
</template>

<script>
import ChildComponent from "@/views/ChildComponent";
export default {
  name: "ParentComponent",
  components: {ChildComponent},
  mounted() {
    this.$refs.child_component.$refs.btn.click();
  }
};
</script>

<style scoped>

</style>
