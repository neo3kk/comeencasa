<template>
  <q-page class="text-center">
    <q-img src="https://entrenosotros.consum.es/public/Image/2019/3/muchos1_Mediano.jpg" style="width: 50%"
           native-context-menu>
      <q-icon class="absolute all-pointer-events" size="32px" name="info" color="white" style="top: 8px; left: 8px">
        <q-tooltip>
          Tooltip
        </q-tooltip>
      </q-icon>
      <div class="absolute-bottom text-subtitle1 text-center">
        <div class="q-pa-md row items-start q-gutter-md">
          <p>Añade a tu pedido un menu en el que te incluye un entrante, el primer plato, el postre y la bebida!</p>
        </div>
        <div class="q-pa-md row items-start q-gutter-md">
          <q-btn color="white" text-color="black" label="Prepara tu menu!"
                 @click="$router.replace('/profile/pedidomenu')"/>
        </div>

      </div>
    </q-img>
    <div class="q-pa-md row items-start q-gutter-md">
      <q-card class="my-card" v-for="plato in platos" key="plato.id">
        <img :src="plato.image" class="comida">

        <q-card-section>
          <div class="text-h6">{{ plato.nombre }}</div>
          <div class="text-subtitle2">{{ plato.description }}</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          Ingredientes:
        </q-card-section>
        <q-separator/>

        <q-card-actions align="right">
          <q-btn flat color="secondary" @click="añadirPlato(plato.id)">Añadir Plato</q-btn>
          <q-btn flat color="primary" @click="startComputing(1)">Mas información</q-btn>
        </q-card-actions>
      </q-card>
    </div>
  </q-page>


</template>

<script>


import {SETTINGS} from "src/settings";
import Carrousel from 'src/components/Carrousel.vue'

export default {
  name: 'nuevoPedido',
  components: {Carrousel},
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
      console.log(this.platos)
    },
    async añadirPlato(id) {
      console.log(id, "hola")
      let addPlato = await this.$axios.post(this.url_server_api + '/addPlatoPedido', {
        plato_id: id.toString()
      }).then(response => {
        this.showNotification("Se ha añadido correctamente el plato a su pedido", "check_circle_outline", "positive")
      })
    },
    showNotification(content, icon, color) {
      this.$q.notify({
        message: content,
        color: color,
        icon: icon,
        actions: [
          {
            label: 'OK', color: 'white', handler: () => {
              this.tab = "nuevoPedido"
            }
          }
        ]
      })
    },
  }
}
</script>
<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 250px
</style>
