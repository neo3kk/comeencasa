<template>
  <div class="fullscreen bg-red text-white text-center q-pa-md flex flex-center">
    <div>
      <div style="font-size: 10vh">
        Acceso restringido
      </div>

      <div class="text-h2" style="opacity:.4">
        No tienes permiso para acceder a esta sección
      </div>

      <q-btn
        class="q-mt-xl"
        color="white"
        text-color="red"
        unelevated
        to="/"
        label="Volver al inicio"
        no-caps
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Unauthorized'
}
</script>
