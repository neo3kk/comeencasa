package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoDTO;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.service.*;
import com.rest.comeencasa.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/pedidos")
    public ResponseEntity<String> getAllByUser(@RequestHeader("Authorization") String auth) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            ;
            if (validate != null) {
                user = userService.getUserByEmail(validate);
            }
        }
        if (user != null) {
            List<Pedido> pedidos = pedidoService.findByUsuario(user);
            List<PedidoDTO> pedidoDTOList = pedidoService.createListpedidoDTO(pedidos);
            return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/pedidos")
    public ResponseEntity<String> createPedido(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
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
                String ubicacion = map.get("ubicacion");
                String precio_final = map.get("precio_final");
                Pedido pedido = new Pedido();
                pedido.setFecha_pedido(utils.getToday());
                pedido.setUbicacion(ubicacion);
                pedido.setUsuario(user);
                pedido.setPrecio_final(precio_final);
                pedidoService.makePedidoDto(pedido);
                return new ResponseEntity<>("Se ha realizado el pedido correctamente",HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
