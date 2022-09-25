<template>
  <h1>{{title}}</h1>
</template>

<script>
import {inject} from "vue";

export default {
  name: "CompositionAPIInject",
  setup() {
    // inject 를 사용해서 provide에서 정의한 키(key)로 데이터를 전달 받음
    const title = inject('title');

    return {title};
  }
};
</script>

<style scoped>

</style>
