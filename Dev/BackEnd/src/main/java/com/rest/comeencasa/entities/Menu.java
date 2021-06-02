package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.event.internal.OnUpdateVisitor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="nombre_menu", columnDefinition = "TEXT")
    String nombre_menu;

    @Column(name ="fecha_menu", columnDefinition = "TEXT")
    String fecha_menu;


    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PedidoMenu> pedidoMenus;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoMenu> platoMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha_menu() {
        return fecha_menu;
    }

    public void setFecha_menu(String fecha_menu) {
        this.fecha_menu = fecha_menu;
    }

    public String getNombre_menu() {
        return nombre_menu;
    }

    public void setNombre_menu(String nombre_menu) {
        this.nombre_menu = nombre_menu;
    }

    public List<PedidoMenu> getPedidoMenus() {
        return pedidoMenus;
    }

    public void setPedidoMenus(List<PedidoMenu> pedidoMenus) {
        this.pedidoMenus = pedidoMenus;
    }

    public List<PlatoMenu> getPlatoMenu() {
        return platoMenu;
    }

    public void setPlatoMenu(List<PlatoMenu> platoMenu) {
        this.platoMenu = platoMenu;
    }
}
