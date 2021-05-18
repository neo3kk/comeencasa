package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.service.PedidoService;
import com.rest.comeencasa.service.TokenService;
import com.rest.comeencasa.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PedidoController {
    Gson gson = new Gson();

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenService tokenService;


    @PostMapping("/pedidos")
    public ResponseEntity<String> getAllByUser(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            if (validate != null) {
                user = userService.getUserByEmail(validate);
            }
        }

        if (user != null){
            List<Pedido> pedidos = user.getPedidos();
            Map<String, Object> restMap = new HashMap<>();
            restMap.put("pedidos", pedidos);
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
