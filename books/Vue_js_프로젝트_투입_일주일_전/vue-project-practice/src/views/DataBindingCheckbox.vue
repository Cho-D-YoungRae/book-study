<template>
  <div>
    <!--체크박스 체크 되었을 때 기본 값 true, 해제되었을 때 기본 값 false-->
    <!--true-value, false-value 를 통해 변경 가능-->
    <label><input type="checkbox" v-model="checked" true-value="yes" false-value="no"> {{ checked }}</label>
  </div>
</template>

<script>
export default {
  name: "DataBindingCheckbox",
  data() {
    return {
      checked: true
    };
  }
};
</script>

<style scoped>

</style>
