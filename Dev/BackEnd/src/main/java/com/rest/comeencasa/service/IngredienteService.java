package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.entities.IngredienteDTO;

import java.util.List;

public interface IngredienteService {
   boolean addIngrediente(String traduccion, String ingrediente, double energy, double sugar, double fsat, double prot);
  // boolean updateIngredient(String ingrediente, int energy, int sugar, int fsat, int prot);
   Ingrediente buscarIngrediente(String ingrediente);
   List<Ingrediente> ingredientes();
   IngredienteDTO makeIngredienteDTO(Ingrediente ingrediente);
   List<IngredienteDTO> createListIngredienteDTO(List<Ingrediente> ingredientes);
   Ingrediente findIngredienteById(Long id);
}
