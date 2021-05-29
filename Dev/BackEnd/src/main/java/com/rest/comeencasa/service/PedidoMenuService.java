package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoMenu;


public interface PedidoMenuService {
    void savePedidoMenu(PedidoMenu pedidoMenu);
    PedidoMenu getPedidoMenuByPedido(Pedido pedido);
}
