package com.rest.comeencasa.controllers;

import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;


import com.google.gson.Gson;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    Gson gson = new Gson();
    static final String YOUR_DOMAIN = "http://localhost:8080";
    static final String apiKey = "sk_test_KnQIFLmpCoWbMWGMXTP23W7V00jj1MLOZi";


    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> payment(@RequestBody String payload) throws StripeException {

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN + "/success.html")
                        .setCancelUrl(YOUR_DOMAIN + "/cancel.html")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(2000L)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Stubborn Attachments")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();
        Session session = Session.create(params);
        HashMap<String, String> responseData = new HashMap<String, String>();
        responseData.put("id", session.getId());
        return new ResponseEntity<>(gson.toJson(responseData), HttpStatus.ACCEPTED);
    }

}
