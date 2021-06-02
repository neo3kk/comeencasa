<template>

</template>

<script>
import {SETTINGS} from "src/settings";

export default {
  props: ['id'],
  name: "Plato.vue",
  data () {
    return {
      id: '',
      url_server_api: SETTINGS.URL_SERVER_API
    }
  },
  created() {
    this.id = this.$router.currentRoute.params.id

  },

}
</script>

<style scoped>

</style>
