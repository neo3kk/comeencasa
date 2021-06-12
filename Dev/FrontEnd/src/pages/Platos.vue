<template>
  <q-page>
    <div class="row justify-center">
      <div class="row items-center">
        <p>Busca segun el tipo de plato:</p>
        <q-select borderless v-model="tipo" :options="options" label="Tipo de plato" class="q-ml-lg"/>
      </div>
      <div class="row items-center">
        <q-input v-model="search" filled type="search" hint="Search">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>
      </div>
    </div>
    <div class="q-pa-md row wrap items-center justify-center q-gutter-md">
      <q-card class="my-card" @click="$router.push( '/plato/'+plato.id)" v-for="plato in platos" :key="plato.id"
              v-if="plato.visible && plato.tipo_de_plato.toLowerCase().includes(tipo.toLowerCase()) && plato.nombre.includes(search)">
        <img :src="plato.imageUrl" class="comida">
        <q-card-section>
          <div class="text-h6">{{plato.nombre}}</div>
          <div class="text-subtitle2">{{plato.description}}</div>
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<script>
  import {SETTINGS} from "src/settings";

  export default {
    name: 'Platos',
    data() {
      return {
        platos: [],
        url_server_api: SETTINGS.URL_SERVER_API,
        tipo: '',
        options: ['', 'Entrante', 'Primero', 'Postre', 'Bebida'],
        search: '',
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
