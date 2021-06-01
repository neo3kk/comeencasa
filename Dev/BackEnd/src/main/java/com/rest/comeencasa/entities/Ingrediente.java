package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ingrediente")
public class Ingrediente implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "nombre")
    private String name;

    @Column(name= "traduccion")
    private String traduccion;



    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoIngrediente> PlatoIngrediente;

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

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public List<com.rest.comeencasa.entities.PlatoIngrediente> getPlatoIngrediente() {
        return PlatoIngrediente;
    }

    public void setPlatoIngrediente(List<com.rest.comeencasa.entities.PlatoIngrediente> platoIngrediente) {
        PlatoIngrediente = platoIngrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente that = (Ingrediente) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(traduccion, that.traduccion) && Objects.equals(PlatoIngrediente, that.PlatoIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, traduccion, PlatoIngrediente);
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", traduccion='" + traduccion + '\'' +
                ", PlatoIngrediente=" + PlatoIngrediente +
                '}';
    }
}
