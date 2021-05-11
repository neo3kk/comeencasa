package com.rest.vue.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plato_ingrediente")
public class PlatoIngrediente implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "platoingrediente_plato_id"), name = "platoingrediente_plato_id")
    Plato plato;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "platoingrediente_ingrediente_id"), name = "platoingrediente_ingrediente_id")
    Ingrediente ingrediente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
