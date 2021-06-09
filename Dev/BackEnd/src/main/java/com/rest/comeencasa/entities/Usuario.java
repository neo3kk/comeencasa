package com.rest.comeencasa.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class Usuario{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="avatarUrl", columnDefinition = "TEXT")
    private String avatarUrl;

    @Column(name ="email", columnDefinition = "TEXT")
    String email = null;

    @Column(name ="password", columnDefinition = "TEXT")
    String password = null;

    @Column(name ="name", columnDefinition = "TEXT")
    String name = null;

    @Column(name ="last_name", columnDefinition = "TEXT")
    String last_name = null;

    @Column(name ="codigo_postal", columnDefinition = "TEXT")
    String codigo_postal = "";

    @Column(name ="calle", columnDefinition = "TEXT")
    String calle = "";

    @Column(name ="numero", columnDefinition = "TEXT")
    String numero = "";

    @Column(name ="letra", columnDefinition = "TEXT")
    String letra = "";


    @Column(name = "oauth")
    boolean oauth;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AlergenosUsuario> alergenosUsuarios;

    public List<AlergenosUsuario> getAlergenosUsuarios() {
        return alergenosUsuarios;
    }

    public void setAlergenosUsuarios(List<AlergenosUsuario> alergenosUsuarios) {
        this.alergenosUsuarios = alergenosUsuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public boolean isOauth() {
        return oauth;
    }

    public void setOauth(boolean oauth) {
        this.oauth = oauth;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return oauth == usuario.oauth && Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(password, usuario.password) && Objects.equals(name, usuario.name) && Objects.equals(pedidos, usuario.pedidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, oauth, pedidos);
    }
}
