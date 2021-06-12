<template>
  <div class="q-pa-md">
    <div class="q-gutter-md">
      <q-carousel
        v-model="slide"
        transition-prev="scale"
        transition-next="scale"
        swipeable
        animated
        control-color="white"
        navigation
        padding
        arrows
        height="300px"
        class="bg-secondary text-white shadow-1 rounded-borders"
      >
        <q-carousel-slide name="style" class="column no-wrap flex-center">
          <q-icon name="favorite" size="150px" color="red" />
          <div class="q-mt-md text-center">
            {{ saludable }}
          </div>
        </q-carousel-slide>
        <q-carousel-slide name="agriculture" class="column no-wrap flex-center">
          <q-icon name="agriculture" size="150px" color="yellow"/>
          <div class="q-mt-md text-center">
            {{ sostenible }}
          </div>
        </q-carousel-slide>
        <q-carousel-slide name="savings" class="column no-wrap flex-center">
          <q-icon name="savings" size="150px" color="pink"/>
          <div class="q-mt-md text-center">
            {{ ahorro }}
          </div>
        </q-carousel-slide>
      </q-carousel>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      slide: 'style',
      saludable: 'Una dieta equilibrada es imprescindible para una vida saludable',
      sostenible: 'Todos nuestros platos estan elaborados con ingredientes de primera calidad, traidos directamente del huerto',
      ahorro: 'Nunca mas volverás a tirar comida',


    }
  }
}
</script>
