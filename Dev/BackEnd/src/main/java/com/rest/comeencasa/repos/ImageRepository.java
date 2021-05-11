package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByUser(User user);
}

