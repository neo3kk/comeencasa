<template>
  <q-page>
  </q-page>


</template>

<script>

import {SETTINGS} from "src/settings";

export default {
  name: 'Callback',
  data() {
    return {
      post: "",
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  created() {
    this.$nextTick(async function () {
      let parameters = this.$route.query
      let data = await this.$axios.post(this.url_server_api + "/auth/oauth2callback/?code=" + parameters.code);
      if (data.status === 200 || data.status === 201) {
        this.$router.push({name: '', query: {redirect: '/'}});
      } else {
        this.$router.push({name: 'login', query: {redirect: '/login'}});
      }
    })
  }

}
</script>
