<template>
  <div class="q-pa-md">
    <q-list class="">
      <q-item class="flex wrap column justify-center">
        <q-item class="justify-center">
          <q-icon v-for="al in alergenos" :key="al.id"
                  size="6em"
                  :name="'img:alergenos/'+al.name+'.png'"
                  :id="al.id"
                  :title="al.name"
                  draggable="true"
                  dropable="false"
                  @dragstart="onDragStart"
                  @click="clearAlergeno"
          />

        </q-item>
        <q-item
          id="alergenosSelected"
          @dragenter="onDragEnter"
          @dragleave="onDragLeave"
          @dragover="onDragOver"
          @drop="onDrop"
          class="drop-target justify-center"
        ></q-item>
        <q-btn color="red" @click="updateAlergenos()">Actualizar</q-btn>
      </q-item>
    </q-list>
  </div>

</template>
<style scoped lang="sass">
.icons-center
  align-items: baseline

.tamany-drop
  width: 6rem
  heigth: 6rem

.drop-target
  background-color: #d9d9d9
  height: 7em

.drag-enter
  outline-style: dashed

</style>

<script>

import {SETTINGS} from "src/settings";

export default {
  data() {
    return {
      url_server_api: SETTINGS.URL_SERVER_API,
      alergenos: [],
      alergenosSelected: []
    };
  },
  async created() {
    await this.getAlergenos();
    await this.appendAlergenosUsuario();
  },

  methods: {
    async updateAlergenos() {
      let updt = await this.$axios.post(this.url_server_api + '/profile/updateAlergenos', {
        alergenos: this.alergenosSelected
      });
      if (updt.request.status === 202) {
        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Alergenos actualizados correctamente'
        })
      } else {
        this.$q.notify({
          color: 'red-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'Error al actualizar los alergenos'
        })
      }
    },

    async getAlergenos() {
      let alergenosFetch = await this.$axios.get(this.url_server_api + '/profile/getAllAlergenos')
      let alergenosUser = await this.$axios.get(this.url_server_api + '/profile/gellAllFromUser')
      this.alergenos = alergenosFetch.data
      this.alergenosSelected = alergenosUser.data

    },

    async appendAlergenosUsuario() {
      let append = document.querySelector("#alergenosSelected");
      this.alergenosSelected.forEach(alergeno => {
        const elemento = document.getElementById(alergeno.id);
        elemento.dropable = true;
        append.appendChild(elemento);
      });
    },
    clearAlergeno(ev) {
      if (ev.target.dropable === true) {
        let alergenosnew = this.alergenosSelected.filter(function (value, index, arr) {
          return parseInt(value.id) !== parseInt(ev.target.id)
        })
        console.log(alergenosnew)
        ev.target.draggable = true;

        if (document.querySelector("#alergenosSelected").firstChild) {
          let append = document.querySelector("#alergenosSelected");
          append.parentNode.firstChild.appendChild(ev.target)

        }
        this.alergenosSelected = alergenosnew;
        this.$emit("alergens", this.alergenosSelected);
      }



    },
    // store the id of the draggable element
    onDragStart(e) {
      e.dataTransfer.setData("id", e.target.id);
      e.dataTransfer.dropEffect = "move";
    },

    onDragEnter(e) {
      // don't drop on other draggables
      if (e.target.draggable !== true) {
        e.target.classList.add("drag-enter");
      }

    },

    onDragLeave(e) {
      console.log("dragLeave")
      e.target.classList.remove("drag-enter");
    },

    onDragOver(e) {
      e.preventDefault();
    },

    onDrop(e) {
      e.preventDefault();
      if (e.target.draggable === true) {
        return;
      }
      const draggedId = e.dataTransfer.getData("id");
      const draggedEl = document.getElementById(draggedId);
      draggedEl.dropable = true;
      e.target.classList.remove("drag-enter");

      // make the exchange

      e.target.appendChild(draggedEl);
      let alergeno = {
        id: "",
        name: ""
      };
      alergeno.id = draggedEl.id
      alergeno.name = draggedEl.title
      this.alergenosSelected.push(alergeno);
      this.$emit("alergens", this.alergenosSelected);
    }
  }
};
</script>
