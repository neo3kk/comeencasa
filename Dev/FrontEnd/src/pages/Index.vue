<template>
  <q-page>
    <suggestions></suggestions>
    <Carrousel></Carrousel>
  </q-page>


</template>

<script>

import Carrousel from 'src/components/Carrousel.vue'
import Suggestions from "components/suggestions";

export default {
  name: 'PageIndex',
  components: {Suggestions, Carrousel}
}
</script>
