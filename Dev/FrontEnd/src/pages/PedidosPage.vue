<template>
  <q-page class="flex flex-center">

    <q-list>
      Pedidos
      <q-item v-for="pedido in pedidos" key="pedido.id">
        <q-item-section @click="$router.replace( 'pedido/'+pedido.id)">
          <q-item-label>Orden de pedido {{ pedido.id }}</q-item-label>
          <q-item-label caption>Precio total: {{ pedido.precio_final }}€</q-item-label>
        </q-item-section>

        <q-item-section side top>
          <q-item-label caption>{{ pedido.fecha_pedido }}</q-item-label>
        </q-item-section>
        <q-separator spaced inset/>
      </q-item>
    </q-list>
  </q-page>
</template>

<script>
import {SETTINGS} from '../settings.js'
import {required, minLength, between, email} from 'vuelidate/lib/validators'

export default {
  data() {
    return {
      pedidos: [],
      tab: "pedidos",
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  created() {
    this.getPedidos();
  },
  methods: {
    async getPedidos() {
      let pedidosFetch = await this.$axios.get(this.url_server_api + '/pedidos');
      this.pedidos = pedidosFetch.data
    }
  }
};
</script>
