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
        String email = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        if (userDetails.get("email") != null) {
            email = userDetails.get("email");
        }
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
            return new ResponseEntity<>(gson.toJson(null), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getMenus")
    public ResponseEntity<String> getMenu(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        if (userDetails.get("email") != null) {
            email = userDetails.get("email");
        }
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
            return new ResponseEntity<>(gson.toJson(null), HttpStatus.ACCEPTED);
        }
    }
}
