package com.rest.comeencasa.entities;

import java.util.Objects;

public class PlatoDTO {
    private Long id;
    private String nombre;
    private String precio;
    private String traduccion;
    private String description;
    private Boolean visible;
    private String tipo_de_plato;
    private double energia;
    private double azucar;
    private double grasas;
    private double proteinas;
    private String imageUrl;


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

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getTipo_de_plato() {
        return tipo_de_plato;
    }

    public void setTipo_de_plato(String tipo_de_plato) {
        this.tipo_de_plato = tipo_de_plato;
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = energia;
    }

    public double getAzucar() {
        return azucar;
    }

    public void setAzucar(double azucar) {
        this.azucar = azucar;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
