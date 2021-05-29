package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

public class MenuDTO {
    private Long id;
    String nombre_menu;
    String fecha_menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_menu() {
        return nombre_menu;
    }

    public void setNombre_menu(String nombre_menu) {
        this.nombre_menu = nombre_menu;
    }

    public String getFecha_menu() {
        return fecha_menu;
    }

    public void setFecha_menu(String fecha_menu) {
        this.fecha_menu = fecha_menu;
    }
}
