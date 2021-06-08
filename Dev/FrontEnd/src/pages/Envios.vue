<template>
  <q-page padding class="flex">
    <q-card style="flex:1">
      <l-map :zoom="zoom" :center="center">
        <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
        <l-marker :lat-lng="center"> >
        </l-marker>
        <l-circle
          :lat-lng="extraDelivery.center"
          :radius="extraDelivery.radius"
          :color="extraDelivery.color"
        >
          <l-popup :content="extraDelivery.name" />
        </l-circle>
        <l-circle
          :lat-lng="delivery.center"
          :radius="delivery.radius"
          :color="delivery.color"
        >
          <l-popup :content="delivery.name" />
        </l-circle>
        <l-circle
          :lat-lng="freedelivery.center"
          :radius="freedelivery.radius"
          :color="freedelivery.color"
        >
          <l-popup :content="freedelivery.name" />
        </l-circle>


      </l-map>
    </q-card>
  </q-page>
</template>

<script>
import {LMap, LTileLayer, LMarker, LIcon, LCircle, LPopup} from 'vue2-leaflet'
import L from 'leaflet'
import {Icon} from 'leaflet';
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
      zoom: 12,
      center: L.latLng(47.413220, -1.219482),
      url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
      attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      freedelivery: {
        center: [47.413220, -1.0482],
        radius: 1000,
        color: 'yellow',
        name: "Free delivery"
      },
      delivery: {
        center: [47.413220, -1.0482],
        radius: 3000,
        color: 'green',
        name: "Delivery 10%"
      },
      extraDelivery: {
        center: [47.413220, -1.0482],
        radius: 7000,
        color: 'red',
        name: "Delivery 15%"
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
      this.freedelivery.center = [this.lat, this.long]
      this.delivery.center = [this.lat, this.long]
      this.extraDelivery.center = [this.lat, this.long]
    },

    error() {
      console.log('Unable to retrieve your location');
    },
  }
}
</script>
