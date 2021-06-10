package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Image;

public interface ImageService {
    Image getImageByFileName(String fileName);

    void delete(Image i);
}
