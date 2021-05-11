package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ingrediente")
public class Ingrediente implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="azucar", columnDefinition = "TEXT")
    String azucar;

    @Column(name ="grasas_saturadas", columnDefinition = "TEXT")
    String grasas_saturadas;

    @Column(name ="energia", columnDefinition = "TEXT")
    String energia;


    @Column(name ="proteinas", columnDefinition = "TEXT")
    String proteinas;

    @Column(name ="cafeina", columnDefinition = "TEXT")
    String cafeina;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<PlatoIngrediente> PlatoIngrediente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAzucar() {
        return azucar;
    }

    public void setAzucar(String azucar) {
        this.azucar = azucar;
    }

    public String getGrasas_saturadas() {
        return grasas_saturadas;
    }

    public void setGrasas_saturadas(String grasas_saturadas) {
        this.grasas_saturadas = grasas_saturadas;
    }

    public String getEnergia() {
        return energia;
    }

    public void setEnergia(String energia) {
        this.energia = energia;
    }

    public String getProteinas() {
        return proteinas;
    }

    public void setProteinas(String proteinas) {
        this.proteinas = proteinas;
    }

    public String getCafeina() {
        return cafeina;
    }

    public void setCafeina(String cafeina) {
        this.cafeina = cafeina;
    }

    public List<com.rest.comeencasa.entities.PlatoIngrediente> getPlatoIngrediente() {
        return PlatoIngrediente;
    }

    public void setPlatoIngrediente(List<com.rest.comeencasa.entities.PlatoIngrediente> platoIngrediente) {
        PlatoIngrediente = platoIngrediente;
    }
}
