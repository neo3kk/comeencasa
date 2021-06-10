<template>
  <q-page>
    <div class="q-pa-md">
      <q-form @submit="onSubmit" class="q-gutter-md column">
        <div class="row justify-around col" >
          <div class="col-6">
            Imagen
          </div>
          <div class="q-gutter-y-md col-6 justify-around column">
            <div class="row col items-center justify-around">
              <q-input v-model="nombre" label="Nombre del plato" class="col q-pa-sm"/>
              <q-input v-model="traduccion" label="Traduccion" class="col q-pa-sm"/>
            </div>
            <div class="row col items-center justify-around">
              <q-select v-model="tipo_plato" :options="options" label="Tipo de plato" class="col q-pa-sm"/>
              <q-input v-model="description" filled type="textarea" class="col q-pa-sm"/>
            </div>

            <div class="row col items-center justify-around q-pt-lg" >
              <q-input outlined v-model="precio" type="float" prefix="€" class="col">
                <template v-slot:append>
                  <q-avatar>
                    <img src="https://cdn.quasar.dev/logo-v2/svg/logo.svg">
                  </q-avatar>
                </template>
              </q-input>
              <q-btn-toggle v-model="visible" class="col" push glossy toggle-color="primary" :options="[
              {label: 'Visible', value: true},{label: 'Invisible', value: false}]"/>
            </div>

          </div>
        </div>
        <div class="col">
          <q-btn label="Submit" type="submit" color="primary"/>
        </div>

      </q-form>
      <div class="row justify-around">
        <div  class="col-4">
          <h3>Ingredientes:</h3>
          <q-list bordered separator>
            <q-item clickable v-ripple v-for="ingrediente in ingredientesSeleccionados" :key="ingrediente.id">
              <q-item-section>
                <q-item-label>{{ingrediente.name}}</q-item-label>
                <q-item-label caption>{{ingrediente.traduccion}}</q-item-label>
              </q-item-section>
              <q-item-section side top>
                <q-item-label>
                  <q-btn color="red" icon="delete" @click="deleteIngrediente(ingrediente.id)" label="Delete"/>
                </q-item-label>
              </q-item-section>
            </q-item>
          </q-list>
        </div>

        <div class="col-4">
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
          <q-markup-table :separator="separator" flat bordered >
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
      </div>

    </div>
  </q-page>


</template>

<script>


  import {SETTINGS} from "src/settings";

  const stripe = require('stripe')('sk_test_KnQIFLmpCoWbMWGMXTP23W7V00jj1MLOZi');
  import {StripeCheckout} from '@vue-stripe/vue-stripe';

  export default {
    name: 'CrearIngrediente',
    data() {
      return {
        visible: false,
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
        publishableKey: SETTINGS.STRIPE_PUBLISHABLE_KEY,
        plato: '',
        id: '',
        energia: 0,
        azucar: 0,
        grasas: 0,
        proteinas: 0

      }
    },
    async created() {
      let ingredientesFetch = await this.$axios.get(this.url_server_api + '/admin/getAllIngredientes')
      this.ingredientes = ingredientesFetch.data;
      this.id = this.$router.currentRoute.params.id
      if (this.id !== undefined) {
        this.getPlatoById(this.id)
      }
    },
    methods: {
      async onSubmit() {
        if (this.id === '' || this.id == undefined) {
          let crearPlato = await this.$axios.post(this.url_server_api + '/crearPlato', {
            nombre: this.nombre,
            precio: this.precio,
            description: this.description,
            tipo_plato: this.tipo_plato,
            traduccion: this.traduccion,
            ingredientes: this.ingredientesSeleccionados,
            energia: this.energia,
            azucar: this.azucar,
            grasas: this.grasas,
            proteinas: this.proteinas,
            visible: this.visible.toString()
          }).then(response => {
            if (response.data === 400) {
              this.showNotification("Ya existe un plato con el mismo nombre en la base de datos", "error", "negative")
            } else {
              this.showNotification("Se ha añadido el plato correctamente", "check_circle_outline", "positive")
            }
          })


          const product = await stripe.products.create({
            name: this.nombre,
          });

        } else {
          let guardarPlato = await this.$axios.post(this.url_server_api + '/guardarPlato', {
            id: this.id,
            nombre: this.nombre,
            precio: this.precio,
            description: this.description,
            tipo_plato: this.tipo_plato,
            traduccion: this.traduccion,
            ingredientes: this.ingredientesSeleccionados,
            visible: this.visible.toString(),
            energia: this.energia,
            azucar: this.azucar,
            grasas: this.grasas,
            proteinas: this.proteinas,
          })
        }

      },
      deleteIngrediente(id) {
        for (let i = 0; i < this.ingredientesSeleccionados.length; i++) {
          if (this.ingredientesSeleccionados[i].id === id) {
            this.energia = Math.abs(this.energia - parseFloat(this.ingredientesSeleccionados[i].energia))
            this.azucar = Math.abs(this.azucar - parseFloat(this.ingredientesSeleccionados[i].azucar))
            this.grasas = Math.abs(this.grasas - parseFloat(this.ingredientesSeleccionados[i].grasas))
            this.proteinas = Math.abs(this.proteinas - parseFloat(this.ingredientesSeleccionados[i].proteinas))
            console.log(this.proteinas)
            this.ingredientesSeleccionados.splice(i, 1)
          }
        }
      },
      async getPlatoById(id) {
        let menuFetch = await this.$axios.post(this.url_server_api + '/getPlatoById', {
          idplato: id
        });
        let ingredientesByPlato = await this.$axios.post(this.url_server_api + '/getIngredientesByPlato', {
          idplato: id
        });
        ingredientesByPlato.data.forEach(ingredientesByPlato => {
          this.añadirIngrediente(ingredientesByPlato.id)
        })

        var plato = menuFetch.data;
        this.description = plato.description
        this.traduccion = plato.traduccion
        this.id = plato.id
        this.nombre = plato.nombre
        this.precio = plato.precio
        this.tipo_plato = plato.tipo_de_plato
        this.visible = plato.visible
        this.energia = parseFloat(plato.energia)
        this.grasas = parseFloat(plato.grasas)
        this.azucar = parseFloat(plato.azucar)
        this.proteinas = parseFloat(plato.proteinas)
      },
      añadirIngrediente(id) {
        var esta = false;
        this.ingredientesSeleccionados.forEach(ingrediente => {
          if (ingrediente.id === id) {
            esta = true
          }
        })
        this.ingredientes.forEach(ingrediente => {
          if (ingrediente.id === id && !esta) {
            this.energia += parseFloat(ingrediente.energia)
            this.azucar += parseFloat(ingrediente.azucar)
            this.grasas += parseFloat(ingrediente.grasas)
            this.proteinas += parseFloat(ingrediente.proteinas)
            this.ingredientesSeleccionados.push(ingrediente)
          }
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
      },
    }
  }
</script>
