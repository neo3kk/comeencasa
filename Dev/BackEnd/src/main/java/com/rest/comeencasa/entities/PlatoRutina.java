package com.rest.comeencasa.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "plato_rutina")
public class PlatoRutina implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "platorutina_rutina_id"), name = "platorutina_rutina_id")
    Rutina rutina;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "platorutina_plato_id"), name = "platorutina_plato_id")
    Plato plato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }
}
