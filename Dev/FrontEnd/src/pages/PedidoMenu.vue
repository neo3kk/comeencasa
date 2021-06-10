<template>
  <q-page>
    <q-list style="min-width: 100px">
      <q-expansion-item v-for="category in categories" :key="category"
                        expand-separator
                        icon=""
                        :label="category"
                        :caption="category">
        <div class="q-pa-md row items-start q-gutter-md">
          <q-card class="my-card" v-for="plato in platos" :key="plato.id"
                  v-if="category.toLowerCase() === plato.tipo_de_plato.toLowerCase()">
            <img :src="plato.image" class="comida">
            <div @click="seleccionarPlato(plato.id)" clickable :id="plato.id" v-if="id === undefined">
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
            </div>
            <div :id="plato.id" v-else>
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
            </div>

            <q-card-actions align="right">
              <q-btn flat color="primary" @click="$router.push( '/plato/'+plato.id)">Mas información</q-btn>
            </q-card-actions>
          </q-card>
        </div>
      </q-expansion-item>
    </q-list>
    <q-btn color="indigo" label="Confirma tu menu" style="width: 100%" @click="hacerPedido" v-if="id === undefined"></q-btn>
  </q-page>
</template>

<script>
  import {SETTINGS} from "src/settings";

  export default {
    name: "PedidoMenu",
    data() {
      return {
        id: undefined,
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
      this.id = this.$router.currentRoute.params.id
      if (this.id !== undefined) {
        this.getMenuById(this.id)
      }
    },
    methods: {
      async getMenuById(id) {
        let menuFetch = await this.$axios.post(this.url_server_api + '/getPlatosByMenuId', {
          idmenu: id
        });
        this.platosSeleccinados = menuFetch.data;
        this.pintarPlatos(this.platosSeleccinados)
      },
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
            if (plato.id === this.platosSeleccinados[i].id) {
              console.log(1)
              console.log(this.platosSeleccinados)
              console.log(this.platosSeleccinados[i])
              console.log(plato)
              document.getElementById(plato.id).style.backgroundColor = "white"
              this.platosSeleccinados.splice(i, 1)
              contains = true
            } else if (this.platosSeleccinados[i].tipo_de_plato.toLowerCase() === plato.tipo_de_plato.toLowerCase()) {
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
      pintarPlatos(platos) {
        for (let i = 0; i < platos.length; i++) {
          document.getElementById(platos[i].id).style.backgroundColor = "green"
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
        if (this.id !== undefined) {
          let sendMenu = await this.$axios.post(this.url_server_api + '/guardarMenu', {
            platos: this.platosSeleccinados,
            idmenu: this.id
          })
        } else {
          if (this.platosSeleccinados.length < 4 && this.platosSeleccinados.length >= 1) {
            this.showNotification("Falta per seleccionar " + (4 - this.platosSeleccinados.length) + " plats", "error", "negative")
          }
          if (this.platosSeleccinados.length === 1) {
            this.showNotification("Falta per seleccionar un plat", "error", "negative")
          }
          if (this.platosSeleccinados.length === 4) {
            let sendMenu = await this.$axios.post(this.url_server_api + '/añadirMenu', {
              platos: this.platosSeleccinados
            })
            this.showNotification("Se ha añadido el menu a tu pedido correctamente", "check_circle_outline", "positive")
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
