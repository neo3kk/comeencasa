<template>
  <div class="q-pa-md">
    <q-btn round glossy color="blue" icon="house">
      <q-menu>
        <div class="row no-wrap q-pa-md">
          <!--          <div class="column">
                      <div class="text-h6 q-mb-md">Settings</div>
                      <q-toggle v-model="mobileData" label="Use Mobile Data" />
                      <q-toggle v-model="bluetooth" label="Bluetooth" />
                    </div>

                    <q-separator vertical inset class="q-mx-lg" />-->

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
    logout(){
      localStorage.clear()
      this.$router.push("/login");
    }
  }
}
</script>

