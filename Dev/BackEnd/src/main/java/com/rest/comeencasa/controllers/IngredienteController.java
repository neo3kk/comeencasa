package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IngredienteController {
    Gson gson = new Gson();

    @Autowired
    IngredienteService ingredienteService;


    @GetMapping("/admin/getAllIngredientes")
    public ResponseEntity<String> getAllIngredientes(){
        List<Ingrediente> ingredienteList = ingredienteService.ingredientes();
        return new ResponseEntity<>(gson.toJson(ingredienteList), HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/admin/addingredient")
    public ResponseEntity<String> addingredient(@RequestBody String payload ){
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String ingrediente = map.get("name");
        boolean add = ingredienteService.addIngrediente(ingrediente);
        if(add){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


    }

}
