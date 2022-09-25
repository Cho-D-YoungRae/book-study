<template>
  <div>
    <h2>Calculator</h2>
    <div>
      <input type="text" v-model="state.num1" />
      <span> + </span>
      <input type="text" v-model="state.num2" />
      <span> = </span>
      <span>{{state.result}}</span>
    </div>
  </div>
</template>

<script>
import {reactive, computed} from "vue";
export default {
  name: "CompositionAPI2",
  setup() {
    let state = reactive({
      num1: 0,
      num2: 0,
      // computed 를 이용해서 num1, num2 가 변경이 일어나면 즉시 result 를 더한 값 반환
      result: computed(() => parseInt(state.num1) + parseInt(state.num2))
    });

    return {
      state
    }
  }
};
</script>

<style scoped>

</style>
