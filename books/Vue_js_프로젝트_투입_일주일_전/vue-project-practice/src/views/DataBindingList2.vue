<template>
<div>
  <table>
    <thead>
    <tr>
      <th>제품명</th>
      <th>가격</th>
      <th>카테고리</th>
      <th>배송료</th>
    </tr>
    </thead>
    <tbody>
    <tr :key="i" v-for="(product, i) in products">
      <td>{{product.name}}</td>
      <td>{{product.price}}</td>
      <td>{{product.category}}</td>
      <td>{{product.deliveryPrice}}</td>
    </tr>
    </tbody>
  </table>
</div>
</template>

<script>
export default {
  name: "DataBindingList2",
  data() {
    return {
      products: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      this.products = await this.$api("https://9b276bf0-ce4a-4873-815e-43c68a5d4e58.mock.pstmn.io/list", "get");
    }
  }
};
</script>

<style scoped>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}
td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}
</style>
