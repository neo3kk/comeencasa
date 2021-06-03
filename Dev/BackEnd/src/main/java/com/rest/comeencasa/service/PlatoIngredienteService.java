package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.entities.PlatoIngrediente;

import java.util.List;

public interface PlatoIngredienteService {
    void save(PlatoIngrediente platoIngrediente);
    List<PlatoIngrediente> findByPlato(Plato plato);
    void delete(PlatoIngrediente platoIngrediente);
}
