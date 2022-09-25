<template>
  <button type="button" @click="checkChild">자식 컴포넌트 데이터 조회</button>
  <child-component5 ref="child_component" />
</template>

<script>
import ChildComponent5 from "@/views/ChildComponent5";
export default {
  name: "ParentComponent5",
  components: {ChildComponent5},
  computed: {
    msg() {
      return this.$refs.child_component.msg;
    }
  },
  methods: {
    checkChild() {
      alert(this.msg);
    }
  }
};
</script>

<style scoped>

</style>
