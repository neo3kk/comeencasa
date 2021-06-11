package com.rest.comeencasa.interceptors;


import com.rest.comeencasa.service.LoginServiceOauth;
import com.rest.comeencasa.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String auth = request.getHeader("Authorization");
        System.out.println("hola");
        System.out.println(auth);
        System.out.println("hola");
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if(userDetails.get("email") != null){
             validate = userDetails.get("email");
            };
            if (validate==null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;

            } else if (validate.equals("EXPIRED")) {
                response.sendError(403, "EXPIRED");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
