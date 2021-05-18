<template>
  <q-page>
    <alergenos-component></alergenos-component>
  </q-page>


</template>

<script>

import Carrousel from 'src/components/Carrousel.vue'
import Suggestions from "components/Suggestions";
import AlergenosComponent from "components/AlergenosComponent";

export default {
  name: 'Alergenos',
  components: {AlergenosComponent},
}
</script>
