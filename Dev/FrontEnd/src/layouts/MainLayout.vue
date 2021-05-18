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
    title: 'Menus',
    caption: 'Todos nuestros menus',
    icon: 'room_service',
    link: '/test'
  },
  {
    title: 'Mis Pedidos',
    caption: '',
    icon: 'shopping_cart',
    link: '/pedidos'
  },
  {
    title: 'Discord Chat Channel',
    caption: 'chat.quasar.dev',
    icon: 'chat',
    link: 'https://chat.quasar.dev'
  },
  {
    title: 'Forum',
    caption: 'forum.quasar.dev',
    icon: 'record_voice_over',
    link: 'https://forum.quasar.dev'
  },
  {
    title: 'Twitter',
    caption: '@quasarframework',
    icon: 'rss_feed',
    link: 'https://twitter.quasar.dev'
  },
  {
    title: 'Facebook',
    caption: '@QuasarFramework',
    icon: 'public',
    link: 'https://facebook.quasar.dev'
  },
  {
    title: 'Quasar Awesome',
    caption: 'Community Quasar projects',
    icon: 'favorite',
    link: 'https://awesome.quasar.dev'
  }
];

export default {
  name: 'MainLayout',
  components: {UserInfo, EssentialLink},
  data() {
    return {
      leftDrawerOpen: false,
      essentialLinks: linksData,
      showUserInfo: false
    }
  },
  created() {
    if(localStorage.getItem("tokenLogin")){
      this.showUserInfo=true
    }
  }
}
</script>
