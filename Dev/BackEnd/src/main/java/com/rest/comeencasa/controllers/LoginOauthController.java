package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.service.LoginService;
import com.rest.comeencasa.service.LoginServiceOauth;
import com.rest.comeencasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginOauthController {
    Gson gson = new Gson();

    @Autowired
    LoginService loginService;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @Autowired
    UserService userService;

    @GetMapping("/loginOauth")
    public String app() throws Exception {
        URL url = loginServiceOauth.getGoogleRedirectURL();
        return "redirect:" + url;
    }

    @GetMapping("/auth/oauth2callback/")
    @Transactional
    public ResponseEntity<String> oauthCallback(@RequestParam String code, HttpSession session) throws Exception {
        String accessToken = loginServiceOauth.getAccessToken(code);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(accessToken);
        session.setAttribute("userDetails", userDetails);
        session.setAttribute("username", userDetails.get("email"));
        Usuario us = new Usuario();
        us.setEmail(userDetails.get("email"));
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("token", userDetails);

        if (!userService.isRegistred(us)) {
            us.setOauth(1);
            userService.addUser(us);
            Usuario userSession = userService.getUser(us);
            session.setAttribute("usernameId", userSession.getId());
            session.setAttribute("userObject", userSession);
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);

        } else {
            Usuario userSession = userService.getUser(us);
            session.setAttribute("usernameId", userSession.getId());
            session.setAttribute("userObject", userSession);
            return new ResponseEntity<>(gson.toJson(restMap), HttpStatus.ACCEPTED);

        }
    }

}
