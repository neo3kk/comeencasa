<template>
  <div class="col-md-6 col-sm-12 col-xs-12">
    <div>
      <div class="row justify-around col">
        <div class="col-6">
          <q-img
            :src="plato.imageUrl"
            spinner-color="white"
            style="height: 140px; max-width: 150px"
          />
        </div>
        <div class="q-gutter-y-md col-6 justify-around column">
          <div class="row col items-center justify-around">
            <h5>Nombre del plato: {{plato.nombre}}</h5>
            <h5>Nombre traducido: {{plato.traduccion}}</h5>
          </div>
          <div class="row col items-center justify-around">
            <h5>Tipo de plato:    {{plato.tipo_de_plato}}</h5>
            <h5> Descripcio : {{plato.description}}</h5>
          </div>

          <div class="row col items-center justify-around q-pt-lg">
            <h5>Precio: {{plato.precio}}€</h5>
          </div>

        </div>
      </div>
    </div>
    <div id="chart" style="width: 500px">
      <apexchart type="donut" :options="chartOptions" :series="series"></apexchart>
    </div>
  </div>
</template>

<script>
  import {SETTINGS} from "src/settings";
  import VueApexCharts from 'vue-apexcharts'

  export default {
    props: ['id'],
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
                  total: {
                    showAlways: true,
                    show: true
                  }
                }
              }
            }
          },
          labels: ["Azucar", "Energia", "Grasas", "Proteinas"],
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
      console.log(this.plato)
      this.series = [this.plato.azucar, this.plato.energia, this.plato.grasas, this.plato.proteinas]


    },

  }
</script>

<style scoped>

</style>
