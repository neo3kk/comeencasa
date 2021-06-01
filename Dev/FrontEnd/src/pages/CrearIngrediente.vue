<template>
  <q-page>
    <div class="q-pa-md" style="max-width: 400px">
      <q-form
        @submit="onSubmit"
        @reset="onReset"
        class="q-gutter-md"
      >
        <q-input
          filled
          v-model="name"
          label="Ingrediente"
          hint="Name and surname"
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Please type something']"
        />
        <q-input
          filled
          v-model="traduccion"
          label="Ingrediente en ingles"
          hint="Name and surname"
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Please type something']"
        />

        <div>
          <q-btn label="Submit" type="submit" color="primary"/>
          <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm"/>
        </div>
      </q-form>

    </div>
    <div class="q-pa-md">
      <q-list bordered>
        <q-item clickable v-ripple v-for="ingrediente in ing" :key="ingrediente.id">
          <q-item-section thumbnail>
            <img src="https://cdn.quasar.dev/img/mountains.jpg">
          </q-item-section>
          <q-item-section>{{ ingrediente.name}}</q-item-section>
          <q-item-section>{{ ingrediente.traduccion}}</q-item-section>
          <q-btn color="red">Borrar</q-btn>
        </q-item>
      </q-list>
    </div>
  </q-page>


</template>

<script>
import {SETTINGS} from "src/settings";

export default {
  data() {
    return {
      url_server_api: SETTINGS.URL_SERVER_API,
      name: null,
      traduccion:null,
      ing: []
    }
  },
  created() {
    this.getIngredientes();

  },

  methods: {
    async onSubmit() {
      let add = await this.$axios.post(this.url_server_api + '/admin/addingredient', {
        name: this.name,
        traduccion: this.traduccion
      });
      if (add.request.status === 202) {
        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Submitted'
        })
        this.getIngredientes();
      } else {
        this.$q.notify({
          color: 'red-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Error'
        })
      }

    },
    onReset() {
      this.name = null
      this.traduccion = null
    },
    async getIngredientes() {
      let ingredientes = await this.$axios.get(this.url_server_api + '/admin/getAllIngredientes')
      this.ing = ingredientes.data;
    }
  }
}
</script>

