<template>

</template>

<script>
export default {
  props: ['id'],
  name: "Plato.vue",
  data () {
    return {
      id: this.id
    }
  },
  created() {
    this.$router.currentRoute.params(id)
    console.log(this.id)
  },

}
</script>

<style scoped>

</style>
