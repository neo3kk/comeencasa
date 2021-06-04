package com.rest.comeencasa.controllers;

import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping(value = "/images/users/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String name){
        Image i = imageService.getImageByFileName( name );
        System.out.println("hola");
        System.out.println(Arrays.toString(i.getBytes()));
        System.out.println("hola");
        return new ResponseEntity<>(i.getBytes(), HttpStatus.OK);

    }
}