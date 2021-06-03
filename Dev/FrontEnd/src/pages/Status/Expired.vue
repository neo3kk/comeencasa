<template>
  <div class="fullscreen bg-yellow text-white text-center q-pa-md flex flex-center">
    <div>
      <div style="font-size: 10vh">
        Ha caducado la sesión
      </div>

      <div class="text-h2" style="opacity:.4">
        Tu sesión ha caducado, vuelve a iniciar sesión
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
