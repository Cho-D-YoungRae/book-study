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
  name: "DataBindingList",
  data() {
    return {
      products: [
        {name: "기계식키보드", price: 25000, category: "노트북/태블릿", deliveryPrice: 5000},
        {name: "무선마우스", price: 12000, category: "노트북/태블릿", deliveryPrice: 5000},
        {name: "아이패드", price: 725000, category: "노트북/태블릿", deliveryPrice: 5000},
        {name: "태블릿거치대", price: 32000, category: "노트북/태블릿", deliveryPrice: 5000},
        {name: "무선충전기", price: 43000, category: "노트북/태블릿", deliveryPrice: 5000},
      ]
    };
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
