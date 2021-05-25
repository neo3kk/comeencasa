<template>
  <q-page>
    <div class="q-pa-md q-gutter-md fit column wrap justify-center items-center content-center">
      <h3>Adminsitra ingredientes y platos</h3>
      <q-card class="my-card">
        <q-card-section class="bg-primary text-white">
          <div class="text-h6">Ingredientes</div>
          <div class="text-subtitle2">Edita la lista de ingredientes</div>
        </q-card-section>

        <q-separator />

        <q-card-actions align="around">
          <q-btn flat @click="$router.push('/admin/nuevoingrediente')">Edita</q-btn>
        </q-card-actions>
      </q-card>
      <q-card class="my-card">
        <q-card-section class="bg-purple text-white">
          <div class="text-h6">Platos</div>
          <div class="text-subtitle2">Crea los diferentes platos</div>
        </q-card-section>

        <q-card-actions align="around">
          <q-btn flat @click="$router.push('/admin/nuevoplato')">Edita</q-btn>
        </q-card-actions>
      </q-card>
    </div>
  </q-page>


</template>

<script>


import {SETTINGS} from "src/settings";

export default {
  name: 'AdminPage',

  data() {
    return {
      admin: "",
      post: "",
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  created() {
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

<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 250px
</style>


