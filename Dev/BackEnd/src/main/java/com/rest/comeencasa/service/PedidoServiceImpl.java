package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PedidoDTO;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.PedidoRepository;
import com.rest.comeencasa.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public List<PedidoDTO> createListpedidoDTO(List<Pedido> pedidos) {
        List<PedidoDTO> listDto = new ArrayList<>();
        pedidos.forEach(pedido -> {
            PedidoDTO pedidoDTO = makePedidoDto(pedido,pedido.getUsuario());
            listDto.add(pedidoDTO);
        });
        return listDto;
    }

    @Override
    public PedidoDTO makePedidoDto(Pedido pedido, Usuario user) {
       PedidoDTO pedidoDTO = new PedidoDTO();
       if (pedido != null){
           pedidoDTO.setEstado(pedido.getEstado());
           pedidoDTO.setId(pedido.getId());
           pedidoDTO.setUsuario(pedido.getUsuario().getEmail());
           pedidoDTO.setFecha_pedido(pedido.getFecha_pedido());
           pedidoDTO.setUbicacion(pedido.getUbicacion());
           pedidoDTO.setPrecio_final(pedido.getPrecio_final());
       }else{
           pedido = new Pedido();
           pedido.setEstado("Pendiente");
           pedido.setUsuario(user);
           pedidoRepository.save(pedido);
       }
       return pedidoDTO;
    }

    @Override
    public List<Pedido> findByUsuario(Usuario usuario) {
        List<Pedido> listPedido = pedidoRepository.findPedidoByUsuario(usuario);
        return listPedido;
    }

    @Override
    public Pedido findPedidoByUsuarioAndEstado(Usuario usuario, String estado) {
        Pedido pedido = pedidoRepository.findPedidoByUsuarioAndEstado(usuario,estado);
        return pedido;
    }

    @Override
    public Pedido findPedidoById(long id) {
        Pedido pedido = pedidoRepository.findPedidoById(id);
        return pedido;
    }


    @Override
    public Pedido updatePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void savePedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

}
