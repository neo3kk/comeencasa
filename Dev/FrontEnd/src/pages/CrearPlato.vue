<template>
  <q-page>
    <div class="q-pa-md">
      <q-form @submit="onSubmit" class="q-gutter-md">
        <div class="q-gutter-y-md column" style="max-width: 300px">
          <q-input v-model="nombre" label="Nombre del plato"/>
          <q-input v-model="traduccion" label="Traduccion"/>
          <q-select v-model="tipo_plato" :options="options" label="Tipo de plato"/>
          <q-input v-model="description" filled type="textarea"/>
          <q-input outlined v-model="precio" type="number" prefix="€">
            <template v-slot:append>
              <q-avatar>
                <img src="https://cdn.quasar.dev/logo-v2/svg/logo.svg">
              </q-avatar>
            </template>
          </q-input>
          <div>
            <q-btn label="Submit" type="submit" color="primary"/>
          </div>
        </div>
        <div>
          <q-list bordered separator>
            <q-item clickable v-ripple v-for="ingrediente in ingredientesSeleccionados" :key="ingrediente.id">
              <q-item-section>
                <q-item-label>{{ingrediente.traduccion}}</q-item-label>
                <q-item-label caption>{{ingrediente.name}}</q-item-label>
              </q-item-section>
            </q-item>
          </q-list>
        </div>
      </q-form>
      <q-option-group
        v-model="separator"
        inline
        class="q-mb-md"
        :options="[
        { label: 'Horizontal', value: 'horizontal' },
        { label: 'Vertical', value: 'vertical' },
        { label: 'Cell', value: 'cell' },
        { label: 'None', value: 'none' }
      ]"
      />

      <q-markup-table :separator="separator" flat bordered>
        <thead>
        <tr>
          <th class="text-left">Id</th>
          <th class="text-right">Nombre del Ingrediente</th>
          <th class="text-right">Nombre Traducido</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="ingrediente in ingredientes" :key="ingrediente.id" @click="añadirIngrediente(ingrediente.id)">
          <td class="text-left">{{ingrediente.id}}</td>
          <td class="text-right">{{ingrediente.name}}</td>
          <td class="text-right">{{ingrediente.traduccion}}</td>
        </tr>
        </tbody>
      </q-markup-table>
    </div>
  </q-page>


</template>

<script>


  import {SETTINGS} from "src/settings";

  export default {
    name: 'CrearIngrediente',
    data() {
      return {
        separator: 'vertical',
        tipo_plato: null,
        options: ['Entrante', 'Primero', 'Postre', 'Bebida'],
        description: '',
        nombre: '',
        precio: null,
        ingredientes: [],
        traduccion: '',
        ingredientesSeleccionados: [],
        url_server_api: SETTINGS.URL_SERVER_API,
      }
    },
    async created() {
      let ingredientesFetch = await this.$axios.get(this.url_server_api + '/admin/getAllIngredientes')
      console.log(ingredientesFetch.data)
      this.ingredientes = ingredientesFetch.data;
    },
    methods: {
      async onSubmit(){
        let crearPlato = await this.$axios.post(this.url_server_api + '/crearPlato', {
          nombre: this.nombre,
          precio: this.precio,
          description: this.description,
          tipo_plato: this.tipo_plato,
          traduccion: this.traduccion,
          ingredientes: this.ingredientesSeleccionados,
        })
      },
      añadirIngrediente(id){
        var esta = false;
        this.ingredientesSeleccionados.forEach(ingrediente=>{
          if (ingrediente.id === id){
            esta= true
          }
        })
        this.ingredientes.forEach(ingrediente=>{
          if (ingrediente.id === id && !esta){
            this.ingredientesSeleccionados.push(ingrediente)
          }
        })
      }
    }
  }
</script>
