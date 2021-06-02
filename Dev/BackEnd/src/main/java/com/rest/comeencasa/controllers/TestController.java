package com.rest.comeencasa.controllers;


import com.google.gson.Gson;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.UsuarioRepository;
import com.rest.comeencasa.service.LoginServiceOauth;
import com.rest.comeencasa.service.TokenService;
import com.rest.comeencasa.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    Gson gson = new Gson();

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @GetMapping("/getAdmin")
    public ResponseEntity<String> test(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        if (userDetails.get("email") != null) {
            email = userDetails.get("email");
        }
        System.out.println(email);
        if (email != null) {

            return new ResponseEntity<>(email, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("InvalidToken", HttpStatus.OK);
        }
    }

}
