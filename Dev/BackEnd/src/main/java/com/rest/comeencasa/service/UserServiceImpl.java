package com.rest.comeencasa.service;

import com.google.gson.Gson;
import com.rest.comeencasa.entities.AlergenosUsuario;
import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.AlergenosUsuarioRepository;
import com.rest.comeencasa.repos.ImageRepository;
import com.rest.comeencasa.repos.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    Gson gson = new Gson();

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    LoginServiceOauth loginServiceOauth;

    @Autowired
    AlergenosUsuarioRepository alergenosUsuarioRepository;

    @Autowired
    ImageService imageService;


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
    public Usuario getUserByEmail(String email) {
        Usuario us = usuarioRepository.findUsuarioByEmail(email);
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
            if (usuari.isOauth()) {
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
    public String validateUser(String token) throws Exception {
        String email = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        if (userDetails.get("email") != null) {
            email = userDetails.get("email");
        }
        return email;
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
    public String processAvatar(String avatar, String userId) {
        if (avatar != null) {
            byte[] bytes = Base64.decodeBase64(avatar);
            Image image = new Image();
            image.setBytes(bytes);
            image.setFileName(userId + "profile.png");
            Image i = imageService.getImageByFileName(userId+"profile.png");
            if(i != null){
                imageService.delete(i);
            }
            imageRepository.save(image);
            return image.getFileName();
        }
        return null;
    }

    @Override
    public void save(Usuario user) {
        usuarioRepository.save(user);
    }

    @Override
    public boolean deleteAlergenos(Usuario usuario) {
       List<AlergenosUsuario> alergenosUsuariosList = alergenosUsuarioRepository.findAlergenosUsuarioByUsuario(usuario);
       alergenosUsuariosList.forEach(alergenosUsuario -> {
           alergenosUsuarioRepository.delete(alergenosUsuario);
       });
        return true;
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
