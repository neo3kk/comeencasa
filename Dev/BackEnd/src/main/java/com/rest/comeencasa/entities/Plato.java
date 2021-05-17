package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "plato")
public class Plato implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nombre", columnDefinition = "TEXT")
    String nombre;

    @Column(name ="precio", columnDefinition = "TEXT")
    String precio;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoMenu> platoMenu;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoIngrediente> PlatoIngrediente;

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

    public List<PlatoMenu> getPlatoRutinas() {
        return platoMenu;
    }

    public void setPlatoRutinas(List<PlatoMenu> platoRutinas) {
        this.platoMenu = platoRutinas;
    }

    public List<com.rest.comeencasa.entities.PlatoIngrediente> getPlatoIngrediente() {
        return PlatoIngrediente;
    }

    public void setPlatoIngrediente(List<com.rest.comeencasa.entities.PlatoIngrediente> platoIngrediente) {
        PlatoIngrediente = platoIngrediente;
    }
}
