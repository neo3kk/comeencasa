package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.entities.IngredienteDTO;
import com.rest.comeencasa.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<IngredienteDTO> ingredienteDTOS = ingredienteService.createListIngredienteDTO(ingredienteList);
        return new ResponseEntity<>(gson.toJson(ingredienteDTOS), HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/admin/addingredient")
    public ResponseEntity<String> addingredient(@RequestBody String payload ){
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String ingrediente = map.get("name");
        String traduccion = map.get("traduccion");
        double energy = Double.parseDouble(map.get("energia"));
        double sugar = Double.parseDouble(map.get("azucar"));
        double prot = Double.parseDouble(map.get("proteinas"));
        double fsat = Double.parseDouble(map.get("grasas"));
        boolean add = ingredienteService.addIngrediente(ingrediente, traduccion, energy, sugar, fsat, prot);
        if(add){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Transactional
    @DeleteMapping("/admin/deleteIngredient")
    public ResponseEntity<String> deleteIngredient(@RequestBody String payload ){
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String ingrediente = map.get("name");
        System.out.println(ingrediente);
        boolean del = ingredienteService.delete(ingrediente);
        if(del){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
