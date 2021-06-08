const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {path: '', component: () => import('pages/Index.vue'), meta: {requiresAuth: false}},
      {path: 'platos', component: () => import('pages/Platos.vue'), meta: {requiresAuth: false}},
      {path: 'envios', component: () => import('pages/Envios.vue'), meta: {requiresAuth: false}},
      {path: 'alergenos', component: () => import('pages/Alergenos.vue'), meta: {requiresAuth: false}},
      {path: 'auth/oauth2callback/', component: () => import('pages/Status/Callback.vue'), meta: {requiresAuth: false}},
      {path: 'unauthorized', component: () => import('pages/Status/Unauthorized.vue'), meta: {requiresAuth: false}},
      {path: 'expired', component: () => import('pages/Status/Expired.vue'), meta: {requiresAuth: false}},
      {path: 'plato/:id', component: () => import('pages/Plato.vue'), meta: {requiresAuth: true}},

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
      {path: '', component: () => import('pages/Profile.vue'), meta: {requiresAuth: true}},
      {path: 'pedidos', component: () => import('pages/PedidosPage.vue'), meta: {requiresAuth: true}},
      {path: 'nuevopedido', component: () => import('pages/NuevoPedido.vue'), meta: {requiresAuth: true}},
      {path: 'carrito', component: () => import('pages/Carrito.vue'), meta: {requiresAuth: true}},
      {path: 'pedidomenu', component: () => import('pages/PedidoMenu.vue'), meta: {requiresAuth: true}},
      {path: 'pedidomenu/:id', component: () => import('pages/PedidoMenu.vue'), meta: {requiresAuth: true}},
      {path: 'pago/:id', component: () => import('pages/CheckOut.vue'), meta: {requiresAuth: true}},

    ]
  },
  {
    path: '/admin',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {path: '', component: () => import('pages/Admin/Admin.vue'), meta: {requiresAuth: true}},
      {path: 'nuevoingrediente', component: () => import('pages/Admin/AdminCrearIngrediente.vue'), meta: {requiresAuth: true}},
      {path: 'nuevoplato', component: () => import('pages/CrearPlato.vue'), meta: {requiresAuth: true}},
      {path: 'editplato/:id', component: () => import('pages/CrearPlato.vue'), meta: {requiresAuth: true}},
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Status/Error404.vue')
  }
]

export default routes
