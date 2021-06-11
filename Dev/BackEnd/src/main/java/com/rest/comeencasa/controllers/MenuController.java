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
public class MenuController {
    Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    PedidoMenuService pedidoMenuService;

    @Autowired
    PlatoService platoService;

    @Autowired
    MenuService menuService;

    @Autowired
    PedidoPlatoService pedidoPlatoService;

    @Autowired
    TokenService tokenService;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @DeleteMapping("/deleteMenuPedido")
    public ResponseEntity<String> deletePlato(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        if (userDetails.get("email") != null) {
            validate = userDetails.get("email");
        }
        if (validate != null) {
            user = userService.getUserByEmail(validate);
            Map<String, Double> map = gson.fromJson(payload, HashMap.class);
            double idmenu = map.get("idmenu");
            Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
            List<PedidoMenu> pedidoMenus = pedido.getPedidoMenus();
            for (int i = 0; i < pedidoMenus.size(); i++) {
                if ((double) pedidoMenus.get(i).getMenu().getId() == idmenu) {
                    pedidoMenus.remove(pedidoMenus.get(i));
                }
            }
            pedido.setPedidoMenus(pedidoMenus);
            pedido.setPrecio_final(String.valueOf(Double.valueOf(pedido.getPrecio_final())- 12.00));
            pedidoService.savePedido(pedido);
            return new ResponseEntity<>("Se ha añadido el plato correctamente", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/getPlatosByMenuId")
    public ResponseEntity<String> menuById(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {

        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        long idmenu = Long.parseLong(map.get("idmenu"));
        Menu menu = menuService.findById(idmenu);
        List<Plato> platos = new ArrayList<>();
        menu.getPlatoMenu().forEach(platoMenu -> {
            platos.add(platoMenu.getPlato());
        });
        List<PlatoDTO> platoDTOS = platoService.createListplatoDTO(platos);
        return new ResponseEntity<>(gson.toJson(platoDTOS), HttpStatus.ACCEPTED);
    }
}
