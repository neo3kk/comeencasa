<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated style="background-color: green">
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <q-toolbar-title class="flex justify-center">
          <img
            alt="COME EN CASA"
            src="~src/assets/logomin.png"
            id="logomin"
            @click="$router.push('/')"
          >
        </q-toolbar-title>

        <div class="q-pa-md" v-show=showUserInfo>
          <q-btn round>
            <q-avatar size="42px">
              <q-img :src="user.img" alt=""/>
            </q-avatar>
            <q-menu>
              <div class="row no-wrap q-pa-md">
                <div class="column items-center">
                  <q-avatar size="72px">
                    <q-img :src="user.img" alt=""/>
                  </q-avatar>

                  <div class="text-subtitle1 q-mt-md q-mb-xs">{{ user.email }}</div>

                  <q-btn
                    color="green"
                    label="Logout"
                    push
                    size="sm"
                    v-close-popup
                    @click="logout"
                  />
                  <div class="row no-wrap q-pa-md">
                    <div class="q-pa-md q-gutter-sm column">
                      <q-btn round color="green" icon="menu_book" @click="$router.push('/profile/pedidos')">
                        <q-tooltip content-class="bg-accent">Mis pedidos</q-tooltip>
                      </q-btn>
                      <q-btn round color="green" icon="shopping_cart" @click="$router.push('/profile/carrito')">
                        <q-tooltip content-class="bg-accent">Mi carrito</q-tooltip>
                      </q-btn>
                      <q-btn round color="deep-orange" icon="account_circle" @click="$router.push('/profile')">
                        <q-tooltip content-class="bg-accent">Mi casa</q-tooltip>
                      </q-btn>
                      <q-btn v-show="user.admin" round color="negative" icon="shield"
                             @click="$router.push('/admin')">
                        <q-tooltip content-class="bg-accent">Admin</q-tooltip>
                      </q-btn>

                    </div>
                  </div>
                </div>
              </div>
            </q-menu>
          </q-btn>
        </div>
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
      <router-view :user="user"/>
    </q-page-container>
  </q-layout>
</template>

<script>
import EssentialLink from 'src/components/EssentialLink.vue'
import {SETTINGS} from "src/settings";

const linksData = [
  {
    title: 'Platos',
    caption: 'Todos nuestros platos',
    icon: 'room_service',
    link: '/platos'
  },
  {
    title: 'Donde estamos',
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
    title: 'Nuevo Pedido',
    caption: 'Pide ya tu comida y te lo llevamos lo antes posible',
    icon: 'add_circle',
    link: "/profile/nuevopedido"
  },
  {
    title: 'Mi cuenta',
    caption: 'Entra a tu cuenta y disfruta de la comida',
    icon: 'account_circle',
    link: "/login"
  }
];

export default {
  name: 'MainLayout',
  components: { EssentialLink},

  data() {
    return {
      user: {
        name: "",
        email:"",
        direccion:"",
        admin: false,
        img: '',
        oauth:false
      },
      url_server_api: SETTINGS.URL_SERVER_API,
      leftDrawerOpen: false,
      essentialLinks: linksData,
      showUserInfo: false,
      logged: false,
    }
  },
  async created() {
    this.user.email = localStorage.getItem("user")
    this.user.img = localStorage.getItem("picture")
    this.user.oauth = localStorage.getItem("oauth")
    if (this.user.email === "admin@comeencasa.com") {
      this.user.admin = true;
    }

    if (localStorage.getItem("tokenLogin")) {
      this.essentialLinks.forEach(function (z) {
        if (z.title === "Mi cuenta") {
          z.link = "/profile"
        }
      })
      this.showUserInfo = true
    }
  },
  methods:{
    logout() {
      localStorage.clear()
      this.$router.push("/login");
    },
  }
}
</script>
