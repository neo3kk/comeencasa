package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Usuario;

import javax.servlet.http.HttpServletRequest;

public interface UserService {


    Usuario findUsuarioByEmail(String email);

    boolean createUser(Usuario user);

    Usuario getUerRequest(HttpServletRequest request);

    Usuario updateUser(String payload, HttpServletRequest request);

    Usuario updatePassword(String newPassword, HttpServletRequest request);

    boolean checkpassword(HttpServletRequest request, String currentPassword);
}
