package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "alergeno")
public class Alergeno implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;

    @OneToMany(mappedBy = "alergeno", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AlergenosUsuario> alergenosUsuarios;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alergeno alergeno = (Alergeno) o;
        return Objects.equals(id, alergeno.id) && Objects.equals(name, alergeno.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Alergeno{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
