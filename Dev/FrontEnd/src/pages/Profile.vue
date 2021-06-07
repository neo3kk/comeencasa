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
              <q-input filled
                       label="Nom"
                       v-model="name"
                       :error-message="mensaError('name')"
                       :error="$v.name.$invalid"
              />
              <q-input filled
                       label="Cognoms"
                       v-model="last_name"
                       :error-message="mensaError('last_name')"
                       :error="$v.last_name.$invalid"
              />
              <q-input filled
                       label="Email"
                       v-model="email"
                       :error-message="mensaError('email')"
                       :error="$v.email.$invalid"
              />

              <q-btn class="q-my-lg" color="secondary" label="Update" @click="updateUser"/>
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
  import {required, minLength, between, email} from 'vuelidate/lib/validators'
  import {SETTINGS} from "src/settings";
export default {
  name: 'ProfilePage',
  props: {
    user: {type: Object}
  },
  data() {
    return {
      name: '',
      last_name: '',
      email: '',
      tab: "login",
      url_server_api: SETTINGS.URL_SERVER_API}
  },
  validations: {
    name: {
      required,
      minLength: minLength(3)
    },
    email: {
      required,
      email
    },
    last_name: {
      required,
      minLength: minLength(3)
    },
  },
  async created() {
    if (localStorage.getItem("tokenLogin")) {
      let getUserDetails = await this.$axios.get(this.url_server_api + '/getUserDetails')
      this.name = getUserDetails.data.name
      this.last_name = getUserDetails.data.last_name
      this.email = getUserDetails.data.email
    }
  },
  methods:{
    async updateUser() {
      this.$v.$touch()
      if (this.$v.$invalid) {
        this.showNotification("Revisa tots els camps requerits", "error", "negative")
      } else {
        let sendRegister = await this.$axios.post(this.url_server_api + '/updateUser', {
          name: this.name,
          last_name: this.last_name,
          email: this.email,
        }).then(response => {
          this.showNotification("Registre completat, ja pots iniciar sessió", "check_circle_outline", "positive")
        }).catch(error => {
          if (error.response.data.message == "google user") {
            this.showNotification("Aquest correu ja s'ha registrat a través de Google", "error", "negative")
          } else if (error.response.data.message == "local user") {
            this.showNotification("Aquest correu ja s'ha registrat", "error", "negative")
          } else {
            this.showNotification("Ens falten algunes dades", "error", "negative")
          }

        });
      }
    },
    showNotification(content, icon, color) {
      this.$q.notify({
        message: content,
        color: color,
        icon: icon,
        actions: [
          {
            label: 'OK', color: 'white', handler: () => {
              this.tab = "login"
            }
          }
        ]
      })
    },
    uploaded(info) {
      this.file = info.xhr.response;
    },
    mensaError(data) {
      if (data === 'name') {
        if (!this.$v.name.name) return 'Introdueix el teu nom'
        if (!this.$v.name.required) return 'Campo requerido'
      }
      if (data === 'last_name') {
        if (!this.$v.last_name.last_name) return 'Introdueix el teu cognom/s'
        if (!this.$v.last_name.required) return 'Campo requerido'
      }
      if (data === 'email') {
        if (!this.$v.email.email) return 'Introdueix el teu correu electrònic'
        if (!this.$v.email.required) return 'Camp requerit'
      }
    }
  }


}
</script>
