<template>
  <q-page>
    <div class="row justify-around" style="background-color: green">
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
    <div class="q-pa-md row items-start q-gutter-md">
      <q-card class="my-card" v-for="plato in platos" key="plato"
              v-if="plato.visible && plato.tipo_de_plato.toLowerCase().includes(tipo.toLowerCase()) && plato.nombre.includes(search) ">
        <img :src="plato.image" class="comida">

        <q-card-section>
          <div class="text-h6">{{plato.nombre}}</div>
          <div class="text-subtitle2">{{plato.description}}</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          Ingredientes:
        </q-card-section>
      </q-card>
    </div>

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
        console.log(this.platos)
      }
    }

  }
</script>
<style lang="sass" scoped>
  .my-card
    width: 100%
    max-width: 250px
</style>
