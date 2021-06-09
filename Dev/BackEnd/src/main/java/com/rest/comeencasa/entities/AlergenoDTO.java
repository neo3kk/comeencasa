package com.rest.comeencasa.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


public class AlergenoDTO implements Serializable {


    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
