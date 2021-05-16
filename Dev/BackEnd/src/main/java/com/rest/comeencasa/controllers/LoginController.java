package com.rest.comeencasa.controllers;


import com.google.gson.Gson;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.UsuarioRepository;
import com.rest.comeencasa.service.LoginService;
import com.rest.comeencasa.service.TokenService;
import com.rest.comeencasa.service.UserService;
import com.rest.comeencasa.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
   Gson gson = new Gson();

    @Autowired
    LoginService loginService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        if (!loginService.checkUserAndPassword(email, password)) {
            Map<String, Object> restMap = new HashMap<>();
            restMap.put("message", "Incorrect email or password.");
            return new ResponseEntity<>(gson.toJson(restMap),HttpStatus.UNAUTHORIZED);
        }
        Usuario user = usuarioRepository.findUsuarioByEmailAndPassword(email, password);
        String token = tokenService.newToken(email);
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("token", token);
        restMap.put("user", user);
        return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);
    }


    @Transactional
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        String name = map.get("name");
        Usuario user = new Usuario();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        if (userService.getUser(user)==null){
            userService.addUser(user);
            Map<String, Object> restMap = new HashMap<>();
            restMap.put("message", "done");
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);
        } else {
            Map<String, Object> restMap = new HashMap<>();
            restMap.put("error", "Bad request");
            restMap.put("message", "This user already exists");
            restMap.put("statusCode", 400);
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.BAD_REQUEST);
        }


    }
}
