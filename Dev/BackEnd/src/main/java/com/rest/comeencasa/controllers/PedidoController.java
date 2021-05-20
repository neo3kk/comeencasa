package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PedidoController {
    Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    TokenService tokenService;

    @Autowired
    LoginServiceOauth loginServiceOauth;


    @PostMapping ("/pedidos")
    public ResponseEntity<String> getAllByUser(@RequestHeader("Authorization") String auth) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if(userDetails.get("email") != null){
                validate = userDetails.get("email");
            };
            if (validate != null) {
                user = userService.getUserByEmail(validate);
            }
        }
        if (user != null) {
            List<Pedido> pedidos = pedidoService.findByUsuario(user);
            Map<String, Object> restMap = new HashMap<>();
            restMap.put("pedidos", pedidos);
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
