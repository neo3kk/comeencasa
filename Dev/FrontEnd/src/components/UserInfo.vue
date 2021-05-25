<template>
  <div class="q-pa-md">
    <q-btn round>
      <q-avatar size="42px">
        <q-img :src="image" alt=""/>
      </q-avatar>
      <q-menu>
        <div class="row no-wrap q-pa-md">
          <div class="column items-center">
            <q-avatar size="72px">
              <q-img :src="image" alt=""/>
            </q-avatar>

            <div class="text-subtitle1 q-mt-md q-mb-xs">{{ user }}</div>

            <q-btn
              color="primary"
              label="Logout"
              push
              size="sm"
              v-close-popup
              @click="logout"
            />
            <div class="row no-wrap q-pa-md">
              <div class="q-pa-md q-gutter-sm column">
                <q-btn round color="primary" icon="menu_book" @click="$router.push('/profile/pedidos')">
                  <q-tooltip content-class="bg-accent">Mis pedidos</q-tooltip>
                </q-btn>
                <q-btn round color="primary" icon="shopping_cart" @click="$router.push('/profile/carrito')">
                  <q-tooltip content-class="bg-accent">Mi carrito</q-tooltip>
                </q-btn>
                <q-btn round color="deep-orange" icon="account_circle" @click="$router.push('/profile')">
                  <q-tooltip content-class="bg-accent">Mi casa</q-tooltip>
                </q-btn>
                <q-btn v-show="adminShow" round color="negative" icon="account_circle" @click="$router.push('/admin')">
                  <q-tooltip content-class="bg-accent">Admin</q-tooltip>
                </q-btn>

              </div>
            </div>
          </div>
        </div>
      </q-menu>
    </q-btn>
  </div>
</template>
<script>
import {SETTINGS} from "src/settings";

export default {
  data() {
    return {
      image: 'https://placeimg.com/500/300/nature',
      user: "",
      adminShow:false,
      url_server_api: SETTINGS.URL_SERVER_API
    }
  },
  async created() {
    if(localStorage.getItem("user")){
      this.user = localStorage.getItem("user")
      if(this.user==="admin@gmail.com"){
        this.adminShow = true;
      }
/*          let sendRegister = await this.$axios.post(this.url_server_api + '/getImage', {
      user: this.user
    }).then(response => {
      console.log(response)
      var Base64 = {
        _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", encode: function (e) {
          var t = "";
          var n, r, i, s, o, u, a;
          var f = 0;
          e = Base64._utf8_encode(e);
          while (f < e.length) {
            n = e.charCodeAt(f++);
            r = e.charCodeAt(f++);
            i = e.charCodeAt(f++);
            s = n >> 2;
            o = (n & 3) << 4 | r >> 4;
            u = (r & 15) << 2 | i >> 6;
            a = i & 63;
            if (isNaN(r)) {
              u = a = 64
            } else if (isNaN(i)) {
              a = 64
            }
            t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(u) + this._keyStr.charAt(a)
          }
          return t
        }, decode: function (e) {
          var t = "";
          var n, r, i;
          var s, o, u, a;
          var f = 0;
          e = e.replace(/[^A-Za-z0-9\+\/\=]/g, "");
          while (f < e.length) {
            s = this._keyStr.indexOf(e.charAt(f++));
            o = this._keyStr.indexOf(e.charAt(f++));
            u = this._keyStr.indexOf(e.charAt(f++));
            a = this._keyStr.indexOf(e.charAt(f++));
            n = s << 2 | o >> 4;
            r = (o & 15) << 4 | u >> 2;
            i = (u & 3) << 6 | a;
            t = t + String.fromCharCode(n);
            if (u != 64) {
              t = t + String.fromCharCode(r)
            }
            if (a != 64) {
              t = t + String.fromCharCode(i)
            }
          }
          t = Base64._utf8_decode(t);
          return t
        }, _utf8_encode: function (e) {
          e = e.replace(/\r\n/g, "\n");
          var t = "";
          for (var n = 0; n < e.length; n++) {
            var r = e.charCodeAt(n);
            if (r < 128) {
              t += String.fromCharCode(r)
            } else if (r > 127 && r < 2048) {
              t += String.fromCharCode(r >> 6 | 192);
              t += String.fromCharCode(r & 63 | 128)
            } else {
              t += String.fromCharCode(r >> 12 | 224);
              t += String.fromCharCode(r >> 6 & 63 | 128);
              t += String.fromCharCode(r & 63 | 128)
            }
          }
          return t
        }, _utf8_decode: function (e) {
          var t = "";
          var n = 0;
          let c1;
          let c2;
          let c3;
          let r = c1 = c2 = 0;
          while (n < e.length) {
            r = e.charCodeAt(n);
            if (r < 128) {
              t += String.fromCharCode(r);
              n++
            } else if (r > 191 && r < 224) {
              c2 = e.charCodeAt(n + 1);
              t += String.fromCharCode((r & 31) << 6 | c2 & 63);
              n += 2
            } else {
              c2 = e.charCodeAt(n + 1);
              c3 = e.charCodeAt(n + 2);
              t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
              n += 3
            }
          }
          return t
        }
      }
      this.image = Base64.encode(response.data);
    })*/
    }


    console.log("Ara ha estat creat")
  },
  methods: {
    logout() {
      localStorage.clear()
      this.$router.push("/login");
    }
  }
}
</script>

