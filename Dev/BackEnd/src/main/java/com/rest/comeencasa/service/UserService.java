package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.User;
import com.rest.comeencasa.entities.UserDTO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserDTO makeUserDTO(User user);

    User findUserByemail(String email);

    boolean createUser(User user);

    User getUerRequest(HttpServletRequest request);

    User updateUser(String payload, HttpServletRequest request);

    User updatePassword(String newPassword, HttpServletRequest request);

    boolean checkpassword(HttpServletRequest request, String currentPassword);
}
