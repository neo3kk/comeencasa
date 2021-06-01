<template>
  <q-page>
    <suggestions></suggestions>
    <Carrousel></Carrousel>
    <div>

    </div>
  </q-page>


</template>

<script>

import Carrousel from 'src/components/Carrousel.vue'
import Suggestions from "components/Suggestions";

export default {
  name: 'PageIndex',
  components: {Suggestions, Carrousel},

}
</script>
