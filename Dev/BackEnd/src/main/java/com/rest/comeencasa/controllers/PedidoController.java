package com.rest.comeencasa.controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.rest.comeencasa.entities.*;
import com.rest.comeencasa.service.*;
import com.rest.comeencasa.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class PedidoController {
    Gson gson = new Gson();

    @Autowired
    UserService userService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    PedidoMenuService pedidoMenuService;

    @Autowired
    PlatoService platoService;

    @Autowired
    MenuService menuService;

    @Autowired
    PedidoPlatoService pedidoPlatoService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PlatoMenuService platoMenuService;

    @Autowired
    LoginServiceOauth loginServiceOauth;


    @PostMapping("/pedidos")
    public ResponseEntity<String> getAllByUser(@RequestHeader("Authorization") String auth) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            ;
            if (validate != null) {
                user = userService.getUserByEmail(validate);
            }
        }
        if (user != null) {
            List<Pedido> pedidos = pedidoService.findByUsuario(user);
            List<PedidoDTO> pedidoDTOList = pedidoService.createListpedidoDTO(pedidos);
            return new ResponseEntity<>(gson.toJson(pedidoDTOList), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/pedidos")
    public ResponseEntity<String> createPedido(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            if (validate != null) {
                user = userService.getUserByEmail(validate);
                Map<String, String> map = gson.fromJson(payload, HashMap.class);
                String ubicacion = map.get("ubicacion");
                String precio_final = map.get("precio_final");
                String estado = map.get("estado");
                Pedido pedido = new Pedido();
                pedido.setFecha_pedido(utils.getToday());
                pedido.setUbicacion(ubicacion);
                pedido.setEstado(estado);
                pedido.setUsuario(user);
                pedido.setPrecio_final(precio_final);
                pedidoService.makePedidoDto(pedido, user);
                return new ResponseEntity<>("Se ha realizado el pedido correctamente", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updatePedido")
    public ResponseEntity<String> updatePedido(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            if (validate != null) {
                user = userService.getUserByEmail(validate);
                Map<String, String> map = gson.fromJson(payload, HashMap.class);
                String precio_final = map.get("precio_final");
                String estado = map.get("estado");
                Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, estado);
                pedido.setFecha_pedido(utils.getToday());
                pedido.setEstado(estado);
                pedido.setPrecio_final(precio_final);
                pedidoService.updatePedido(pedido);
                return new ResponseEntity<>("Se ha modificado el pedido correctamente", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getOpenPedido")
    public ResponseEntity<String> getOpenPedido(@RequestHeader("Authorization") String auth) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            if (validate != null) {
                user = userService.getUserByEmail(validate);
                String estado = "Pendiente";
                Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, estado);
                PedidoDTO pedidoDTO = pedidoService.makePedidoDto(pedido, user);
                return new ResponseEntity<>(gson.toJson(pedidoDTO), HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addPlatoPedido")
    public ResponseEntity<String> addPlatoPedido(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            String validate = tokenService.verifyToken(token);
            Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
            if (userDetails.get("email") != null) {
                validate = userDetails.get("email");
            }
            if (validate != null) {
                user = userService.getUserByEmail(validate);
                Map<String, String> map = gson.fromJson(payload, HashMap.class);
                System.out.println(map);
                String object = map.get("plato_id");
                Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
                if (pedido != null) {
                    Plato plato = platoService.findPlatoById(Long.valueOf(object));
                    PedidoPlato pedidoPlato = new PedidoPlato();
                    pedidoPlato.setPlato(plato);
                    pedidoPlato.setPedido(pedido);
                    pedidoPlatoService.savePedidoPlato(pedidoPlato);
                } else {
                    pedido = new Pedido();
                    pedido.setFecha_pedido(utils.getToday());
                    pedido.setUsuario(user);
                    pedido.setFecha_pedido(utils.getToday());
                    pedido.setEstado("Pendiente");
                    pedido.setUsuario(user);
                    pedidoService.savePedido(pedido);
                    Plato plato = platoService.findPlatoById(Long.valueOf(object));
                    PedidoPlato pedidoPlato = new PedidoPlato();
                    pedidoPlato.setPlato(plato);
                    pedidoPlato.setPedido(pedido);
                    pedidoPlatoService.savePedidoPlato(pedidoPlato);
                }
                return new ResponseEntity<>("Se ha añadido el plato correctamente", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/añadirMenu")
    public ResponseEntity<String> añadirMenu(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        validate = userDetails.get("email");

        user = userService.getUserByEmail(validate);
        Map<String, List> map = gson.fromJson(payload, HashMap.class);
        List platos = map.get("platos");
        List<Plato> platosSeleccionados = new ArrayList<>();
        Menu m = new Menu();
        m.setFecha_menu(utils.getToday());
        m.setNombre_menu("Mi nombre");
        menuService.saveMenu(m);

        List<PlatoMenu> platmens = new ArrayList<>();
        platos.forEach(plato -> {
            System.out.println(plato);
            LinkedTreeMap<Object, Object> t = (LinkedTreeMap) plato;
            float idplato = Float.parseFloat(t.get("id").toString());
            Plato platoMenu = platoService.findPlatoById((long) idplato);
            platosSeleccionados.add(platoMenu);
            PlatoMenu platmen = new PlatoMenu();
            platmen.setPlato(platoMenu);
            platmen.setRutina(m);
            platmens.add(platmen);
        });
        m.setPlatoMenu(platmens);
        Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
        PedidoMenu pm = new PedidoMenu();
        pm.setMenu(m);
        pm.setPedido(pedido);
        pedidoMenuService.savePedidoMenu(pm);
        pm = pedidoMenuService.getPedidoMenuByPedido(pedido);

        List<PedidoMenu> pedidoMenus = m.getPedidoMenus();
        if (pedidoMenus == null) {
            pedidoMenus = new ArrayList<>();
            pedidoMenus.add(pm);
        } else {
            pedidoMenus.add(pm);
        }
        m.setPedidoMenus(pedidoMenus);
        menuService.saveMenu(m);
        if (pedido != null) {

        }
        return new ResponseEntity<>("Se ha añadido el menu correctamente", HttpStatus.ACCEPTED);
    }

    @PostMapping("/guardarMenu")
    public ResponseEntity<String> guardarMenu(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;

        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        validate = userDetails.get("email");

        user = userService.getUserByEmail(validate);
        Map<String, List> map1 = gson.fromJson(payload, HashMap.class);
        Map<String, String> map2 = gson.fromJson(payload, HashMap.class);
        List platos = map1.get("platos");
        String idmenu = map2.get("idmenu");
        List<Plato> platosSeleccionados = new ArrayList<>();
        Menu m = menuService.findById(Long.valueOf(idmenu));
        PedidoMenu pm = pedidoMenuService.findPedidoMenuByMenu(m);

        List<PlatoMenu> platmens = m.getPlatoMenu();
        for (int i = 0; i < platos.size(); i++) {
            System.out.println(platos.get(i));
            LinkedTreeMap<Object, Object> t = (LinkedTreeMap) platos.get(i);
            float idplato = Float.parseFloat(t.get("id").toString());
            Plato platoMenu = platoService.findPlatoById((long) idplato);
            platosSeleccionados.add(platoMenu);
            PlatoMenu platmen = platmens.get(i);
            platmen.setPlato(platoMenu);
            platoMenuService.savePlatoMenu(platmen);
            platmens.add(platmen);

        }
        m.setPlatoMenu(platmens);
        menuService.saveMenu(m);
        Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
        pm.setMenu(m);
        pm.setPedido(pedido);
        pedidoMenuService.savePedidoMenu(pm);
        pm = pedidoMenuService.getPedidoMenuByPedido(pedido);

        return new ResponseEntity<>("Se ha guardado el menu correctamente", HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/deletePlato")
    public ResponseEntity<String> deletePlato(@RequestHeader("Authorization") String auth, @RequestBody String payload) throws Exception {
        Usuario user = null;
        String token = auth.replace("Bearer ", "");
        String validate = tokenService.verifyToken(token);
        Map<String, String> userDetails = loginServiceOauth.getUserDetails(token);
        validate = userDetails.get("email");

        user = userService.getUserByEmail(validate);
        Map<String, Double> map = gson.fromJson(payload, HashMap.class);
        double idplato = map.get("idplato");
        Pedido pedido = pedidoService.findPedidoByUsuarioAndEstado(user, "Pendiente");
        List<PedidoPlato> pedidoPlatos = pedido.getPedidoPlato();
        for (int i = 0; i < pedidoPlatos.size(); i++) {
            if ((double) pedidoPlatos.get(i).getPlato().getId() == idplato) {
                pedidoPlatos.remove(pedidoPlatos.get(i));
            }
        }
        pedido.setPedidoPlato(pedidoPlatos);
        pedidoService.savePedido(pedido);
        return new ResponseEntity<>("Se ha añadido el plato correctamente", HttpStatus.ACCEPTED);

    }

}
