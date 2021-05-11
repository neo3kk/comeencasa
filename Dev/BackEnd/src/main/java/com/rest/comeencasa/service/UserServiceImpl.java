package com.rest.comeencasa.service;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
    Gson gson = new Gson();

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;


    @Override
    public Usuario findUsuarioByEmail(String email) {
        return null;
    }

    @Override
    public boolean createUser(Usuario user) {
        return false;
    }

    @Override
    public Usuario getUerRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public Usuario updateUser(String payload, HttpServletRequest request) {
        return null;
    }

    @Override
    public Usuario updatePassword(String newPassword, HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean checkpassword(HttpServletRequest request, String currentPassword) {
        return false;
    }
}
