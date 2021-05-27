<template>
  <q-page class="flex flex-center">

    <q-list>
      Tu lista de productos
      <q-item v-for="plato in platos" key="pedido.id" v-if="platos!=null">
        <q-item-section>
          <q-item-label>Nombre: {{ plato.nombre }}</q-item-label>
          <q-item-label caption>{{ plato.description }}</q-item-label>
        </q-item-section>

        <q-item-section side top>
          <q-item-label caption>Precio total: {{ plato.precio }}€</q-item-label>
          <q-item-label caption>{{ plato.tipo_de_plato }}</q-item-label>

        </q-item-section>
        <q-separator spaced inset/>
      </q-item>
    </q-list>
  </q-page>
</template>

<script>
import {SETTINGS} from "src/settings";

export default {
  name: "carrito.vue",
  data() {
    return {
      platos: [],
      tab: "pedidos",
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  created() {
    this.getPlatos();
  },
  methods: {
    async getPlatos() {
      let platosFetch = await this.$axios.get(this.url_server_api + '/getCarrito');
      this.platos = platosFetch.data
    }
  }
}
</script>

<style scoped>

</style>
