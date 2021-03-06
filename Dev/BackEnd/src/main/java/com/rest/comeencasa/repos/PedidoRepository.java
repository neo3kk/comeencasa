package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findPedidoByUsuario(Usuario usuario);

    Pedido findPedidoByUsuarioAndEstado(Usuario usuario, String estado);

    Pedido findPedidoById(long id);
}

