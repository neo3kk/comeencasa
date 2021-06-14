package com.rest.comeencasa.controllers;

import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.service.ImageService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Arrays;

@RestController
public class ImageController {

    @Autowired
    ImageService imageService;


    @PostMapping("/upload/image")
    public HttpEntity<? extends Serializable> uploadFileRegister(@RequestPart(value = "file") MultipartFile uploadfile) {
        try {
            byte[] bytes = Base64.encodeBase64(uploadfile.getBytes());
            return new ResponseEntity<>(bytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("ERROR", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/images/platos/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImagePlato(@PathVariable String name) {
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