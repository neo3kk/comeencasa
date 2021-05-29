package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.entities.PedidoPlato;

public interface MenuService {
    void saveMenu(Menu menu);

    Menu findById(Long id);
}
