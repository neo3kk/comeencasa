<template>
  <q-page padding class="flex">
    <q-card style="flex:1">
      <l-map :zoom="zoom" :center="center">
        <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
        <l-marker :lat-lng="center"> >

        </l-marker>
      </l-map>
    </q-card>
  </q-page>
</template>

<script>
import {LMap, LTileLayer, LMarker, LIcon} from 'vue2-leaflet'
import L from 'leaflet'
import { Icon } from 'leaflet';
import 'leaflet/dist/leaflet.css'

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

export default {
  name: 'Map',
  components: {
    LMap,
    LTileLayer,
    LMarker,
    LIcon
  },
  data() {
    return {
      lat:"",
      long:"",
      zoom: 17,
      center: L.latLng(47.413220, -1.219482),
      url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    }
  },
  created() {
    this.locate();
  },
  computed:{

  },
  methods: {
    locate() {
      if (!navigator.geolocation) {
        console.log('Geolocation is not supported by your browser');
      } else {
        console.log("location on")
        navigator.geolocation.getCurrentPosition(this.success, this.error);
      }
    },
    success(position) {
      this.lat = position.coords.latitude;
      this.long = position.coords.longitude;
      this.center = L.latLng(this.lat,this.long)
    },

    error() {
      console.log('Unable to retrieve your location');
    }
  }
}
</script>
