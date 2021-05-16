import Vue from 'vue'
import axios from 'axios'
import {SETTINGS} from '../settings.js'

Vue.prototype.$axios = axios;

export default async ({Vue, router}) => {

  axios.interceptors.request.use(config => {


      config.withCredentials = true;
      config.headers['Authorization'] = localStorage.getItem("tokenLogin");
    return config;
  }, function (error) {
    return Promise.reject(error);
  });

  axios.interceptors.response.use(function (response) {
    if (response.data.tokenLogin) {
      localStorage.setItem("tokenLogin", "Bearer " + response.data.tokenLogin);
      if (router.apps[0]._route.path == "/login") {
        router.push("/");
      }
    }

    return response;
  }, async function (error) {

    const originalRequest = error.config;

    if (error.response.status == 403) {

      router.push("/login");

      if (response.status == 200) {
        localStorage.setItem("tokenLogin", "Bearer " + response.data.tokenLogin);
        return axios(originalRequest).catch(err => {});
      }
    }

    if (error.response.status == 401) {
      router.push("/login")
    }

    return Promise.reject(error);
  });
}
