package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.repos.MenuRepository;
import com.rest.comeencasa.repos.PedidoPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceimpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Override
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public Menu findById(Long id) {
        return menuRepository.findMenuById(id);
    }
}
