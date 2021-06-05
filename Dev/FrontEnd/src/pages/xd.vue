<template>
  <div id="payment-form">

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
      @click="submitForm"/>

  </div>
</template>

<script>
  import {loadStripe} from '@stripe/stripe-js/pure';

  export default {
    props: {
      data: {type: Object, required: false, default: () => {}}
    },

    data() {
      return {

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
      async myfunc(elements) {
        let crearPlato = await this.$axios.post(this.url_server_api + '/charge',{
          amount: elements.amount,
          description: elements.description,
          source: elements.source
        })
      },
      async submitForm(e) {
        e.preventDefault();
        try {
          this.loading = true;
          this.submissionError = null;
          const { token, error } = await this.stripe.createToken(this.card['cardNumber']);
          console.log(token)
          console.log(error);
          if(error) {
            this.submissionError = error.message;
            this.$emit('failed', error);
          } else {
            this.resetForm();
            console.log(token)
            var elements = {
                amount: '20.00',
              description: "no tengo ni idea de que poner",
              source: token
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
    },

    async mounted() {

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
        cardElements.forEach(element => {
          this.card[element] = this.elements.create(element, {style: style});
          this.card[element].mount('#' + element);
          this.card[element].addEventListener('change', (e) => this.updated(e));
        });
      }
    }
  }
</script>

<style scoped lang="scss">
  .StripeElement--invalid {
    border-color: transparent
  }
</style>

