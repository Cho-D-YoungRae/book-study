<template>
  <child-component2 ref="child_component" />
</template>

<script>
import ChildComponent2 from "@/views/ChildComponent2";
export default {
  name: "ParentComponent2",
  components: {ChildComponent2},
  mounted() {
    this.$refs.child_component.callFromParent();
  }
};
</script>

<style scoped>

</style>
