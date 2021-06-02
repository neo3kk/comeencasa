package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Menu;
import com.rest.comeencasa.entities.MenuDTO;
import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.entities.PlatoDTO;
import com.rest.comeencasa.repos.MenuRepository;
import com.rest.comeencasa.repos.PedidoPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MenuDTO> creatListMenu(List<Menu> menus) {
        List<MenuDTO> listDto = new ArrayList<>();
        menus.forEach(menu -> {
            MenuDTO menuDTO = makeMenuDto(menu);
            listDto.add(menuDTO);
        });
        return listDto;
    }

    @Override
    public MenuDTO makeMenuDto(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setFecha_menu(menu.getFecha_menu());
        menuDTO.setNombre_menu(menu.getNombre_menu());
        menuDTO.setId(menu.getId());
        return menuDTO;
    }

}
