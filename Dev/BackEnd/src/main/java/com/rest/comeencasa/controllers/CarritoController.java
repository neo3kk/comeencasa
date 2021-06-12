package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.*;
import com.rest.comeencasa.repos.UsuarioRepository;
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
public class CarritoController {
    Gson gson = new Gson();
    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    PlatoService platoService;

    @Autowired
    MenuService menuService;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/getCarrito")
    public ResponseEntity<String> getOpenPedido(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
            if (pedido != null) {
                List<PedidoPlato> pp = pedido.getPedidoPlato();
                List<Plato> platos = new ArrayList<>();
                pp.forEach(result -> {
                    platos.add(result.getPlato());
                });
                List<PlatoDTO> platoDTOS = platoService.createListplatoDTO(platos);
                return new ResponseEntity<>(gson.toJson(platoDTOS), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/getCarritoById")
    public ResponseEntity<String> getCarritoById(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        Pedido pedido = pedidoService.findPedidoById(Long.parseLong(map.get("id")));
        if (pedido != null) {
            List<PedidoPlato> pp = pedido.getPedidoPlato();
            List<Plato> platos = new ArrayList<>();
            pp.forEach(result -> {
                platos.add(result.getPlato());
            });
            List<PlatoDTO> platoDTOS = platoService.createListplatoDTO(platos);
            return new ResponseEntity<>(gson.toJson(platoDTOS), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getPedido")
    public ResponseEntity<String> getPedido(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
            if (pedido != null) {
                return new ResponseEntity<>(gson.toJson(pedido.getId()), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/getPedidoById")
    public ResponseEntity<String> getPedidoById(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            Map<String, String> map = gson.fromJson(payload, HashMap.class);
            Pedido pedido = pedidoService.findPedidoById(Long.parseLong(map.get("id")));
            if (pedido != null) {
                return new ResponseEntity<>(gson.toJson(pedido.getId()), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getMenus")
    public ResponseEntity<String> getMenu(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
           Usuario user = userService.getUserByEmail(email);
            Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
            List<Menu> menus = new ArrayList<>();
            if (pedido != null) {
                pedido.getPedidoMenus().forEach(pedidoMenu -> {
                    menus.add(pedidoMenu.getMenu());
                });

                List<MenuDTO> menuDTOS = menuService.creatListMenu(menus);
                return new ResponseEntity<>(gson.toJson(menuDTOS), HttpStatus.ACCEPTED);
            } else {
                if (user !=null){
                    pedido = new Pedido();
                    pedido.setUsuario(user);
                    pedido.setEstado("Pendiente");
                    pedidoService.savePedido(pedido);
                }
                return new ResponseEntity<>(HttpStatus.CONTINUE);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/getMenusById")
    public ResponseEntity<String> getMenusById(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            Map<String, String> map = gson.fromJson(payload, HashMap.class);
            Pedido pedido = pedidoService.findPedidoById(Long.parseLong(map.get("id")));
            List<Menu> menus = new ArrayList<>();
            if (pedido != null) {
                pedido.getPedidoMenus().forEach(pedidoMenu -> {
                    menus.add(pedidoMenu.getMenu());
                });

                List<MenuDTO> menuDTOS = menuService.creatListMenu(menus);
                return new ResponseEntity<>(gson.toJson(menuDTOS), HttpStatus.ACCEPTED);
            } else {
                if (user != null) {
                    pedido = new Pedido();
                    pedido.setUsuario(user);
                    pedido.setEstado("Pendiente");
                    pedidoService.savePedido(pedido);
                }
                return new ResponseEntity<>(HttpStatus.CONTINUE);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
