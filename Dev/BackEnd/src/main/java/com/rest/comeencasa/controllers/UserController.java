package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rest.comeencasa.entities.*;
import com.rest.comeencasa.service.AlergenoService;
import com.rest.comeencasa.service.LoginServiceOauth;
import com.rest.comeencasa.service.TokenService;
import com.rest.comeencasa.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
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

    @Autowired
    AlergenoService alergenoService;

    @GetMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            userService.deleteUser(user);
            return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("InvalidToken", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/userHaveDirection")
    public ResponseEntity<String> userHaveDirection(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            Map<String, String> myMap = loginServiceOauth.getUserDetails(token);
            if (user.getCalle()==null||user.getCodigo_postal()==null||user.getNumero()==null||user.getLetra()==null){
                return new ResponseEntity<>("false", HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>("true", HttpStatus.ACCEPTED);
            }
        } else {
            return new ResponseEntity<>("InvalidToken", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/getUserDetails")
    public ResponseEntity<String> getUserDetails(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);
        if (email != null) {
            Usuario user = userService.getUserByEmail(email);
            Map<String, String> myMap = new HashMap<>();
            myMap.put("name", user.getName());
            myMap.put("last_name", user.getLast_name());
            myMap.put("email", user.getEmail());
            myMap.put("calle", user.getCalle());
            myMap.put("codigo_postal", user.getCodigo_postal());
            myMap.put("numero", user.getNumero());
            myMap.put("letra", user.getLetra());
            return new ResponseEntity<>(gson.toJson(myMap), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("InvalidToken", HttpStatus.BAD_REQUEST);
        }

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

    @PostMapping("/updateUserDirection")
    public ResponseEntity<String> updateUserDirection(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);

        user = userService.getUserByEmail(validate);
        Map<String, String> map1 = gson.fromJson(payload, HashMap.class);
        user.setCalle(map1.get("calle"));
        user.setCodigo_postal(map1.get("codigo_postal"));
        user.setNumero(map1.get("numero"));
        user.setLetra(map1.get("letra"));
        userService.save(user);

        return new ResponseEntity<>("Se ha guardado el menu correctamente", HttpStatus.ACCEPTED);

    }

    @PostMapping("/profile/updateAlergenos")
    public ResponseEntity<String> updateAlergenos(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        String token = auth.replace("Bearer ", "");
        String email = userService.validateUser(token);


        if (email != null) {
            Map<String, ArrayList> alergenos = gson.fromJson(payload, HashMap.class);
            ArrayList arrayList = alergenos.get("alergenos");

            List<AlergenosUsuario> alergenoList = new ArrayList<>();

            Type Alergeno = new TypeToken<Alergeno>(){}.getType();
            Usuario user = userService.getUserByEmail(email);
            userService.deleteAlergenos(user);
            arrayList.forEach(al -> {
                Alergeno alergeno = gson.fromJson(al.toString(), Alergeno);
                AlergenosUsuario alu = new AlergenosUsuario();
                alu.setAlergeno(alergeno);
                alu.setUsuario(user);
                alergenoService.adddAlergenoUsuario(alu);
            });

            return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);

        user = userService.getUserByEmail(validate);
        Map<String, String> map1 = gson.fromJson(payload, HashMap.class);
        System.out.println(user.getPassword());
        System.out.println(map1.get("new_password"));
        if (BCrypt.checkpw(map1.get("last_password"), user.getPassword())) {
            String passEncripted = BCrypt.hashpw(map1.get("new_password"), BCrypt.gensalt(10));
            user.setPassword(passEncripted);
            userService.save(user);
            return new ResponseEntity<>("200", HttpStatus.ACCEPTED);
        }


        return new ResponseEntity<>("ha habido un problema", HttpStatus.ACCEPTED);

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
