package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoMenu;
import com.rest.comeencasa.repos.PedidoMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoMenuServiceImpl implements PedidoMenuService{

    @Autowired
    PedidoMenuRepository pedidoMenuRepository;
    @Override
    public void savePedidoMenu(PedidoMenu pedidoMenu) {
        pedidoMenuRepository.save(pedidoMenu);
    }

    @Override
    public PedidoMenu getPedidoMenuByPedido(Pedido pedido) {
       return pedidoMenuRepository.findPedidoMenuByPedido(pedido);
    }

    @Override
    public PedidoMenu findPedidoMenuByMenu(Menu menu) {
        return pedidoMenuRepository.findPedidoMenuByMenu(menu);
    }
}
