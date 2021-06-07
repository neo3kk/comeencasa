package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="fecha_pedido", columnDefinition = "TEXT")
    String fecha_pedido;

    @Column(name = "ubicacion_entrega", columnDefinition = "TEXT")
    String ubicacion;

    @Column(name = "precio_final", columnDefinition = "TEXT")
    String precio_final;

    @Column(name = "estado", columnDefinition = "TEXT")
    String estado;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "pedidoUsuario_id"), name = "pedidoUsuario_id")
    Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PedidoMenu> pedidoMenus;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PedidoPlato> pedidoPlato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPrecio_final() {
        return precio_final;
    }

    public void setPrecio_final(String precio_final) {
        this.precio_final = precio_final;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PedidoMenu> getPedidoMenus() {
        return pedidoMenus;
    }

    public void setPedidoMenus(List<PedidoMenu> pedidoMenus) {
        this.pedidoMenus = pedidoMenus;
    }

    public List<PedidoPlato> getPedidoPlato() {
        return pedidoPlato;
    }

    public void setPedidoPlato(List<PedidoPlato> pedidoPlato) {
        this.pedidoPlato = pedidoPlato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) && Objects.equals(fecha_pedido, pedido.fecha_pedido) && Objects.equals(ubicacion, pedido.ubicacion) && Objects.equals(precio_final, pedido.precio_final) && Objects.equals(usuario, pedido.usuario) && Objects.equals(pedidoMenus, pedido.pedidoMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha_pedido, ubicacion, precio_final, usuario, pedidoMenus);
    }


}
