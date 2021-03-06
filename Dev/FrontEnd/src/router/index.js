import Vue from 'vue'
import VueRouter from 'vue-router'

import routes from './routes'
import {SETTINGS} from '../settings.js'
import axios from 'axios';

Vue.use(VueRouter)

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default function (/* { store, ssrContext } */) {
  const Router = new VueRouter({
    scrollBehavior: () => ({x: 0, y: 0}),
    routes,

    // Leave these as they are and change in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    mode: process.env.VUE_ROUTER_MODE,
    base: process.env.VUE_ROUTER_BASE
  })

  Router.beforeEach((to, from, next) => {
    if (to.query.accessToken) {
      localStorage.setItem("tokenLogin", "Bearer " + to.query.accessToken);
    }
    if (to.meta.requiresAuth) {
      if (localStorage.getItem("tokenLogin")) {
        axios.get(SETTINGS.URL_SERVER_API + `/validateToken`).then(function (res) {
          if (res.request.responseText === "InvalidToken") {
            next({path: "expired"});
          } else {
            next();
          }
        })
      } else {
        next({name: "login"})
      }
    } else {
      next();
    }
  });

  return Router
}
