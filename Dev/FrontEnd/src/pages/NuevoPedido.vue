<template>
  <div>
    <q-page class="text-center q-pa-md">
      <div class="justify-around">
        <q-img src="https://entrenosotros.consum.es/public/Image/2019/3/muchos1_Mediano.jpg" style="width: 50%"
               native-context-menu>
          <div class="absolute-bottom text-subtitle1 text-center">
            <div class="q-pa-md row items-start q-gutter-md">
              <p>Añade a tu pedido un menu en el que te incluye un entrante, el primer plato, el postre y la bebida! Tan
                solo a 12€!!!</p>
            </div>
            <div class="q-pa-md row items-start q-gutter-md">
              <q-btn color="white" text-color="black" label="Prepara tu menu!"
                     @click="$router.push('/profile/pedidomenu')"/>
            </div>
          </div>
        </q-img>
        <div class="row justify-center q-pa-md">
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
      </div>
      <div class="q-pa-md row items-start q-gutter-md">
        <q-card class="my-card" v-for="plato in platos" :key="plato.id"
                v-if="plato.visible && plato.tipo_de_plato.toLowerCase().includes(tipo.toLowerCase()) && plato.nombre.includes(search)">
          <img :src="plato.imageUrl" class="comida">
          <q-card-section>
            <div class="text-h6">{{ plato.nombre }}</div>
            <div class="text-subtitle2">{{ plato.description }}</div>
            <div class="text-subtitle1">{{ plato.precio }}€</div>
            <q-btn flat color="secondary" @click="añadirPlato(plato.id)">Añadir Plato</q-btn>
            <q-btn flat color="primary" @click="$router.push( '/plato/'+plato.id)">Mas información</q-btn>
          </q-card-section>
        </q-card>
      </div>
    </q-page>
  </div>
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
    },
    async añadirPlato(id) {
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
