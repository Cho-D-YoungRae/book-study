<template>
<h1>Full Name: {{fullName}}</h1>
</template>

<script>
export default {
  name: "WatchView",
  data() {
    return {
      firstName: 'Youngrae',
      lastName: 'Cho',
      fullName: ''
    };
  },
  watch: {
    firstName() {
      this.fullName = this.firstName() + ' ' + this.lastName;
    },
    lastName() {
      this.fullName = this.firstName() + ' ' + this.lastName;
    }
  }
};
</script>

<style scoped>

</style>
