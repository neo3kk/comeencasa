package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.rest.comeencasa.entities.ChargeRequest;
import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.entities.PlatoIngrediente;
import com.rest.comeencasa.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class ChargeController {
    Gson gson = new Gson();

    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model, @RequestBody String payload)
            throws StripeException {
        Map<String, String> map1 = gson.fromJson(payload, HashMap.class);
        Map<String, LinkedTreeMap > map2 = gson.fromJson(payload, HashMap.class);
        chargeRequest.setDescription(map1.get("description"));
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        chargeRequest.setStripeToken(String.valueOf(map2.get("source")));
        chargeRequest.setAmount((int) Math.round(Double.parseDouble(map1.get("amount"))*100));
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return gson.toJson(model);
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}
