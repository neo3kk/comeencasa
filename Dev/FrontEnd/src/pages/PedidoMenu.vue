<template>
  <q-page>
    <q-btn color="indigo" :label="category" style="width: 100%" v-for="category in categories">
      <q-menu>
        <q-list style="min-width: 100px">
          <q-item>
            <div class="q-pa-md row items-start q-gutter-md">
              <q-card class="my-card" v-for="plato in platos" key="plato.id"  v-if="category === plato.tipo_de_plato"
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
      </q-menu>
    </q-btn>
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
      console.log(this.platosSeleccinados)
      if (this.platosSeleccinados.length !== 0) {
        for (let i = 0; i < this.platosSeleccinados.length; i++) {
          if (this.platosSeleccinados[i].tipo_de_plato === plato.tipo_de_plato) {
            document.getElementById(this.platosSeleccinados[i].id + "").style.backgroundColor="white"
            this.platosSeleccinados.splice(i, 1)
            this.platosSeleccinados.push(plato)
            document.getElementById(plato.id).style.backgroundColor = "green"
          } else if (i === this.platosSeleccinados.length - 1) {
            this.platosSeleccinados.push(plato)
            document.getElementById(plato.id).style.backgroundColor="green"
          }
        }
      } else {
        this.platosSeleccinados.push(plato)
        document.getElementById(plato.id).style.backgroundColor="green"
      }


    },
    getPlato(id) {
      for (let i = 0; i < this.platos.length; i++) {
        if (this.platos[i].id === id) {
          return this.platos[i]
        }
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
