package com.rest.comeencasa.entities;

import javax.persistence.*;

@Entity
@Table(name = "pedido_menu")
public class PedidoMenu {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "pedidomenu_pedido_id"), name = "pedidomenu_pedido_id")
    Pedido pedido;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "pedidomenu_menu_id"), name = "pedidomenu_menu_id")
    Menu menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
