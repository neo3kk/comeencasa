package com.rest.comeencasa.repos;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByUser(User user);
}

