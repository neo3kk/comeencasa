package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {


    List<Usuario> getAll();

    Usuario getUserByEmail(String email);

    Usuario getUser(Usuario usuari);

    Usuario getUserByid(Long userId);

    boolean isRegistred(Usuario usuari);

    String validateUser(String user) throws Exception;

    boolean validatePass(String pass);

    boolean addUser(Usuario usuari);

    void deleteUser(Usuario userObject);

    boolean createUser(Usuario user);

    String processAvatar(String avatar, String userId);

    void save(Usuario user);
}
