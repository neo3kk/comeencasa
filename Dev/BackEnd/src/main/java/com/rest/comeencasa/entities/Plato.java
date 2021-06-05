package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plato")
public class Plato implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nombre", columnDefinition = "TEXT")
    String nombre;

    @Column(name ="traduccion", columnDefinition = "TEXT")
    String traduccion;

    @Column(name ="description", columnDefinition = "TEXT")
    String description;

    @Column(name ="precio", columnDefinition = "TEXT")
    String precio;

    @Column(name = "tipo_de_plato", columnDefinition = "TEXT")
    String tipo_de_plato;

    @Column(name = "visible", columnDefinition = "TEXT")
    boolean visible;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoMenu> platoMenu;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PedidoPlato> pedidoPlato;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoIngrediente> PlatoIngrediente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addChild(PlatoIngrediente aSon)
    {
        this.PlatoIngrediente.add(aSon);
    }

    public void removeChild(PlatoIngrediente aSon)
    {
        this.PlatoIngrediente.remove(aSon);
    }
    public void clear()
    {
        this.PlatoIngrediente.clear();;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTraduccion() { return traduccion; }

    public void setTraduccion(String traduccion) { this.traduccion = traduccion; }

    public String getDescription() {
        return description;
    }

    public List<PedidoPlato> getPedidoPlato() {
        return pedidoPlato;
    }

    public void setPedidoPlato(List<PedidoPlato> pedidoPlato) {
        this.pedidoPlato = pedidoPlato;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTipo_de_plato() {
        return tipo_de_plato;
    }

    public void setTipo_de_plato(String tipo_de_plato) {
        this.tipo_de_plato = tipo_de_plato;
    }

    public List<PlatoMenu> getPlatoMenu() {
        return platoMenu;
    }

    public void setPlatoMenu(List<PlatoMenu> platoMenu) {
        this.platoMenu = platoMenu;
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
        Plato plato = (Plato) o;
        return Objects.equals(id, plato.id) && Objects.equals(nombre, plato.nombre) && Objects.equals(description, plato.description) && Objects.equals(precio, plato.precio) && Objects.equals(tipo_de_plato, plato.tipo_de_plato) && Objects.equals(platoMenu, plato.platoMenu) && Objects.equals(PlatoIngrediente, plato.PlatoIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, description, precio, tipo_de_plato, platoMenu, PlatoIngrediente);
    }

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", description='" + description + '\'' +
                ", precio='" + precio + '\'' +
                ", tipo_de_plato='" + tipo_de_plato + '\'' +
                ", platoMenu=" + platoMenu +
                ", PlatoIngrediente=" + PlatoIngrediente +
                '}';
    }
}
