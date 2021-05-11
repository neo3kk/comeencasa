package com.rest.vue.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rutina")
public class Rutina implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="fecha_rutina", columnDefinition = "TEXT")
    String fecha_rutina;

    @Column(name = "ubicacion_entrega", columnDefinition = "TEXT")
    String ubicacion;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "rutinaPedido_id"), name = "rutinaPedido_id")
    Pedido pedido;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoRutina> platoRutinas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha_rutina() {
        return fecha_rutina;
    }

    public void setFecha_rutina(String fecha_rutina) {
        this.fecha_rutina = fecha_rutina;
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

    public List<PlatoRutina> getPlatoRutinas() {
        return platoRutinas;
    }

    public void setPlatoRutinas(List<PlatoRutina> platoRutinas) {
        this.platoRutinas = platoRutinas;
    }
}
