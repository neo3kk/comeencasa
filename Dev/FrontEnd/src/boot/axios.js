import Vue from 'vue'
import axios from 'axios'
import {SETTINGS} from '../settings.js'

Vue.prototype.$axios = axios;

export default async ({Vue, router}) => {

  axios.interceptors.request.use(config => {

    if (config.url !== "http://server247.cfgs.esliceu.net/bloggeri18n/blogger.php") {
      config.withCredentials = true;
      config.headers['Authorization'] = localStorage.getItem("tokenLogin");
    }
    return config;
  }, function (error) {

    return Promise.reject(error);
  });

  axios.interceptors.response.use(function (response) {

    if (response.data.accessToken && response.data.refreshToken) {
      localStorage.setItem("tokenLogin", "Bearer " + response.data.accessToken);
      localStorage.setItem("refreshToken", "Bearer " + response.data.refreshToken);
      if (router.apps[0]._route.path == "/login") {
        router.push("/admin");
      }
    }

    return response;
  }, async function (error) {

    const originalRequest = error.config;

    if (error.response.status == 403) {

      let refreshToken = localStorage.getItem("refreshToken");
      let response = await axios.post(SETTINGS.URL_SERVER_OAUTH+'/oauth/newtoken', {
        refreshToken: refreshToken
      });

      if (response.status == 200) {
        localStorage.setItem("tokenLogin", "Bearer " + response.data.accessToken);
        localStorage.setItem("refreshToken", "Bearer " + response.data.refreshToken);

        return axios(originalRequest).catch(err => {});
      }
    }

    if (error.response.status == 401) {
      router.push("/login")
    }

    return Promise.reject(error);
  });
}
