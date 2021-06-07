package com.rest.comeencasa.controllers;


import com.google.gson.Gson;
import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.ImageRepository;
import com.rest.comeencasa.repos.UsuarioRepository;
import com.rest.comeencasa.service.ImageService;
import com.rest.comeencasa.service.TokenService;
import com.rest.comeencasa.service.UserServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    Gson gson = new Gson();

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ImageService imageService;

    @Value("${server.domain}")
    String serverDomain;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        Usuario us = new Usuario();
        us.setEmail(email);
        us.setPassword(password);

        if (!userService.isRegistred(us)) {
            Map<String, Object> restMap = new HashMap<>();
            restMap.put("message", "Incorrect email or password.");
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.UNAUTHORIZED);
        }
        Usuario logged = userService.getUser(us);
        String token = tokenService.newToken(email);
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("tokenLogin", token);
        restMap.put("user", email);
        restMap.put("picture", logged.getAvatarUrl());

        return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("email");
        String password = map.get("password");
        String name = map.get("name");
        String last_name = map.get("last_name");
        String avatar = map.get("file");
        byte[] bytes = Base64.encodeBase64(avatar.getBytes());
        Usuario user = new Usuario();
        user.setName(name);
        user.setEmail(email);
        user.setLast_name(last_name);
        user.setPassword(password);

        user.setAvatarUrl(serverDomain + "/images/users/" + userService.processAvatar(avatar, user.getName()));
        if (userService.getUser(user) == null) {
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

    @PostMapping("/getImage")
    public HttpEntity<? extends Serializable> getImage(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("user");
        Usuario usuario = userService.getUserByEmail(email);

        return new ResponseEntity<String>(usuario.getAvatarUrl(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/getImage2")
    public HttpEntity<? extends Serializable> getImage2(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String email = map.get("user");
        Usuario usuario = userService.getUserByEmail(email);

        return new ResponseEntity<String>(usuario.getAvatarUrl(), HttpStatus.ACCEPTED);
    }


    @PostMapping("/upload/image")
    public HttpEntity<? extends Serializable> uploadFileRegister(@RequestPart(value = "file") MultipartFile uploadfile) {

        try {
            byte[] bytes = Base64.encodeBase64(uploadfile.getBytes());
            return new ResponseEntity<>(bytes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>("ERROR", HttpStatus.UNAUTHORIZED);
        }
    }
}
