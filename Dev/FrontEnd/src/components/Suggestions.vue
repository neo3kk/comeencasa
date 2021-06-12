<template>
  <div class="q-pa-md">
    <div class="text-center sugerencias" >Sugerencias</div>
  <div class="q-pa-md row wrap items-center justify-center q-gutter-md">

    <q-card class="my-card" @click="$router.push( '/plato/'+plato.id)" v-for="plato in platos" :key="plato.id"
            v-if="plato.visible && plato.tipo_de_plato.toLowerCase().includes(tipo.toLowerCase()) && plato.nombre.includes(search) ">
      <img :src="plato.imageUrl" class="comida">
      <q-card-section>
        <div class="text-h6">{{plato.nombre}}</div>
        <div class="text-subtitle2">{{plato.description}}</div>
      </q-card-section>
    </q-card>
  </div>
  </div>
</template>

<script>
import {SETTINGS} from "src/settings";

export default {
  data () {
    return {
      platos: [],
      url_server_api: SETTINGS.URL_SERVER_API,
      tipo: '',
      options: ['', 'Entrante', 'Primero', 'Postre', 'Bebida'],
      search: '',
    }
  },
  async created() {
    await this.getPlatos();
  },
  methods:{
    async getPlatos() {
      let platosFetch = await this.$axios.get(this.url_server_api + '/platos');
      let items = 4;
      this.platos = platosFetch.data.slice(0, items).map(i => {
        return i
      })
    }
  }
}
</script>
<style lang="sass" scoped>
.my-card
  max-width: 150px


.sugerencias
  font-family: "Open Sans", 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif
  font-size: 3rem
</style>
