<template>
  <CompositionAPIInject/>
</template>

<script>
import CompositionAPIInject from "@/views/CompositionAPIInject";
import {provide} from "vue";

export default {
  name: "CompositionAPIProvide",
  components: {
    CompositionAPIInject
  },
  setup() {
    // provide 함수를 통해서 전달할 키(key), 값(value) 설정
    provide('title', 'Vue.js 프로젝트');
  }
};
</script>

<style scoped>

</style>
