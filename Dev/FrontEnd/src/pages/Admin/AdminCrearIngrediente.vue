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
          <q-item-section>{{ ingrediente.name }}</q-item-section>
          <q-item-section>{{ ingrediente.traduccion }}</q-item-section>
          <q-item-section>{{ ingrediente.energia }}</q-item-section>
          <q-item-section>{{ ingrediente.azucar }}</q-item-section>
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
      db: null,
      name: null,
      traduccion: null,
      ing: [],
      ingredient: {
        name: "",
        traduccion: "",
        azucar: "",
        energia: "",
        grasasSat: "",
        proteinas: ""
      }
    }
  },
  async created() {
    await this.getIngredientes();
    this.db = await this.getDb();
    //await this.reloadIdb();

  },

  methods: {
    async onSubmit() {
      await this.addIngredientDB(this.name, this.traduccion);
      await this.getIngredientes();
      //await this.reloadIdb();

    },

    async addToDb(ingredient) {
      console.log(ingredient)
      let add = await this.$axios.post(this.url_server_api + '/admin/addingredient', {
        name: ingredient.name,
        traduccion: ingredient.traduccion,
        energia: ingredient.energia.toString(),
        azucar: ingredient.azucar.toString(),
        grasasSat: ingredient.grasasSat.toString(),
        proteinas: ingredient.proteinas.toString()
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
          message: 'Error to add ingredient in database'
        })
      }
    },

    async addIngredientDB(nameIng, tradIng) {
      let comp = await this.comprovarElementIDXDB(nameIng, tradIng)
      if (!comp.result) {
        await this.comprovaKcal(nameIng, tradIng)
      } else {
        await this.addToDb(comp.result)
      }
    },

    async getIngredientes() {
      let ingredientes = await this.$axios.get(this.url_server_api + '/admin/getAllIngredientes')
      this.ing = ingredientes.data;
    },


    async getDb() {
      return new Promise((resolve, reject) => {
        let request = indexedDB.open(["ingredients"], 1);

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


    /*    async reloadIdb() {
          for (const ing of this.ing) {
            let comp = await this.comprovarElementIDXDB(ing.name, ing.energia)
            if (comp.result) {
              console.log("element a la llista")
            } else {
              await this.comprovaKcal(ing.name, ing.traduccion);
            }
          }
        },*/

    async comprovarElementIDXDB(ingrediente) {
      return new Promise((resolve, reject) => {
        let trans = this.db.transaction(["Llista"], "readwrite");

        let objectStore = trans.objectStore("Llista");
        let myIndex = objectStore.index("Index");
        let elementValidat = myIndex.get(ingrediente);
        trans.oncomplete = e => {
          resolve(elementValidat);
        };
      });
    },

    async comprovaKcal(ing, trad) {
      let kcalAliment = await this.kcal(ing);
      if (kcalAliment && kcalAliment.calories > 0) {
        let ingredient = {
          energia: "",
          azucar: "",
          grasasSat: "",
          proteinas: "",
          name: "",
          traduccion: "",
        };
        ingredient.energia = kcalAliment.calories;
        ingredient.azucar = kcalAliment.totalNutrients.SUGAR.quantity;
        ingredient.grasasSat = kcalAliment.totalNutrients.FASAT.quantity;
        ingredient.proteinas = kcalAliment.totalNutrients.PROCNT.quantity;
        ingredient.name = ing
        ingredient.traduccion = trad

        await this.addAlimentBd(ingredient);
      }
    },

    async addAlimentBd(ingredient) {
      return new Promise((resolve, reject) => {
        let trans = this.db.transaction("Llista", "readwrite");
        trans.oncomplete = async e => {
          await this.addToDb(ingredient)
          resolve();
        };
        let store = trans.objectStore("Llista");
        store.add(ingredient);
        //this.updateIngredient(ingredient.name, ingredient.energia.toString(), ingredient.azucar.toString(), ingredient.grasasSat.toString(), ingredient.proteinas.toString())
      });
    },


    /*
        async updateIngredient(nameIng, enerIng, azuIng, grasIng, proting) {
          let upd = await this.$axios.post(this.url_server_api + '/admin/updateIngredient', {
            name: nameIng,
            energia: enerIng,
            azucar: azuIng,
            grasasSat: grasIng,
            proteinas: proting,
          });
          if (upd.request.status === 202) {
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
              message: 'Update Error'
            })
          }

        },
    */


    async kcal(ing) {
      const appId = "58fd51c1";
      const apyKey = "5731f61303e7d6c4f10ba1518812e0bd";
      const url =
        "https://api.edamam.com/api/nutrition-data?app_id=" +
        appId +
        "&app_key=" +
        apyKey +
        "&ingr=one%20" +
        ing +
        "";
      let calories = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json"
        }
      });
      let caloriesJson = await calories.json();
      console.log(caloriesJson)
      return caloriesJson;
    },


    onReset() {
      this.name = null
      this.traduccion = null
    },
  }
}
</script>

