<template>
  <q-page padding class="flex">
    <q-card style="flex:1">
      <l-map :zoom="zoom" :center="center">
        <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
        <l-circle
          :lat-lng="delivery.center"
          :radius="delivery.radius"
          :color="delivery.color"
          :icon="icon"
        >
          <l-popup :content="delivery.name"/>
        </l-circle>
        <l-marker :lat-lng="center">
          <l-popup content="Tu posicion"/>
        </l-marker>

        <l-marker :lat-lng="delivery.center">
          <l-popup content="Comeencasa"/>
        </l-marker>

      </l-map>
    </q-card>
  </q-page>
</template>

<script>
import {LMap, LTileLayer, LMarker, LIcon, LCircle, LPopup} from 'vue2-leaflet'
import L from 'leaflet'
import {Icon, icon} from 'leaflet';
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
    LIcon,
    LCircle,
    LPopup
  },
  data() {
    return {
      lat: "",
      long: "",
      icon: icon({
        iconUrl: "src/assets/logomin.png",
        iconSize: [32, 37],
        iconAnchor: [16, 37]
      }),
      deliveryPrice: "deliveryPrice",
      zoom: 12,
      center: L.latLng(47.413220, -1.219482),
      url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      delivery: {
        center: [39.6012567777098, 2.6893242690042873],
        radius: 10000,
        color: 'green',
        name: "Envio disponible"
      }

    }
  },
  created() {
    this.locate();

  },
  computed: {},
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
      this.center = L.latLng(this.lat, this.long)
    },

    error() {
      console.log('Unable to retrieve your location');
    },
  }
}
</script>
