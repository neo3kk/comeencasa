<template>
  <q-page class="q-pa-md">
      <q-card v-for="pedido in pedidos" :key="pedido.id" class="my-card">
        <q-card-section-section @click="$router.push( 'pedido/'+pedido.id)">
            <div class="text-h4">Orden de pedido {{ pedido.id }}</div>
            <div class="text-h6">{{ pedido.fecha_pedido }}</div>
            <div class="text-h6">Precio total: {{ pedido.precio_final }}€</div>
        </q-card-section-section>
        <q-separator spaced inset/>
      </q-card>
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
