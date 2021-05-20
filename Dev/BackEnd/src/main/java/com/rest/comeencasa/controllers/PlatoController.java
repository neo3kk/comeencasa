package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.*;

import com.rest.comeencasa.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PlatoController {
    Gson gson = new Gson();

    @Autowired
    PlatoService platoService;

    @GetMapping("/platos")
    public ResponseEntity<String> getAllByUser(@RequestHeader("Authorization") String auth) throws Exception {
        List<Plato> platos = platoService.findAll();
        List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);
        return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
    }

}
