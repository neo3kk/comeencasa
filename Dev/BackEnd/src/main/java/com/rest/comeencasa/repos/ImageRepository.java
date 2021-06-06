package com.rest.comeencasa.repos;

import com.rest.comeencasa.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findImageByFileNameContains(String fileName);

    Image findImageByFileName(String fileName);
}
