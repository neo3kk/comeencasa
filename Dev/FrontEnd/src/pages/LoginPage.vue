<template>
  <q-page>
    <q-img src="../statics/logo.png" style="max-width: 150px;"/>
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
              :href="''"
              label="Sign in with google"
              push
              color="negative"
            />
          </q-tab-panel>

          <q-tab-panel name="register">
            <q-input filled
                     label="Nombre"
                     v-model="name"
                     :error-message="'Hay errores'"
                     :error="''"
            />
            <q-input filled
                     label="Apellidos"
                     v-model="last_name"
                     :error-message="'Hay errores'"
                     :error="''"
            />
            <q-input filled
                     label="Email"
                     v-model="email"
                     :error-message="'Hay errores'"
                     :error="''"
            />
            <q-input filled
                     label="Password"
                     v-model="password"
                     type="password"
                     :error-message="'Hay errores'"
                     :error="''"
            />
            <q-uploader
              label="Selecciona una imagen de perfil"
              fieldName="file"
              auto-upload
              class="q-mt-md"
              @uploaded="uploaded"
              method="POST"
              accept=".jpg,.png,image/*"
              :url="'#'"
              multiple
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
  import { required, minLength, between, email } from 'vuelidate/lib/validators'

  export default {
    data() {
      return {
        name: '',
        last_name: '',
        email: '',
        password: '',
        image: '',
        tab: "login"
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
    },
    methods: {
      async register() {

      },
      async login() {

      }
    }
  };
</script>
