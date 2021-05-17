package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name ="fecha_menu", columnDefinition = "TEXT")
    String fecha_menu;

    @Column(name = "ubicacion_entrega", columnDefinition = "TEXT")
    String ubicacion;

    @Column(name = "tipo_de_plato", columnDefinition = "TEXT")
    String tipo_de_plato;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "menuPedido_id"), name = "menuPedido_id")
    Pedido pedido;

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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<PlatoMenu> getPlatoMenu() {
        return platoMenu;
    }

    public void setPlatoMenu(List<PlatoMenu> platoMenu) {
        this.platoMenu = platoMenu;
    }
}
