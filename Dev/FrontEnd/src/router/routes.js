const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {path: '', component: () => import('pages/Index.vue'), meta: {requiresAuth: false}},
      {path: 'platos', component: () => import('pages/Platos.vue'), meta: {requiresAuth: false}},
      {path: 'tarifas', component: () => import('pages/Tarifas.vue'), meta: {requiresAuth: false}},
      {path: 'envios', component: () => import('pages/Envios.vue'), meta: {requiresAuth: false}},
      {path: 'alergenos', component: () => import('pages/Alergenos.vue'), meta: {requiresAuth: false}},
      {path: 'auth/oauth2callback/', component: () => import('pages/Callback.vue'), meta: {requiresAuth: false}}
    ]
  },
  {
    path: '/',
    component: () => import('layouts/LoginLayout.vue'),
    children: [
      {path: 'login', name: 'login', component: () => import('pages/LoginPage.vue'), meta: {requiresAuth: false}},
    ]
  },
  {
    path: '/profile',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {path: '', component: () => import('pages/Index.vue'), meta: {requiresAuth: true}},
      {path: 'pedidos', component: () => import('pages/PedidosPage.vue'), meta: {requiresAuth: true}},
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
