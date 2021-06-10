package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepo;

    @Override
    public Image getImageByFileName(String fileName) {
        Image im = imageRepo.findImageByFileName(fileName);
        return im;
    }

    @Override
    public void delete(Image i) {
        imageRepo.delete(i);
    }
}
