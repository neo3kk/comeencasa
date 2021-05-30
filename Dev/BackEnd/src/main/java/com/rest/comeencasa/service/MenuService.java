package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.entities.MenuDTO;
import com.rest.comeencasa.entities.PedidoPlato;

import java.util.List;

public interface MenuService {
    void saveMenu(Menu menu);
    Menu findById(Long id);
    List<MenuDTO> creatListMenu(List<Menu> menus);
    MenuDTO makeMenuDto(Menu menu);
}
