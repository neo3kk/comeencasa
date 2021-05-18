<template>
  <div class="q-pa-md">
    <q-btn round>
      <q-avatar size="42px">
        <img src="https://cdn.quasar.dev/img/avatar2.jpg">
      </q-avatar>
      <q-menu>
        <div class="row no-wrap q-pa-md">
          <div class="column items-center">
            <q-avatar size="72px">
              <img src="https://cdn.quasar.dev/img/avatar4.jpg">
            </q-avatar>

            <div class="text-subtitle1 q-mt-md q-mb-xs">{{ user }}</div>

            <q-btn
              color="primary"
              label="Logout"
              push
              size="sm"
              v-close-popup
              @click="logout"
            />

            <div class="row no-wrap q-pa-md">
              <div class="q-pa-md q-gutter-sm column">
                <q-btn round color="primary" icon="shopping_cart" @click="$router.push('pedidos')">
                  <q-tooltip content-class="bg-accent">Mis pedidos</q-tooltip>
                </q-btn>
                <q-btn round color="deep-orange" icon="account_circle" @click="$router.push('profile')">
                  <q-tooltip content-class="bg-accent">Mi casa</q-tooltip>
                </q-btn>
              </div>
            </div>
          </div>
        </div>
      </q-menu>
    </q-btn>
  </div>
</template>
<script>
export default {
  data() {
    return {
      user: "",
      mobileData: true,
      bluetooth: false
    }
  },
  created() {
    this.user = localStorage.getItem("user")
  },
  methods: {
    logout() {
      localStorage.clear()
      this.$router.push("/login");
    }
  }
}
</script>

