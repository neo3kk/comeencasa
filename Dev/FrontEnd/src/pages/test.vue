<template>
  <q-page class="flex flex-center">
    <img
      alt="COME EN CASA"
      src="~src/assets/logo.png"
    >
  </q-page>
</template>

<script>
import { SETTINGS } from "../settings";

export default {
  name: "test",

  data() {
    return {
      post: "",
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  created(){
    this.getPost()
  },
  methods: {
    async getPost() {
      let data = await this.$axios.get(this.url_server_api + `/test`);
      this.post = data.data;
    }
  }
};
</script>
