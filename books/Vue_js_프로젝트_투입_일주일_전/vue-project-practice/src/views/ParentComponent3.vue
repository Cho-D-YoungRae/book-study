<template>
  <child-component3 ref="child_component" />
  <button type="button" @click="changeChildData">Change Child Data</button>
</template>

<script>
import ChildComponent3 from "@/views/ChildComponent3";
export default {
  name: "ParentComponent3",
  components: {ChildComponent3},
  methods: {
    changeChildData() {
      this.$refs.child_component.msg = "부모 컴포넌트가 변경한 데이터";
    }
  }
};
</script>

<style scoped>

</style>
