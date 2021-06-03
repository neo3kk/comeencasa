<template>
  <q-page>
    hola
    {{ this.user.name }}
    <div>

    </div>
  </q-page>


</template>

<script>

export default {
  name: 'ProfilePage',
  props:{
    user: {type: Object}
  },
  data(){
    return {

    }
  }

}
</script>
