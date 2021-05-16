package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {


}

