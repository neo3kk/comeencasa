package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Ingrediente;

import java.util.List;

public interface IngredienteService {
   boolean addIngrediente(String s, String ingrediente);
   Ingrediente buscarIngrediente(String ingrediente);
   List<Ingrediente> ingredientes();
}
