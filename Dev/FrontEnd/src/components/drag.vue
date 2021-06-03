<template>
  <div class="q-pa-md">
    <q-list bordered padding class="q-mt-lg">
      <q-item class="icons-center">
        <q-item>
          <q-icon
            size="2em"
            color="negative"
            name="filter_1"
            id="poc"
            draggable="true"
            @dragstart="onDragStart"
          />
          <q-icon
            size="2em"
            color="negative"
            name="filter_2"
            id="lleuger"
            draggable="true"
            @dragstart="onDragStart"
          />
          <q-icon
            size="2em"
            color="negative"
            name="filter_3"
            id="moderat"
            draggable="true"
            @dragstart="onDragStart"
          />
          <q-icon
            size="2em"
            color="negative"
            name="filter_4"
            id="fort"
            draggable="true"
            @dragstart="onDragStart"
          />
          <q-icon
            size="2em"
            color="negative"
            name="filter_5"
            id="profesional"
            draggable="true"
            @dragstart="onDragStart"
          />
        </q-item>
        <q-item
          id="nivell"
          @dragenter="onDragEnter"
          @dragleave="onDragLeave"
          @dragover="onDragOver"
          @drop="onDrop"
          class="drop-target rounded-borders tamany-drop"
        ></q-item>
      </q-item>
    </q-list>
  </div>
</template>
<style scoped lang="sass">
.icons-center
  align-items: baseline

.tamany-drop
  width: 50px
  heigth: 50px

.drop-target
  background-color: gainsboro

.drag-enter
  outline-style: dashed

</style>

<script>
export default {
  data() {
    return {
      nivell: ""
    };
  },

  methods: {
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
      if (document.querySelector("#nivell").firstChild) {
        let append = document.querySelector("#nivell");
        e.target.parentNode.insertBefore(
          append.firstChild,
          e.target.parentNode.childNodes[0]
        );
      }
    },

    onDragLeave(e) {
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

      if (draggedEl.parentNode === e.target) {
        e.target.classList.remove("drag-enter");
        return;
      }

      // make the exchange
      draggedEl.parentNode.removeChild(draggedEl);
      e.target.appendChild(draggedEl);
      e.target.classList.remove("drag-enter");
      this.nivell = draggedEl.id;
      if (this.nivell == "poc") {
        this.nivell = 1.2;
      } else if (this.nivell == "lleuger") {
        this.nivell = 1.375;
      } else if (this.nivell == "moderat") {
        this.nivell = 1.55;
      } else if (this.nivell == "fort") {
        this.nivell = 1.725;
      } else if (this.nivell == "profesional") {
        this.nivell = 1.9;
      }
      this.$emit("level", this.nivell);
    }
  }
};
</script>
