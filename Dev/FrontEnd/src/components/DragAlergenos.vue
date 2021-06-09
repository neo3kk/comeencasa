<template>
  <div class="q-pa-md">
    <q-list class="">
      <q-item class="flex wrap column justify-center">
        <q-item class="justify-center">
          <q-icon
            size="6em"
            name="img:alergenos/altramuces.png"
            id="altramuces"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/apio.png"
            id="apio"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/cacahuetes.png"
            id="cacahuetes"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/cereales.png"
            id="cereales"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/crustaceo.png"
            id="crustaceo"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/frutosdecascara.png"
            id="frutosdecascara"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/huevos.png"
            id="huevos"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/lacteos.png"
            id="lacteos"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/moluscos.png"
            id="moluscos"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/mostaza.png"
            id="mostaza"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/pescado.png"
            id="pescado"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/sesamo.png"
            id="sesamo"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/soja.png"
            id="soja"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
          <q-icon
            size="6em"
            name="img:alergenos/sulfitos.png"
            id="sulfitos"
            draggable="true"
            dropable="false"
            @dragstart="onDragStart"
            @click="clearAlergeno"
          />
        </q-item>
        <q-item
          id="alergenos"
          @dragenter="onDragEnter"
          @dragleave="onDragLeave"
          @dragover="onDragOver"
          @drop="onDrop"
          class="drop-target justify-center"
        ></q-item>
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

export default {
  data() {
    return {
      alergenos: []
    };
  },

  methods: {
    clearAlergeno(ev) {
      if (ev.target.dropable === true) {
        let alergenosnew = this.alergenos.filter(function (value, index, arr) {
          return value !== ev.target.id
        })
        console.log(ev.target)
        ev.target.draggable=true;

        if (document.querySelector("#alergenos").firstChild){
          let append = document.querySelector("#alergenos");
         append.parentNode.firstChild.appendChild(ev.target)
        }
        this.alergenos = alergenosnew;

      }
      this.$emit("alergens", this.alergenos);


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
      draggedEl.dropable=true;
      e.target.classList.remove("drag-enter");

      // make the exchange

      e.target.appendChild(draggedEl);
      this.alergenos.push(draggedEl.id);
      this.$emit("alergens", this.alergenos);
    }
  }
};
</script>
