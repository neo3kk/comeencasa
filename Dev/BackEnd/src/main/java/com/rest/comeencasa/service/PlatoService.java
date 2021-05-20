package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.entities.PlatoDTO;

import java.util.List;

public interface PlatoService {
    List<PlatoDTO> createListplatoDTO(List<Plato> platos);
    PlatoDTO makePlatoDto(Plato plato);
    List<Plato> findAll();
}
