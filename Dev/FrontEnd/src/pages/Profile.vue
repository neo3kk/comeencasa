<template>
  <q-page>
    hola
    {{ this.user }}
    <div>

    </div>
  </q-page>


</template>

<script>

export default {
  name: 'ProfilePage',
  props:{
    user: {type: String}
  },
  data(){
    return {

    }
  }

}
</script>
