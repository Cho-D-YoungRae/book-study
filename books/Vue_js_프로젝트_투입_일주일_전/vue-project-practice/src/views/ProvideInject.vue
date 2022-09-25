<template>
  <div>
    <provide-inject-child/>
  </div>
</template>

<script>
import ProvideInjectChild from "@/views/ProvideInjectChild";
export default {
  name: "ProvideInject",
  components: {ProvideInjectChild},
  data() {
    return {
      items: ['A', 'B']
    };
  },
  provide() {
    return {
      itemLength: this.items.length
    }
  }
};
</script>

<style scoped>

</style>
