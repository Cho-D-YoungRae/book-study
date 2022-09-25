<template>
  <child-component4 @send-message="sendMessage" />
</template>

<script>
import ChildComponent4 from "@/views/ChildComponent4";
export default {
  name: "ParentComponent4",
  components: {ChildComponent4},
  methods: {
    sendMessage(data) {
      console.log(data);
    }
  }
};
</script>

<style scoped>

</style>
