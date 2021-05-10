package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmailAndPassword(String name, String pass);

    User findUserByName(String user);

    User findUserByemail(String email);


}

