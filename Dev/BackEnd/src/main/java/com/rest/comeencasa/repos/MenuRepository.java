package com.rest.comeencasa.repos;


import com.rest.comeencasa.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findMenuById(Long id);
}