package com.rest.comeencasa.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plato_menu")
public class PlatoMenu implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "platomenu_menu_id"), name = "platomenu_menu_id")
    Menu menu;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "platomenu_plato_id"), name = "platomenu_plato_id")
    Plato plato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getRutina() {
        return menu;
    }

    public void setRutina(Menu menu) {
        this.menu = menu;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }
}
