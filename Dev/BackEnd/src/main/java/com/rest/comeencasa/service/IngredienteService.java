package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.entities.IngredienteDTO;

import java.util.List;

public interface IngredienteService {
   boolean addIngrediente(String s, String ingrediente);
   Ingrediente buscarIngrediente(String ingrediente);
   List<Ingrediente> ingredientes();
   IngredienteDTO makeIngredienteDTO(Ingrediente ingrediente);
   List<IngredienteDTO> createListIngredienteDTO(List<Ingrediente> ingredientes);
   Ingrediente findIngredienteById(Long id);
}
