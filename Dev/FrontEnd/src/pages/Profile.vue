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
              <q-card-section class="column items-baseline content-center">
                <q-img :src="user.img" alt="" width="200px" height="200px"/>
                <q-uploader
                  label="Selecciona una imatge de perfil"
                  fieldName="file"
                  auto-upload
                  class="q-mt-md"
                  @uploaded="uploaded"
                  method="POST"
                  accept=".jpg,.png,image/*"
                  :url="url_server_api+'/upload/image'"
                 v-if="user.oauth==='false'"/>
                <q-btn class="q-my-lg" color="secondary" label="Cambiar imagen" @click="updateImage"
                       v-if="user.oauth==='false'"/>
              </q-card-section>
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
              <q-btn class="q-my-lg" color="secondary" label="Guardar" @click="updateUser"/>
              <q-btn label="Cambia la contraseña" color="primary" @click="prompt = true" v-if="user.oauth==='false'"/>
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
              <DragAndDrop @alergens="alergenos = $event"></DragAndDrop>
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
              <q-input filled
                       label="Codigo postal"
                       v-model="codigo_postal"/>
              <q-input filled
                       label="Calle"
                       v-model="calle"/>

              <q-input filled
                       label="Numero"
                       v-model="numero"/>
              <q-input filled
                       label="Letra"
                       v-model="letra"/>
              <q-btn class="q-my-lg" color="secondary" label="Guardar Direccion" @click="updateUserDirection()"/>
            </q-card-section>
          </q-card>
        </q-expansion-item>

        <q-expansion-item icon="delete" label="Elimina tu cuenta">
          <q-card>
            <q-card-section>
              <div class="text-h6">Pulsa el boton para eliminar tu cuenta</div>
              <q-btn label="Elimina tu cuenta" color="red" @click="del = true"/>
            </q-card-section>
          </q-card>
        </q-expansion-item>
      </q-list>
    </div>
    <q-dialog v-model="prompt" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">Tu antiguar contaseña</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input dense v-model="last_password" autofocus @keyup.enter="prompt = false" type="password"/>
        </q-card-section>
        <q-card-section>
          <div class="text-h6">Tu nueva contaseña</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input dense v-model="new_password" autofocus @keyup.enter="prompt = false" type="password"/>
        </q-card-section>
        <q-card-section>
          <div class="text-h6">Repite tu nueva contaseña</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          <q-input dense v-model="same_new_password" autofocus @keyup.enter="prompt = false" type="password"/>
        </q-card-section>


        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Cancel" v-close-popup/>
          <q-btn flat label="Add address" v-close-popup @click="updatePassword()"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-dialog v-model="del" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">Estas seguro de borrar tu cuenta?</div>
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Cancel" v-close-popup/>
          <q-btn flat label="Borrar igualmente" color="red" v-close-popup @click="deleteUser()"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>

</template>

<script>
import {required, minLength, between, email} from 'vuelidate/lib/validators'
import {SETTINGS} from "src/settings";

import DragAndDrop from "components/DragAlergenos";

export default {
  name: 'ProfilePage',
  components: {DragAndDrop},
  props: {
    user: {type: Object}
  },
  data() {
    return {
      file: '',
      last_password: '',
      new_password: '',
      same_new_password: '',
      name: '',
      last_name: '',
      calle: '',
      codigo_postal: '',
      numero: '',
      letra: '',
      tab: "login",
      url_server_api: SETTINGS.URL_SERVER_API,
      prompt: false,
      alergenos: [],
      del: false,
    }
  },
  validations: {
    name: {
      required,
      minLength: minLength(3)
    },
    last_name: {
      required,
      minLength: minLength(3)
    }
  },
  async created() {
    if (localStorage.getItem("tokenLogin")) {
      let getUserDetails = await this.$axios.get(this.url_server_api + '/getUserDetails')
      this.name = getUserDetails.data.name
      this.last_name = getUserDetails.data.last_name
      this.email = getUserDetails.data.email
      this.calle = getUserDetails.data.calle
      this.codigo_postal = getUserDetails.data.codigo_postal
      this.numero = getUserDetails.data.numero
      this.letra = getUserDetails.data.letra
    }
  },
  methods: {
    async updateImage(n) {
      let upadteImage = await this.$axios.post(this.url_server_api + '/updateImageUser', {
        file: this.file,
      })
      if (upadteImage.request.status === 202) {
        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Imagen cambiada correctamente'
        })
        this.$router.go(n)
      } else {
        this.$q.notify({
          color: 'red-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Error al cambiar la imagen'
        })
      }
    },

    async updatePassword() {
      if (this.new_password === this.same_new_password) {
        let sendRegister = await this.$axios.post(this.url_server_api + '/changePassword', {
          last_password: this.last_password,
          new_password: this.new_password,
        })
        if (sendRegister.data === 200) {
          this.showNotification("Se ha cambiado la contraseña correctamente", "check_circle_outline", "positive")
        } else {
          this.showNotification("Te has equivocado con la contraseña anterior", "error", "negative")
        }
      } else {
        this.showNotification("Las contraseñas no son iguales", "error", "negative")
      }

    },
    async updateUser() {
      this.$v.$touch()
      if (this.$v.$invalid) {
        this.showNotification("Revisa los campos requeridos", "error", "negative")
      } else {
        let sendRegister = await this.$axios.post(this.url_server_api + '/updateUser', {
          name: this.name,
          last_name: this.last_name,
        }).then(response => {
          this.showNotification("Usuario actualizado correctamente", "check_circle_outline", "positive")
        }).catch(error => {
            this.showNotification("Algo ha fallado", "error", "negative")
          }
        );
      }
    }
    ,
    async deleteUser() {
      let sendRegister = await this.$axios.get(this.url_server_api + '/deleteUser')
      if (sendRegister.data === "ok") {
        localStorage.clear()
        this.$router.push("/login");
      }
    }
    ,
    async updateUserDirection() {
      let sendRegister = await this.$axios.post(this.url_server_api + '/updateUserDirection', {
        calle: this.calle,
        codigo_postal: this.codigo_postal,
        numero: this.numero,
        letra: this.letra
      })
    }
    ,
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
    }
    ,
    uploaded(info) {
      this.file = info.xhr.response;
    }
    ,
    mensaError(data) {
      if (data === 'name') {
        if (!this.$v.name.name) return 'Introduce tu nombre'
        if (!this.$v.name.required) return 'Campo requerido'
      }
      if (data === 'last_name') {
        if (!this.$v.last_name.last_name) return 'Introduce tus apellidos'
        if (!this.$v.last_name.required) return 'Campo requerido'
      }
      if (data === 'new_password') {
        if (!this.$v.same_new_password) return 'La contraseña es demasiado corta'
        if (!this.$v.same_new_password.required) return 'Campo requerido'
      }
    }
  }


}
</script>
