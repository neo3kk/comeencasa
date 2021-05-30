<template>
  <q-page>
    <div v-for="category in categories" :key="id">
      <q-btn color="indigo" :label="category" style="width: 100%"></q-btn>
      <q-list style="min-width: 100px">
        <q-item>
          <div class="q-pa-md row items-start q-gutter-md">
            <q-card class="my-card" v-for="plato in platos" key="plato.id" v-if="category === plato.tipo_de_plato"
                    @click="seleccionarPlato(plato.id)" clickable :id="plato.id">
              <img :src="plato.image" class="comida">
              <q-card-section>
                <div>
                  <div class="text-h6">{{ plato.nombre }}</div>
                  <div class="text-subtitle2">{{ plato.description }}</div>
                </div>
              </q-card-section>

              <q-card-section class="q-pt-none">
                Ingredientes:
              </q-card-section>
              <q-separator/>

              <q-card-actions align="right">
                <q-btn flat color="primary" @click="startComputing(1)">Mas información</q-btn>
              </q-card-actions>
            </q-card>
          </div>
        </q-item>
      </q-list>
    </div>
    <q-btn color="indigo" label="Confirma tu pedido" style="width: 100%" @click="hacerPedido"></q-btn>
  </q-page>
</template>

<script>
import {SETTINGS} from "src/settings";

export default {
  name: "PedidoMenu",
  data() {
    return {
      lorem: "loremimpsum",
      platos: [],
      categories: ["entrante", "primero", "postre", "bebida"],
      entrante: true,
      primero: true,
      postre: true,
      bebida: true,
      url_server_api: SETTINGS.URL_SERVER_API,
      platosSeleccinados: []
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
    async seleccionarPlato(id) {
      var plato = this.getPlato(id)
      var contains = false;
      if (this.platosSeleccinados.length !== 0) {
        for (let i = 0; i < this.platosSeleccinados.length; i++) {
          if (plato.id === this.platosSeleccinados[i].id){
            console.log(1)
            console.log(plato)
            document.getElementById(plato.id).style.backgroundColor = "white"
            this.platosSeleccinados.splice(i, 1)
            contains = true
          }
          else if (this.platosSeleccinados[i].tipo_de_plato === plato.tipo_de_plato) {
            console.log(2)
            document.getElementById(this.platosSeleccinados[i].id + "").style.backgroundColor = "white"
            this.platosSeleccinados.splice(i, 1)
            this.platosSeleccinados.push(plato)
            document.getElementById(plato.id).style.backgroundColor = "green"
            contains = true;
            i++;
          }

        }
        if (!contains) {
          console.log(4)
          this.platosSeleccinados.push(plato)
          document.getElementById(plato.id).style.backgroundColor = "green"
        }
      } else {
        console.log(5)
        this.platosSeleccinados.push(plato)
        document.getElementById(plato.id).style.backgroundColor = "green"
      }


    },
    getPlato(id) {
      for (let i = 0; i < this.platos.length; i++) {
        if (this.platos[i].id === id) {
          return this.platos[i]
        }
      }
    },
    async hacerPedido() {
      if (this.platosSeleccinados.length < 4 && this.platosSeleccinados.length >= 1) {
        this.showNotification("Falta per seleccionar " + (4 - this.platosSeleccinados.length) + " plats", "error", "negative")
      }
      if (this.platosSeleccinados.length === 1) {
        this.showNotification("Falta per seleccionar un plat", "error", "negative")
      }
      if (this.platosSeleccinados.length === 4){
        let sendMenu = await this.$axios.post(this.url_server_api + '/añadirMenu', {
          platos: this.platosSeleccinados
        })
        this.showNotification("Se ha añadido el menu a tu pedido correctamente", "check_circle_outline", "positive")
      }
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

<style scoped>

</style>
