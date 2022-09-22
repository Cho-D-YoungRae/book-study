<template>
<select v-model="selectedValue" @change="changeSelected">
  <option value="Seoul">서울</option>
  <option value="Pusan">부산</option>
  <option value="Jeju">제주</option>
</select>
</template>

<script>
export default {
  name: "EventChange",
  data() {
    return {
      selectedValue: ''
    };
  },
  methods: {
    changeSelected() {
      alert(this.selectedValue);
    }
  }
};
</script>

<style scoped>

</style>
