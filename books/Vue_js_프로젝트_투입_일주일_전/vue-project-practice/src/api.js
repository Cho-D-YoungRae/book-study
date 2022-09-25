import axios from "axios";

export default {
  mounted() {
    console.log("mixin mounted");
  },
  unmounted() {
    console.log("mixin unmounted");
  },
  methods: {
    async $callAPI(url, method, data) {
      return (await axios({
        method: method,
        url,
        data
      }).catch(e => {
        console.log(e);
      })).data;
    }
  }
}
