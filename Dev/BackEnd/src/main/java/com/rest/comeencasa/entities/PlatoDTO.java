package com.rest.comeencasa.entities;

import java.util.Objects;

public class PlatoDTO {
    private Long id;
    String nombre;
    String precio;
    String description;
    String tipo_de_plato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTipo_de_plato() {
        return tipo_de_plato;
    }

    public void setTipo_de_plato(String tipo_de_plato) {
        this.tipo_de_plato = tipo_de_plato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatoDTO platoDTO = (PlatoDTO) o;
        return Objects.equals(id, platoDTO.id) && Objects.equals(nombre, platoDTO.nombre) && Objects.equals(precio, platoDTO.precio) && Objects.equals(description, platoDTO.description) && Objects.equals(tipo_de_plato, platoDTO.tipo_de_plato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio, description, tipo_de_plato);
    }

    @Override
    public String toString() {
        return "PlatoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", description='" + description + '\'' +
                ", tipo_de_plato='" + tipo_de_plato + '\'' +
                '}';
    }
}
