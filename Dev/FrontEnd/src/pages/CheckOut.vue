<template>
  <div id="payment-form">

    <div class="q-mt-md q-mb-md text-negative" v-if="submissionError">
      <div id="card-errors" role="alert">{{ submissionError }}</div>
    </div>

    <q-field label="Card Number"
             stack-label
             class="q-mb-md"
             :error-message="errors['cardNumber']"
             :error="!isCardNumberValid">

      <template v-slot:control>
        <div class="self-center full-width no-outline">
          <div id="cardNumber" ref="cardNumber"></div>
        </div>
      </template>

    </q-field>

    <div class="row q-col-gutter-lg">
      <div class="col-6">
        <q-field label="Card Expiry"
                 stack-label
                 class="q-mb-md"
                 :error-message="errors['cardExpiry']"
                 :error="!isCardExpiryValid">

          <template v-slot:control>
            <div class="self-center full-width no-outline">
              <div id="cardExpiry" ref="cardExpiry"></div>
            </div>
          </template>

        </q-field>
      </div>
      <div class="col-6">
        <q-field label="Card CVC"
                 stack-label
                 class="q-mb-md"
                 :error-message="errors['cardCvc']"
                 :error="!isCardCvcValid">

          <template v-slot:control>
            <div class="self-center full-width no-outline">
              <div id="cardCvc" ref="cardCvc"></div>
            </div>
          </template>

        </q-field>
      </div>
    </div>

    <q-btn
      unelevated
      color="accent"
      label="Make Payment"
      :loading="loading"
      @click="seguro = true"/>
    <q-dialog v-model="seguro" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">Estas seguro que tu direccion de envio sea correcta??</div>
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Continuar con el pago" @click="submitForm"/>
          <q-btn flat label="Mejor voy a comprobarlo" color="red" v-close-popup  @click="$router.push('/profile')"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-dialog v-model="del" persistent>
      <q-card style="min-width: 350px">
        <q-card-section>
          <div class="text-h6">No has definido los datos de envio. Por favor, asegurate de guardarlos!</div>
        </q-card-section>

        <q-card-actions align="right" class="text-primary">
          <q-btn flat label="Ir a tu perfil" color="red" v-close-popup  @click="$router.push('/profile')"/>
        </q-card-actions>
      </q-card>
    </q-dialog>

  </div>
</template>

<script>
  // https://github.com/stripe/stripe-js
  import {loadStripe} from '@stripe/stripe-js/pure';
  import {SETTINGS} from "src/settings";

  export default {
    props: {
      data: {
        type: Object, required: false, default: () => {
        }
      },
      props: ['id'],
    },
    data() {
      return {
        seguro: false,
        del: false,
        id: '',
        precio_final: '',
        loading: false,
        stripe: null,
        elements: null,
        card: {
          cardNumber: null,
          cardExpiry: null,
          cardCvc: null
        },
        errors: {
          cardNumber: '',
          cardExpiry: '',
          cardCvc: ''
        },
        url_server_api: SETTINGS.URL_SERVER_API,
        submissionError: null
      }
    },
    computed: {
      isCardNumberValid() {
        return this.isValid('cardNumber');
      },
      isCardExpiryValid() {
        return this.isValid('cardExpiry');
      },
      isCardCvcValid() {
        return this.isValid('cardCvc');
      }
    },
    methods: {
      showNotification(content, icon, color) {
        this.$q.notify({
          message: content,
          color: color,
          icon: icon,
          actions: [
            {
              label: 'OK', color: 'white', handler: () => {
                this.tab = "nuevoPedido"
              }
            }
          ]
        })
      },
      async myfunc(elements) {
        let crearPlato = await this.$axios.post(this.url_server_api + '/charge', {
          amount: elements.amount,
          description: elements.description,
          source: elements.source
        })
        if (crearPlato.data.status === "succeeded") {
          this.showNotification("Se ha realizado el pago de manera correcta", "check_circle_outline", "positive")
          var pagado = await this.$axios.post(this.url_server_api + '/setPedidoPagado', {
            pedidoid: this.id
          });
        } else {
          this.showNotification("Ha habido un error con el pago, pruebe mas tarde", "error", "negative")
        }
      },
      async submitForm(e) {
        e.preventDefault();
        try {
          this.loading = true;
          this.submissionError = null;
          const {token, error} = await this.stripe.createToken(this.card['cardNumber']);
          console.log(error);
          if (error) {
            this.submissionError = error.message;
            this.$emit('failed', error);
          } else {
            this.resetForm();
            console.log(token)
            var elements = {
              amount: this.precio_final,
              description: "Pedido numero: " + this.id,
              source: token.id
            }

            await this.myfunc(elements)
            this.$emit('success', token);
          }
        } catch (error) {
          this.$emit('failed', error);
        } finally {
          this.loading = false;
        }
      },
      resetForm() {
        for (const [elementType, item] of Object.entries(this.card)) {
          this.card[elementType].clear();
        }
      },
      updated(e) {
        const elementType = e['elementType'];
        const error = e['error'];
        if (error) {
          this.errors[elementType] = e['error']['message'];
          return null;
        } else {
          if (this.errors[elementType]) {
            this.errors[elementType] = '';
          }
        }
      },
      isValid(elementType) {
        return this.errors[elementType] === '';
      },
      errorMessage(elementType) {
        return this.isValid(elementType) ? this.errors[elementType] : false;
      }
    }
    ,
    async mounted() {
      var userDirection = await this.$axios.get(this.url_server_api + '/userHaveDirection')
      console.log(userDirection.data)
      if (userDirection.data !== true){
        console.log(userDirection.data)
        this.del = true
      } else {
        this.id = this.$router.currentRoute.params.id
        var preciofinal = await this.$axios.post(this.url_server_api + '/getPrecioPedido', {
          pedidoid: this.id
        });
        this.precio_final = preciofinal.data
        const style = {
          base: {
            fontFamily: '"Roboto", "-apple-system", "Helvetica Neue", Helvetica, Arial, sans-serif',
            '::placeholder': {
              color: '#CFD7E0',
            },
          },
        };
        if (!this.stripe) {
          this.stripe = await loadStripe('pk_test_LESShQ47cPmtRV4MhkefvSax00lZTqQsOv');
        }
        if (!this.elements) {
          const cardElements = ['cardNumber', 'cardExpiry', 'cardCvc']
          this.elements = this.stripe.elements();
          console.log(this.elements)
          cardElements.forEach(element => {
            this.card[element] = this.elements.create(element, {style: style});
            this.card[element].mount('#' + element);
            this.card[element].addEventListener('change', (e) => this.updated(e));
          });
        }
      }
    },
  }
</script>

<style scoped lang="scss">
  .StripeElement--invalid {
    border-color: transparent
  }
</style>
