package com.rest.comeencasa.service;


import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoDTO;
import com.rest.comeencasa.entities.Usuario;

import java.util.List;

public interface PedidoService {
    List<Pedido> findAll();

    List<PedidoDTO> createListpedidoDTO(List<Pedido> pedidos);
    PedidoDTO makePedidoDto(Pedido pedido);
    List<Pedido> findByUsuario(Usuario usuario);

}
