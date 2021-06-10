<template>
  <q-page class="text-center justify-center">
    <h3>Benvingut {{ user.name }}</h3>
    <div class="q-pa-md q-gutter-md fit flex wrap justify-center items-center content-center">

      <q-card class="my-card">
        <q-card-section class="bg-primary text-white">
          <div class="text-h6">Ingredientes</div>
          <div class="text-subtitle2">Edita la lista de ingredientes</div>
        </q-card-section>

        <q-separator/>

        <q-card-actions align="around">
          <q-btn flat @click="$router.push('/admin/ingredientes')">Entra</q-btn>
        </q-card-actions>
      </q-card>
      <q-card class="my-card">
        <q-card-section class="bg-primary text-white">
          <div class="text-h6">Alergenos</div>
          <div class="text-subtitle2">Edita la lista de alergenos</div>
        </q-card-section>

        <q-separator/>

        <q-card-actions align="around">
          <q-btn flat @click="$router.push('/admin/alergenos')">Entra</q-btn>
        </q-card-actions>
      </q-card>
      <q-card class="my-card">
        <q-card-section class="bg-purple text-white">
          <div class="text-h6">Platos</div>
          <div class="text-subtitle2">Crea los diferentes platos</div>
        </q-card-section>

        <q-card-actions align="around">
          <q-btn flat @click="$router.push('/admin/nuevoplato')">Entra</q-btn>
        </q-card-actions>
        <q-card-actions align="around">
          <q-btn flat @click="platosInvisible = !platosInvisible">Edita tus platos</q-btn>
        </q-card-actions>
      </q-card>
      <q-card class="my-card">
        <q-card-section class="bg-green text-white">
          <div class="text-h6">Usuarios</div>
          <div class="text-subtitle2">Administra los usuarios</div>
        </q-card-section>

        <q-card-actions align="around">
          <q-btn flat @click="$router.push('/admin/usersadmin')">Entra</q-btn>
        </q-card-actions>
      </q-card>
    </div>
    <div v-if="platosInvisible">
      <div class="text-h3">Clicka sobre el plato a modificar</div>
      <q-markup-table flat bordered>
        <thead>
        <tr>
          <th class="text-left">Id</th>
          <th class="text-right">Nombre del plato</th>
          <th class="text-right">Nombre Traducido</th>
          <th class="text-right">Precio</th>
          <th class="text-right">Buttons</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="plato in platos" :key="plato.id">
          <td class="text-left">{{ plato.id }}</td>
          <td class="text-right">{{ plato.nombre }}</td>
          <td class="text-right">{{ plato.traduccion }}</td>
          <td class="text-right">{{ plato.precio }}</td>
          <td class="text-right">
            <q-btn-toggle v-model="plato.visible" push glossy toggle-color="primary" :options="[
              {label: 'Visible', value: true},{label: 'Invisible', value: false}]" @click="setVisibility(plato)"/>
            <q-btn color="red" icon="delete" @click="deletePlato(plato.id)" label="Delete"/>
            <q-btn color="blue" icon="edit" @click="$router.push( 'admin/editplato/'+plato.id)" label="Editar"/>
          </td>
        </tr>
        </tbody>
      </q-markup-table>
    </div>
  </q-page>


</template>

<script>


import {SETTINGS} from "src/settings";

export default {
  name: 'AdminPage',
  props: {
    user: {type: Object}
  },

  data() {
    return {
      post: "",
      platosInvisible: false,
      platos: [],
      url_server_api: SETTINGS.URL_SERVER_API
    };
  },
  async created() {
    if (this.user.admin !== true) {
      await this.$router.push("/unauthorized");
    } else {
      await this.getPlatos()
    }
  },
  methods: {
    async getPlatos() {
      let platosFetch = await this.$axios.get(this.url_server_api + '/platos');
      this.platos = platosFetch.data
    },
    async deletePlato(id) {
      let platosFetch = await this.$axios.delete(this.url_server_api + '/deletePlato', {data: {idplato: id}}).then(response => {
        this.showNotification("Se ha borrado el plato correctamente", "check_circle_outline", "positive")
      });
      for (let i = 0; i < this.platos.length; i++) {
        if (this.platos[i].id === id) {
          this.platos.splice(i, 1)
        }
      }
    },
    async setVisibility(plato) {
      let setVisibility = await this.$axios.post(this.url_server_api + '/setVisibility', {
        idplato: plato.id,
        visible: plato.visible,
      })
    },
    showNotification(content, icon, color) {
      this.$q.notify({
        message: content,
        color: color,
        icon: icon,
        actions: [
          {
            label: 'OK', color: 'white', handler: () => {
              this.tab = "nuevoPedido"
            }
          }
        ]
      })
    }
  }
}
</script>

<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 250px
</style>


