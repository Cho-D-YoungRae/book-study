<template>
  <slot-modal-layout>
    <template v-slot:header>
      <h1>팝업 타이틀</h1>
    </template>
    <template v-slot:default>
      <p>팝업 컨텐츠 1</p>
      <p>팝업 컨텐츠 2</p>
    </template>
    <template #footer>
      <button type="button">닫기</button>
    </template>
  </slot-modal-layout>
</template>

<script>
import SlotModalLayout from "@/views/SlotModalLayout";
export default {
  name: "SlotUseModel",
  components: {SlotModalLayout}
};
</script>

<style scoped>

</style>
