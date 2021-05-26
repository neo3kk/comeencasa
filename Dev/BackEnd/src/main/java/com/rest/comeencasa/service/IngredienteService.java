package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.entities.Plato;
import com.rest.comeencasa.entities.PlatoDTO;

import java.util.List;

public interface IngredienteService {
   boolean addIngrediente(String ingrediente);
   Ingrediente buscarIngrediente(String ingrediente);
   List<Ingrediente> ingredientes();
}
