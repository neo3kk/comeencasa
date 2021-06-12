<template>
  <q-page>
    <q-item to="/" exact>
      <q-img src="../assets/logomin.png"/>
    </q-item>
    <div class="q-pa-md">
      <div class="q-gutter-y-md" style="width: 350px">
        <q-tabs
          v-model="tab"
          dense
          class="bg-grey-2 text-grey-7"
          active-color="primary"
          indicator-color="red"
          align="justify"
        >
          <q-tab name="login" label="Login"/>
          <q-tab name="register" label="Register"/>
        </q-tabs>

        <q-tab-panels v-model="tab" animated class="bg-purple-1 text-center">
          <q-tab-panel name="login">
            <q-input label="E-mail" v-model="email"/>
            <q-input type="password" label="Password" v-model="password"/>
            <q-btn class="q-my-lg q-mx-sm" color="blue" label="Login" @click="login"/>
            <q-btn
              type="a"
              :href="url_server_api+'/loginOauth'"
              label="Sign in with google"
              push
              color="negative"
            />
          </q-tab-panel>

          <q-tab-panel name="register">
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
            <q-input filled
                     label="Password"
                     v-model="password"
                     type="password"
                     :error-message="mensaError('password')"
                     :error="$v.password.$invalid"
            />
            <q-uploader
              label="Selecciona una imatge de perfil"
              fieldName="file"
              auto-upload
              class="q-mt-md"
              @uploaded="uploaded"
              method="POST"
              accept=".jpg,.png,image/*"
              :url="url_server_api+'/upload/image'"
            />
            <q-btn class="q-my-lg" color="secondary" label="Register" @click="register"/>
          </q-tab-panel>
        </q-tab-panels>
      </div>
    </div>
  </q-page>
</template>

<script>
import {SETTINGS} from '../settings.js'
import {required, minLength, between, email} from 'vuelidate/lib/validators'

export default {
  data() {
    return {
      name: '',
      last_name: '',
      email: '',
      password: '',
      file: '',
      tab: "login",
      url_server_api: SETTINGS.URL_SERVER_API
    };
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
    password: {
      required,
      minLength: minLength(4)
    }
  }, created() {
    if (localStorage.getItem("tokenLogin")) {
      this.$router.push("/")
    }
  },
  methods: {
    async register() {
      this.$v.$touch()
      if (this.$v.$invalid) {
        this.showNotification("Revisa tots els camps requerits", "error", "negative")
      } else {
        let sendRegister = await this.$axios.post(this.url_server_api + '/register', {
          name: this.name,
          last_name: this.last_name,
          email: this.email,
          password: this.password,
          file: this.file,
        }).then(response => {
          this.showNotification("Ok, ya puedes hacer login", "check_circle_outline", "positive")
        }).catch(error => {
          console.log(error.response.data)
          if (error.response.data.message === "existe") {
            this.showNotification("Este correo ya esta registrado", "error", "negative")
          } else {
            this.showNotification("Algo ha fallado", "error", "negative")
          }

        });
      }
    },
    async login() {

      let sendLogin = await this.$axios.post(this.url_server_api + '/login', {
        email: this.email,
        password: this.password,
      }).catch(error => {
          this.showNotification("Usuario o contraseña incorrectos", "error", "negative")
      });

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
        if (!this.$v.name.name) return 'Introduce tu nombre'
        if (!this.$v.name.required) return 'Campo requerido'
      }
      if (data === 'last_name') {
        if (!this.$v.last_name.last_name) return 'Introduce tus apellidos'
        if (!this.$v.last_name.required) return 'Campo requerido'
      }
      if (data === 'email') {
        if (!this.$v.email.email) return 'Introdueix el teu correu electrònic'
        if (!this.$v.email.required) return 'Camp requerit'
      }
      if (data === 'password') {
        if (!this.$v.email.email) return 'La contraseña debe ser de almenos 4 caracteres'
        if (!this.$v.email.required) return 'Campo requerido'
      }
    }
  }
};
</script>
