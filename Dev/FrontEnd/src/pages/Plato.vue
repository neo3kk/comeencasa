<template>
  <q-page class="q-pa-md">
    <q-card class="my-card justify-center flex items-center">
      <img :src="plato.imageUrl" class="comida" spinner-color="white"
      style="max-width: 25%"/>
      <q-card-section>
        <div class="text-h4">Nombre: {{ plato.nombre }}</div>
        <div class="text-subtitle1">Descripcion: {{ plato.description }}</div>
        <div class="text-subtitle1">Traduccion: {{ plato.traduccion }}</div>
        <div class="text-subtitle1">Categoria: {{ plato.tipo_de_plato }}</div>
        <div class="text-subtitle1">{{ plato.precio }}€</div>
      </q-card-section>
      <div id="chart" style="width: 25%">
        <apexchart type="donut" :options="chartOptions" :series="series"></apexchart>
      </div>
    </q-card>
  </q-page>
</template>

<script>
import {SETTINGS} from "src/settings";
import VueApexCharts from 'vue-apexcharts'

export default {
  name: "Plato.vue",
  components: {
    apexchart: VueApexCharts,
  },
  data() {
    return {
      plato: '',
      id: '',
      url_server_api: SETTINGS.URL_SERVER_API,
      series: [44, 55, 41, 17],
      chartOptions: {
        chart: {
          type: 'donut',
        },
        plotOptions: {
          pie: {
            donut: {
              labels: {
                show: true,
                name: {
                  show: true
                },
                value: {
                  show: true
                }
              }
            }
          }
        },
        labels: ["Azucar g", "Energia kcal", "Grasas g", "Proteinas g"],
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 100
            },
            legend: {
              position: 'bottom'
            }
          }
        }]
      },
    }
  },
  async created() {
    this.id = this.$router.currentRoute.params.id
    let platoFetch = await this.$axios.post(this.url_server_api + '/getPlatoById', {
      idplato: this.id
    });
    this.plato = platoFetch.data;
    this.series = [this.plato.azucar, this.plato.energia, this.plato.grasas, this.plato.proteinas]


  },

}
</script>

<style scoped>

</style>
