<template>
<h1>Full Name: {{fullName}}</h1>
</template>

<script>
export default {
  name: "ComputedView",
  data() {
    return {
      firstName: 'Youngrae',
      lastName: 'Cho'
    };
  },
  computed: {
    fullName() {
      return this.firstName + ' ' + this.lastName;
    }
  }
};
</script>

<style scoped>

</style>
