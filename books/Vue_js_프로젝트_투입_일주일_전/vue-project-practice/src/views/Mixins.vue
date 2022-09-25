<template>
  <div></div>
</template>

<script>
import ApiMixin from "@/api";
export default {
  name: "MixinsView",
  mixins: [ApiMixin],
  data() {
    return {
      products: []
    };
  },
  async mounted() {
    this.products = await this.$callAPI("https://9b276bf0-ce4a-4873-815e-43c68a5d4e58.mock.pstmn.io/list\n", "get")
    console.log(this.products);
  }
};
</script>

<style scoped>

</style>
