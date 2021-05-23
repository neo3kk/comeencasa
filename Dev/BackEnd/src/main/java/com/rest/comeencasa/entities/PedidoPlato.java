package com.rest.comeencasa.entities;

import javax.persistence.*;

@Entity
@Table(name = "pedido_plato")
public class PedidoPlato {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "pedidoplato_pedido_id"), name = "pedidoplato_pedido_id")
    Pedido pedido;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "pedidoplato_plato_id"), name = "pedidoplato_plato_id")
    Plato plato;


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

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }
}

