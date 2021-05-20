<template>
  <q-page>
    <q-card class="my-card" v-for="plato in platos" key="plato.id">
      <img :src="plato.image" class="comida">

      <q-card-section>
        <div class="text-h6">{{plato.nombre}}</div>
        <div class="text-subtitle2">{{plato.description}}</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        Ingredientes:
      </q-card-section>
    </q-card>
  </q-page>


</template>

<script>

import Carrousel from 'src/components/Carrousel.vue'
import Suggestions from "components/Suggestions";
import {SETTINGS} from "src/settings";

export default {
  name: 'Platos',
  data() {
    return {
      lorem: "loremimpsum",
      platos: [],
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  created() {
    this.getPlatos();
  },
  methods: {
    async getPlatos() {
      let platosFetch = await this.$axios.get(this.url_server_api + '/platos');
      this.platos = platosFetch.data
    }
  }

}
</script>
<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 250px
</style>
