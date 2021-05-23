package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoDTO;
import com.rest.comeencasa.entities.PedidoPlato;
import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.repos.PedidoPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoPlatoServiceImpl implements PedidoPlatoService{
    @Autowired
    PedidoPlatoRepository pedidoPlatoRepository;

    public void savePedidoPlato(Plato plato, Pedido pedido) {
        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setPedido(pedido);
        pedidoPlato.setPlato(plato);
        pedidoPlatoRepository.save(pedidoPlato);
    }


    @Override
    public void savePedidoPlato(PedidoPlato pedidoPlato) {

        pedidoPlatoRepository.save(pedidoPlato);

    }
}
