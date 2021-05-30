package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoMenu;
import com.rest.comeencasa.entities.PedidoPlato;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
                double idmenu = map.get("idmenu");
                Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user,"Pendiente");
                List<PedidoMenu> pedidoMenus = pedido.getPedidoMenus();
                for (int i = 0; i <pedidoMenus.size() ; i++) {
                    if ((double)pedidoMenus.get(i).getMenu().getId() == idmenu){
                        pedidoMenus.remove(pedidoMenus.get(i));
                    }
                }
                pedido.setPedidoMenus(pedidoMenus);
                pedidoService.savePedido(pedido);
                return new ResponseEntity<>("Se ha añadido el plato correctamente", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
