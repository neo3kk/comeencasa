<template>
  <q-page>
    <div class="q-pa-md">
      <q-list bordered class="rounded-borders">
        <q-expansion-item
          expand-separator
          icon="perm_identity"
          label="Datos personales"
          :caption="user.name"
        >
          <q-card>
            <q-card-section>
              <div class="text-h6">{{ user.img }}</div>
              <div class="text-h6">{{ user.direccion }}</div>
            </q-card-section>
          </q-card>
        </q-expansion-item>

        <q-expansion-item
          expand-separator
          icon="spa"
          label="Alergias"
        >
          <q-card>
            <q-card-section>

            </q-card-section>
          </q-card>
        </q-expansion-item>

        <q-expansion-item
          expand-separator
          icon="house"
          label="Direccion de envio"
          header-class="text-purple"
        >
          <q-card>
            <q-card-section>

            </q-card-section>
          </q-card>
        </q-expansion-item>

        <q-expansion-item icon="menu_book" label="Historico pedidos">
          <q-card>
            <q-card-section>

            </q-card-section>
          </q-card>
        </q-expansion-item>
      </q-list>
    </div>
  </q-page>


</template>

<script>

export default {
  name: 'ProfilePage',
  props: {
    user: {type: Object}
  },
  data() {
    return {}
  }

}
</script>
