package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.rest.comeencasa.entities.*;

import com.rest.comeencasa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    IngredienteService ingredienteService;

    @Autowired
    PlatoIngredienteService platoIngredienteService;

    @Value("${server.domain}")
    String serverDomain;

    @GetMapping("/platos")
    public ResponseEntity<String> getAll() {
        List<Plato> platos = platoService.findAll();
        List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);
        return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
    }

    @PostMapping("/platosByUsuario")
    public ResponseEntity<String> getAllByUser() {
        List<Plato> platos = platoService.findAll();
        List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);
        return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
    }
    @PostMapping("/setVisibility")
    public ResponseEntity<String> setVisibility(@RequestBody String payload) {
        Map<String, Double> map = gson.fromJson(payload, HashMap.class);
        Map<String, Boolean> map2 = gson.fromJson(payload, HashMap.class);
        Double id = map.get("idplato");
        Plato plato = platoService.findPlatoById(Math.round(id));
        plato.setVisible(map2.get("visible"));
        platoService.save(plato);
        return new ResponseEntity<>("Se ha cambiado la visibilidad del plato", HttpStatus.ACCEPTED);
    }

    @PostMapping("/getPlatosMenu")
    public ResponseEntity<String> getPlatosMenu(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {

        Map<String, Double> map = gson.fromJson(payload, HashMap.class);
        Double id = map.get("idmenu");
        Menu menu = menuService.findById(id.longValue());
        List<PlatoMenu> platoMenus = menu.getPlatoMenu();
        List<Plato> platos = new ArrayList<>();
        platoMenus.forEach(platoMenu -> {
            platos.add(platoMenu.getPlato());
        });
        List<PlatoDTO> pedidoDTOList = platoService.createListplatoDTO(platos);

        return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
    }

    @PostMapping("/getPlatoById")
    public ResponseEntity<String> getPlatoById(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {

        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String id = map.get("idplato");
        Plato plato = platoService.findPlatoById(Long.valueOf(id));
        PlatoDTO platoDTO = platoService.makePlatoDto(plato);


        return new ResponseEntity<>(gson.toJson(platoDTO), HttpStatus.ACCEPTED);

    }

    @PostMapping("/crearPlato")
    public ResponseEntity<String> creatPlato(@RequestBody String payload) throws Exception {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        System.out.println(platoService.findPlatoByName(map.get("nombre")));
        if (platoService.exists(map.get("nombre"))) {
            return new ResponseEntity<>("400", HttpStatus.OK);
        } else {
            Plato plato = new Plato();
            plato.setNombre(map.get("nombre"));
            plato.setPrecio(map.get("precio"));
            plato.setDescription(map.get("description"));
            plato.setTipo_de_plato(map.get("tipo_plato"));
            plato.setTraduccion(map.get("traduccion"));
            plato.setVisible(Boolean.parseBoolean(map.get("visible")));
            Map<String, Double> map3 = gson.fromJson(payload, HashMap.class);
            plato.setAzucar(map3.get("energia"));
            plato.setEnergia(map3.get("azucar"));
            plato.setGrasas(map3.get("grasas"));
            plato.setProteinas(map3.get("proteinas"));
            plato.setImageUrl(serverDomain + "/images/platos/" + platoService.processAvatar(map.get("file"), plato.getNombre()));


            platoService.save(plato);
            Map<String, List> map2 = gson.fromJson(payload, HashMap.class);
            List ingredientes = map2.get("ingredientes");

            List<PlatoIngrediente> platoIngredientes = new ArrayList<>();
            Plato finalPlato = platoService.findPlatoByName(map.get("nombre"));
            ingredientes.forEach(ingrediente -> {
                LinkedTreeMap<Object, Object> t = (LinkedTreeMap) ingrediente;
                float idIngrediente = Float.parseFloat(t.get("id").toString());
                Ingrediente ingrediente1 = ingredienteService.findIngredienteById((long) idIngrediente);
                PlatoIngrediente platoIngrediente = new PlatoIngrediente();
                platoIngrediente.setPlato(finalPlato);
                platoIngrediente.setIngrediente(ingrediente1);
                platoIngredientes.add(platoIngrediente);
                platoIngredienteService.save(platoIngrediente);
            });
            finalPlato.setPlatoIngrediente(platoIngredientes);
            platoService.save(finalPlato);


            PlatoDTO platoDTO = platoService.makePlatoDto(plato);


            return new ResponseEntity<>(gson.toJson(platoDTO), HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/guardarPlato")
    public ResponseEntity<String> guardarPlato(@RequestBody String payload) throws Exception {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        Map<String, Double> map1 = gson.fromJson(payload, HashMap.class);

        Plato plato = platoService.findPlatoById(Math.round((map1.get("id"))));
        List<PlatoIngrediente> platoIngredientes = platoIngredienteService.findByPlato(plato);

        Map<String, List> map2 = gson.fromJson(payload, HashMap.class);
        List ingredientes = map2.get("ingredientes");

        for (int i = 0; i <platoIngredientes.size() ; i++) {
            AtomicReference<Boolean> borrar = new AtomicReference<>(true);
            for (int j = 0; j < ingredientes.size(); j++) {
                LinkedTreeMap<Object, Object> t = (LinkedTreeMap) ingredientes.get(j);
                float idIngrediente = Float.parseFloat(t.get("id").toString());
                Ingrediente ingrediente1 = ingredienteService.findIngredienteById((long) idIngrediente);
                if (ingrediente1 == platoIngredientes.get(i).getIngrediente()) {
                    borrar.set(false);
                }
            }
            if (borrar.get()) {
                System.out.println("lo borra");
                platoIngredientes.remove(platoIngredientes.get(i));
                i--;
            }
        }

        System.out.println(platoIngredientes.size());

        plato.setNombre(map.get("nombre"));
        plato.setPrecio(map.get("precio"));
        plato.setDescription(map.get("description"));
        plato.setTipo_de_plato(map.get("tipo_plato"));
        plato.setTraduccion(map.get("traduccion"));
        plato.setVisible(Boolean.parseBoolean(map.get("visible")));
        plato.setAzucar(map1.get("azucar"));
        plato.setEnergia(map1.get("energia"));
        plato.setGrasas(map1.get("grasas"));
        plato.setProteinas(map1.get("proteinas"));
        Plato finalPlato = plato;
        ingredientes.forEach(ingrediente -> {
            LinkedTreeMap<Object, Object> t = (LinkedTreeMap) ingrediente;
            float idIngrediente = Float.parseFloat(t.get("id").toString());
            Ingrediente ingrediente1 = ingredienteService.findIngredienteById((long) idIngrediente);
            AtomicBoolean esIgual = new AtomicBoolean(false);
            platoIngredientes.forEach(platoIngrediente -> {
                if (ingrediente1 == platoIngrediente.getIngrediente()) {
                    esIgual.set(true);
                }
            });
            if (!esIgual.get()) {
                PlatoIngrediente platoIngrediente = new PlatoIngrediente();
                platoIngrediente.setPlato(finalPlato);
                platoIngrediente.setIngrediente(ingrediente1);
                platoIngredientes.add(platoIngrediente);
                platoIngredienteService.save(platoIngrediente);
            }
        });
        plato.getPlatoIngrediente().clear();
        platoIngredientes.forEach(plato::addChild);
        platoService.save(plato);


        PlatoDTO platoDTO = platoService.makePlatoDto(plato);


        return new ResponseEntity<>(gson.toJson(platoDTO), HttpStatus.ACCEPTED);

    }

    @PostMapping("/getIngredientesByPlato")
    public ResponseEntity<String> getIngredientesByPlato(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {

        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String id = map.get("idplato");
        Plato plato = platoService.findPlatoById(Long.valueOf(id));
        List<PlatoIngrediente> platoIngredientes = plato.getPlatoIngrediente();
        List<Ingrediente> ingredientes = new ArrayList<>();
        platoIngredientes.forEach(platoIngrediente -> {
            ingredientes.add(platoIngrediente.getIngrediente());
        });
        List<IngredienteDTO> ingredienteDTOS = ingredienteService.createListIngredienteDTO(ingredientes);
        return new ResponseEntity<>(gson.toJson(ingredienteDTOS), HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/deletePlato")
    public ResponseEntity<String> deletePlato(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {

        Map<String, Double> map = gson.fromJson(payload, HashMap.class);

        Plato plato = platoService.findPlatoById(Math.round(map.get("idplato")));
        platoService.deletePlato(plato);
        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);

    }

}
