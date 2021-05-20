package com.rest.comeencasa.entities;


import java.util.List;
import java.util.Objects;

public class PedidoDTO{

    private Long id;
    String fecha_pedido;
    String ubicacion;
    String precio_final;
    String usuario;

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoDTO pedidoDTO = (PedidoDTO) o;
        return Objects.equals(id, pedidoDTO.id) && Objects.equals(fecha_pedido, pedidoDTO.fecha_pedido) && Objects.equals(ubicacion, pedidoDTO.ubicacion) && Objects.equals(precio_final, pedidoDTO.precio_final) && Objects.equals(usuario, pedidoDTO.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha_pedido, ubicacion, precio_final, usuario);
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", fecha_pedido='" + fecha_pedido + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", precio_final='" + precio_final + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
