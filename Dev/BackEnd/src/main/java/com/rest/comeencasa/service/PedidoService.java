package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.PedidoRepository;
import com.rest.comeencasa.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
}
