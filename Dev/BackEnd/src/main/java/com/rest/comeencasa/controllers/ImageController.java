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

    @GetMapping(value = "/images/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String name) {
        Image i = imageService.getImageByFileName(name);
        if (i != null) {
            return new ResponseEntity<>(i.getBytes(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/images/users/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageUser(@PathVariable String name) {
        Image i = imageService.getImageByFileName(name);
        if (i != null) {
            return new ResponseEntity<>(i.getBytes(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}