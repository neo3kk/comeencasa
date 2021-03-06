package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.service.LoginServiceOauth;
import com.rest.comeencasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginOauthController {
    Gson gson = new Gson();

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @Autowired
    UserService userService;

    @GetMapping("/loginOauth")
    public ModelAndView method() throws Exception {
        URL url = loginServiceOauth.getGoogleRedirectURL();
        return new ModelAndView("redirect:" + url);
    }

    @PostMapping("/auth/oauth2callback/")
    @Transactional
    public ResponseEntity<String> oauthCallback(@RequestParam String code) throws Exception {
        String accessToken = loginServiceOauth.getAccessToken(code);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(accessToken);
        Usuario us = new Usuario();
        us.setEmail(userDetails.get("email"));
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("tokenLogin", accessToken);
        restMap.put("user", userDetails.get("email"));
        restMap.put("picture", userDetails.get("picture"));

        if (!userService.isRegistred(us)) {
            us.setOauth(true);
            us.setName(userDetails.get("email"));
            us.setCalle("");
            us.setAvatarUrl(userDetails.get("picture"));
            us.setCodigo_postal("");
            us.setLast_name("");
            userService.addUser(us);
            restMap.put("oauth", us.isOauth());
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);

        } else {
            Usuario registredOauth = userService.getUser(us);
            restMap.put("oauth", registredOauth.isOauth());

            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);

        }
    }

}
