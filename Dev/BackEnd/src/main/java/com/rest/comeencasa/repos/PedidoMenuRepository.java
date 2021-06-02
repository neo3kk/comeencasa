package com.rest.comeencasa.repos;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoMenu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoMenuRepository extends JpaRepository<PedidoMenu, Long> {
    PedidoMenu findPedidoMenuByPedido(Pedido pedido);
    PedidoMenu findPedidoMenuByMenu(Menu menu);
}
