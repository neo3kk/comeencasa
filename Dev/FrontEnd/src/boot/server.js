const stripe = require('stripe')('sk_test_KnQIFLmpCoWbMWGMXTP23W7V00jj1MLOZi');
const express = require('express');
const app = express();
app.use(express.static('.'));

const YOUR_DOMAIN = 'http://localhost:8080';

app.post('/create-checkout-session', async (req, res) => {
  const session = await stripe.checkout.sessions.create({
    payment_method_types: ['card'],
    line_items: [
      {
        price_data: {
          currency: 'usd',
          product_data: {
            name: 'Stubborn Attachments',
            images: ['https://i.imgur.com/EHyR2nP.png'],
          },
          unit_amount: 2000,
        },
        quantity: 1,
      },
    ],
    mode: 'payment',
    success_url: `${YOUR_DOMAIN}/success.vue`,
    cancel_url: `${YOUR_DOMAIN}/cancel.vue`,
  });

  res.json({ id: session.id });
});

app.listen(4242, () => console.log('Running on port 4242'));
