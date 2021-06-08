<template>
  <div class="col-md-6 col-sm-12 col-xs-12">
    <div id="chart">
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
  data () {
    return {
      id: '',
      url_server_api: SETTINGS.URL_SERVER_API,
      series: [44, 55, 41, 17, 15],
      chartOptions: {
        chart: {
          type: 'donut',
        },
        responsive: [{
          breakpoint: 480,
          options: {
            chart: {
              width: 200
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

  },

}
</script>

<style scoped>

</style>
