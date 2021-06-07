package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.rest.comeencasa.entities.*;
import com.rest.comeencasa.service.LoginServiceOauth;
import com.rest.comeencasa.service.TokenService;
import com.rest.comeencasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    Gson gson = new Gson();

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    LoginServiceOauth loginServiceOauth;

    @GetMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String auth) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        validate = userDetails.get("email");

        user = userService.getUserByEmail(validate);
        userService.deleteUser(user);

        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);

    }
    @GetMapping("/getUserDetails")
    public ResponseEntity<String> getUserDetails(@RequestHeader("Authorization") String auth) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);

        user = userService.getUserByEmail(validate);
        Map<String, String> myMap = loginServiceOauth.getUserDetails(token);
        myMap.put("name",user.getName());
        myMap.put("last_name",user.getLast_name());
        myMap.put("email",user.getEmail());
        return new ResponseEntity<>(gson.toJson(myMap), HttpStatus.ACCEPTED);

    }
    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);

        user = userService.getUserByEmail(validate);
        Map<String, String> map1 = gson.fromJson(payload, HashMap.class);
        user.setEmail(map1.get("email"));
        user.setName(map1.get("name"));
        user.setLast_name(map1.get("last_name"));
        userService.save(user);

        return new ResponseEntity<>("Se ha guardado el menu correctamente", HttpStatus.ACCEPTED);

    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        validate = userDetails.get("email");

        user = userService.getUserByEmail(validate);
        Map<String, String> map1 = gson.fromJson(payload, HashMap.class);

        user.setPassword(map1.get("password"));
        userService.save(user);

        return new ResponseEntity<>("Se ha guardado el menu correctamente", HttpStatus.ACCEPTED);

    }
    @PostMapping("/getNameByEmail")
    public ResponseEntity<String> getNameByEmail(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;

        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        user = userService.getUserByEmail(map.get("email"));
        System.out.println(user.getName());


        return new ResponseEntity<>(gson.toJson(user.getAvatarUrl()), HttpStatus.ACCEPTED);

    }
}
