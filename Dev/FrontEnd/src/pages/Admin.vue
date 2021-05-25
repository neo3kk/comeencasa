<template>
  <q-page>
    {{admin}}
    <div>

    </div>
  </q-page>


</template>

<script>


import {SETTINGS} from "src/settings";

export default {
  name: 'AdminPage',

  data() {
    return {
      admin:"",
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
      this.admin = data.data;
    }
  }
}
</script>


