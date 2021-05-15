package com.rest.comeencasa.service;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    Gson gson = new Gson();

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;


    @Override
    public List<Usuario> getAll() {
        List<Usuario> usuaris = usuarioRepository.findAll();
        return usuaris;
    }

    @Override
    public Usuario getUser(Usuario usuari) {
        Usuario us = usuarioRepository.findUsuarioByEmail(usuari.getEmail());
        return us;
    }

    @Override
    public Usuario getUserByid(Long userId) {
        Usuario us = usuarioRepository.findById(userId).get();
        return us;
    }

    @Override
    public boolean isRegistred(Usuario usuari) {
        Usuario us = usuarioRepository.findUsuarioByEmail(usuari.getEmail());

        if (us != null) {
            if (usuari.getOauth() == 1) {
                return true;
            }
            boolean passDecrypt = BCrypt.checkpw(usuari.getPassword(), us.getPassword());
            if (us.getEmail().equals(usuari.getEmail()) && passDecrypt) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean validateUser(String user) {

        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(user);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean validatePass(String pass) {
        //Un digit, una lletra minuscula, una minuscula, un caracter especial, no espais en blanc, 8 caracters
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}$");
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    @Override
    public boolean addUser(Usuario usuari) {
        if (usuarioRepository.findUsuarioByEmail(usuari.getEmail()) == null) {
            String passEncripted = BCrypt.hashpw(usuari.getPassword(), BCrypt.gensalt(10));

            try {
/*                Usuari userSave = new Usuari();
                userSave.setEmail(usuari.getEmail());
                userSave.setPwd(passEncripted);
                userSave.setOauth(usuari.getOauth());*/
                usuari.setPassword(passEncripted);
                usuarioRepository.save(usuari);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public void deleteUser(Usuario userObject) {
        usuarioRepository.delete(userObject);
    }

    @Override
    public boolean createUser(Usuario user) {
        return false;
    }

}
