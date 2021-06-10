<template>
  <q-page class="flex flex-center">

    <q-list>
      Tu lista de productos
      <q-item v-for="plato in platos" key="plato.id" v-if="platos!=null" clickable>
        <q-item-section @click="$router.push( '/plato/'+plato.id)">
          <q-item-label>Nombre: {{ plato.nombre }}</q-item-label>
          <q-item-label caption>{{ plato.description }}</q-item-label>
        </q-item-section>

        <q-item-section side top>
          <q-item-label caption>Precio total: {{ plato.precio }}€</q-item-label>
          <q-item-label caption>{{ plato.tipo_de_plato }}</q-item-label>
          <q-item-label caption><q-btn color="red" icon="delete" @click="deletePlato(plato.id)"label="Delete" v-if="id === undefined"/></q-item-label>
        </q-item-section>
        <q-separator spaced inset/>
      </q-item>

      <q-item v-for="menu in menus" key="menu.idmenu"  clickable @click="$router.push( '/profile/pedidomenu/'+menu.idmenu)">
        <q-item-section>
          <q-item-label>Nombre: {{ menu.nombre_menu}}</q-item-label>
          <q-item-label caption><p v-for="plato in menu.platos"> {{ plato.nombre }}</p></q-item-label>
        </q-item-section>
        <q-item-section side top>
          <q-item-label caption><q-btn color="red" icon="delete" label="Delete" @click="deleteMenu(menu.idmenu)" v-if="id === undefined"
                                       ></q-btn></q-item-label>
        </q-item-section>
        <q-separator spaced inset/>
      </q-item>

    </q-list>
    <q-btn color="indigo" label="Confirma tu Pedido" style="width: 100%" @click="replace(pedidoId)" v-if="id === undefined"></q-btn>
  </q-page>
</template>

<script>
import {SETTINGS} from "src/settings";

export default {
  name: "carrito.vue",
  data() {
    return {
      id: undefined,
      pedidoId: '',
      menus: [],
      platos: [],
      tab: "pedidos",
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  async created() {
    this.id = this.$router.currentRoute.params.id
    if (this.id !== undefined) {
      let pedidoId = await this.$axios.post(this.url_server_api + '/getPedidoById',{
        id: this.id
      });
      this.pedidoId = pedidoId.data
      let platosFetch = await this.$axios.post(this.url_server_api + '/getCarritoById',{
        id: this.id
      });
      this.platos = platosFetch.data
      var mymenus = []
      let menusFetch = await this.$axios.post(this.url_server_api + '/getMenusById',{
        id: this.id
      });
      for (const menu of menusFetch.data) {
        let platosMenuFetch = await this.$axios.post(this.url_server_api + '/getPlatosMenu', {
          idmenu: menu.id
        });
        mymenus.push({
          'idmenu': menu.id,
          'nombre_menu': menu.nombre_menu,
          'platos': platosMenuFetch.data
        })
        console.log(platosMenuFetch.data)
      }
      this.menus = mymenus
    }else{
      await this.getPlatos();
      await this.getMenus();
      await this.getPedido();
    }

  },
  methods: {
    async getPedido() {
      let pedidoId = await this.$axios.get(this.url_server_api + '/getPedido');
      this.pedidoId = pedidoId.data
    },
    async getPlatos() {
      let platosFetch = await this.$axios.get(this.url_server_api + '/getCarrito');
      this.platos = platosFetch.data
    },
    async getMenus() {
      var mymenus = []
      let menusFetch = await this.$axios.get(this.url_server_api + '/getMenus');
      for (const menu of menusFetch.data) {
        let platosMenuFetch = await this.$axios.post(this.url_server_api + '/getPlatosMenu', {
          idmenu: menu.id
        });
        mymenus.push({
          'idmenu': menu.id,
          'nombre_menu': menu.nombre_menu,
          'platos': platosMenuFetch.data
        })
        console.log(platosMenuFetch.data)
      }
      this.menus = mymenus
    },
    replace(pedidoId){
      var boolean = false;
      var name=0;
      this.platos.forEach(plato=>{
        console.log(plato.visible)
        if (!plato.visible){
          boolean = true;
          name = plato.nombre
        }
      })
      this.menus.forEach(menu=>{
        menu.platos.forEach(plato=>{
          console.log(plato.visible)
          if (!plato.visible){
            boolean = true;
            name = plato.nombre
          }
        })
      })
      if (!boolean){
        this.$router.push( 'pago/'+pedidoId)
      }else{
        this.showNotification("El plato: "+name+" ya no se encuentra disponible en este momento, " +
          "borrelo de su pedido elija otro producto, disculpe las molestias", "error", "negative")
      }
    },
    async deletePlato(id){
      for (let i = 0; i < this.platos.length; i++) {
        if (this.platos[i].id === id){
          this.platos.splice(i, 1)
        }
      }
      let platosMenuFetch = await this.$axios.delete(this.url_server_api + '/deletePlatoPedido' ,{data:{idplato: id}});
    },
    async deleteMenu(id){
      for (let i = 0; i < this.menus.length; i++) {
        if (this.menus[i].idmenu === id){
          this.menus.splice(i, 1)
        }
      }
      let menusFetch = await this.$axios.delete(this.url_server_api + '/deleteMenuPedido' ,{data:{idmenu: id}});
    },
    showNotification(content, icon, color) {
      this.$q.notify({
        message: content,
        color: color,
        icon: icon,
        actions: [
          {
            label: 'OK', color: 'white', handler: () => {
              this.tab = "login"
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
