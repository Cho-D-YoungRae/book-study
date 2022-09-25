<template>
  <p>Count : {{count}}</p>
  <button type="button" @click="increments">Increment</button>
  <p>cartCount : {{cartCount}}</p>
</template>

<script>
export default {
  name: "StoreAccess",
  computed: {
    count() {
      return this.$store.state.count;
    },
    cartCount() {
      return this.$store.getters.cartCount;
    }
  },
  methods: {
    increments() {
      this.$store.commit('increment');
    }
  }
};
</script>

<style scoped>

</style>
