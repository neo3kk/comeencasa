package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.*;

import com.rest.comeencasa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PlatoController {
    Gson gson = new Gson();

    @Autowired
    TokenService tokenService;

    @Autowired
    MenuService menuService;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @Autowired
    PlatoService platoService;

    @Autowired
    UserService userService;

    @GetMapping("/platos")
    public ResponseEntity<String> getAll() {
        List<Plato> platos = platoService.findAll();
        List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);
        return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
    }

    @PostMapping("/platosByUsuario")
    public ResponseEntity<String> getAllByUser(){
        List<Plato> platos = platoService.findAll();
        List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);
        return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
    }

    @PostMapping("/getPlatosMenu")
    public ResponseEntity<String> getPlatosMenu(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            if (validate != null) {
                user = userService.getUserByEmail(validate);
                Map<String, Double> map = gson.fromJson(payload, HashMap.class);
                Double id = map.get("idmenu");
                Menu menu = menuService.findById(id.longValue());
                List<PlatoMenu> platoMenus = menu.getPlatoMenu();
                List<Plato> platos = new ArrayList<>();
                platoMenus.forEach(platoMenu ->{
                    platos.add(platoMenu.getPlato());
                });
                List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);


                return new ResponseEntity<>(gson.toJson(pedidoDTOList),HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/getPlatoById")
    public ResponseEntity<String> getPlatoById(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            if (validate != null) {
                user = userService.getUserByEmail(validate);
                Map<String, String> map = gson.fromJson(payload, HashMap.class);
                String id = map.get("idplato");
                Plato plato = platoService.findPlatoById(Long.valueOf(id));
                PlatoDTO platoDTO = platoService.makePlatoDto(plato);
                /*Menu menu = menuService.findById(id.longValue());
                List<PlatoMenu> platoMenus = menu.getPlatoMenu();
                List<Plato> platos = new ArrayList<>();
                platoMenus.forEach(platoMenu ->{
                    platos.add(platoMenu.getPlato());
                });
                List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);*/


                return new ResponseEntity<>(gson.toJson(platoDTO),HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
