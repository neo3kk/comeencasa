package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Pedido;
import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.entities.PlatoDTO;
import com.rest.comeencasa.entities.PlatoIngrediente;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<PlatoDTO> createListplatoDTO(List<Plato> platos);
    PlatoDTO makePlatoDto(Plato plato);
    List<Plato> findAll();
    Plato findPlatoById(Long id);
    void save(Plato plato);

    Plato findPlatoByName(String nombre);
    void deletePlato(Plato plato);
    boolean exists(String nombre);
}
