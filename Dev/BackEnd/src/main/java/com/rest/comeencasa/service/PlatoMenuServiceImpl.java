package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.PlatoMenu;
import com.rest.comeencasa.repos.PlatoMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatoMenuServiceImpl implements PlatoMenuService{
    @Autowired
    PlatoMenuRepository platoMenuRepository;

    @Override
    public void savePlatoMenu(PlatoMenu platoMenu) {
        platoMenuRepository.save(platoMenu);
    }
}
