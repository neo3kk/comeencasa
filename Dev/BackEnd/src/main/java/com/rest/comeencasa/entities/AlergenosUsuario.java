package com.rest.comeencasa.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name= "alergenos_usuario")
public class AlergenosUsuario implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "alergeno_id"), name = "alergeno_id")
    private Alergeno alergeno;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "usuario_id"), name = "usuario_id")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alergeno getAlergeno() {
        return alergeno;
    }

    public void setAlergeno(Alergeno alergeno) {
        this.alergeno = alergeno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlergenosUsuario that = (AlergenosUsuario) o;
        return Objects.equals(id, that.id) && Objects.equals(alergeno, that.alergeno) && Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alergeno, usuario);
    }

    @Override
    public String toString() {
        return "alergenosUsuario{" +
                "id=" + id +
                ", alergeno=" + alergeno +
                ", usuario=" + usuario +
                '}';
    }
}
