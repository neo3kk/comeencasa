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
          label="Alergeno"
          hint="Alergeno"
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
      <q-markup-table>
        <thead>
        <tr>
          <th class="text-center">Nombre</th>
          <th class="text-center">Imagen</th>
          <th class="text-center">Action</th>
        </tr>
        </thead>
        <tbody v-for="x in ale" :key="x.id">
        <tr>
          <td class="text-center">{{ x.name }}</td>
          <td class="text-center">
            <q-btn color="red" @click="delIngredient(x.name)">Borrar</q-btn>
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
  data() {
    return {
      url_server_api: SETTINGS.URL_SERVER_API,
      db: null,
      name: null,
      ale: [],
      alergeno: {
        name: "",
        imagen: "",
      }
    }
  },
  async created() {
    await this.getAlergenos();
    this.db = await this.getDb();

  },

  methods: {
    async onSubmit() {
      await this.addAlergenoDB(this.name);
      await this.getAlergenos();

    },

    async addToDb(ingredient) {
      let add = await this.$axios.post(this.url_server_api + '/admin/addAlergeno', {
        name: ingredient.name,
      });
      if (add.request.status === 202) {
        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Submitted'
        })
        this.getAlergenos();
      } else {
        this.$q.notify({
          color: 'red-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Error to add ingredient in database'
        })
      }
    },

    async addAlergenoDB(nameAlergeno) {
      let comp = await this.comprovarElementIDXDB(nameAlergeno)
      if (!comp.result) {
        let al = {
          name : this.name
        }
        await this.addAlimentBd(al);
      } else {
        await this.addToDb(comp.result)
      }
    },

    async getAlergenos() {
      let alergenos = await this.$axios.get(this.url_server_api + '/admin/getAllAlergenos')
      this.ale = alergenos.data;
    },

    async delIngredient(ale) {
      let del = await this.$axios.delete(this.url_server_api + '/admin/deleteAlergeno', {
        data: {
          name: ale
        }
      });
      if (del.request.status === 202) {
        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Deleted'
        })
        this.getAlergenos();
      } else {
        this.$q.notify({
          color: 'red-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Error to delete ingredient in database'
        })
      }
    },


    async getDb() {
      return new Promise((resolve, reject) => {
        let request = indexedDB.open(["alergenos"], 1);

        request.onerror = e => {
          reject("Error");
        };

        request.onsuccess = e => {
          resolve(e.target.result);
        };

        request.onupgradeneeded = e => {
          let db = e.target.result;

          let objectStore = db.createObjectStore("Llista", {
            autoIncrement: true,
            keyPath: "id"
          });
          let index = objectStore.createIndex("Index", "name", {
            unique: true
          });
        };
      });
    },

    async comprovarElementIDXDB(alergeno) {
      return new Promise((resolve, reject) => {
        let trans = this.db.transaction(["Llista"], "readwrite");
        let objectStore = trans.objectStore("Llista");
        let myIndex = objectStore.index("Index");
        let elementValidat = myIndex.get(alergeno);
        trans.oncomplete = e => {
          resolve(elementValidat);
        };
      });
    },


    async addAlimentBd(alergeno) {
      return new Promise((resolve, reject) => {
        let trans = this.db.transaction("Llista", "readwrite");
        trans.oncomplete = async e => {
          await this.addToDb(alergeno)
          resolve();
        };
        let store = trans.objectStore("Llista");
        store.add(alergeno);
      });
    },


    onReset() {
      this.name = null
    },
  }
}
</script>

