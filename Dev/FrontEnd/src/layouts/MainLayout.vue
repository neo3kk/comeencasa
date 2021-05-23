<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <q-toolbar-title align="center">
          <img
            alt="COME EN CASA"
            src="~src/assets/logomin.png"
            id="logomin">
        </q-toolbar-title>
        <UserInfo v-show=showUserInfo></UserInfo>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      content-class="bg-grey-1"
    >
      <q-list>
        <q-item-label
          header
          class="text-grey-8"
        >
          Que quieres comer hoy?
        </q-item-label>
        <EssentialLink
          v-for="link in essentialLinks"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view/>
    </q-page-container>
  </q-layout>
</template>

<script>
import EssentialLink from 'src/components/EssentialLink.vue'
import UserInfo from "components/UserInfo";

const linksData = [
  {
    title: 'Platos',
    caption: 'Todos nuestros platos',
    icon: 'room_service',
    link: '/platos'
  },
  {
    title: 'Tarifas',
    caption: 'Una tarifa para cada ocasión',
    icon: 'credit_card',
    link: '/tarifas'
  },
  {
    title: 'Envios',
    caption: 'Descubre si estas en nuestro radio de actividad',
    icon: 'delivery_dining',
    link: '/envios'
  },
  {
    title: 'Alergenos',
    caption: 'Leyenda de los alergenos',
    icon: 'spa',
    link: '/alergenos'
  },
  {
    title: 'Mi cuenta',
    caption: 'Entra a tu cuenta y disfruta de la comida',
    icon: 'account_circle',
    link: "/login"
  },
  {
    title: 'Nuevo Pedido',
    caption: 'Pide ya tu comida y te lo llevamos lo antes posible',
    icon: 'add_circle',
    link: "/nuevoPedido"
  }
];

export default {
  name: 'MainLayout',
  components: {UserInfo, EssentialLink},
  data() {
    return {
      leftDrawerOpen: false,
      essentialLinks: linksData,
      showUserInfo: false,
      logged: false
    }
  },
  created() {
    if (localStorage.getItem("tokenLogin")) {
      this.essentialLinks.forEach(function (z){
        if(z.title === "Mi cuenta"){
          z.link="/profile"
        }
      })
      this.showUserInfo = true
    }
  },
}
</script>
