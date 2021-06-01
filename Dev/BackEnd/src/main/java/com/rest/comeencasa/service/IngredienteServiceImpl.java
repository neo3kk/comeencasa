package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.repos.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteServiceImpl implements IngredienteService{
    @Autowired
    IngredienteRepository ingredienteRepository;

    @Override
    public boolean addIngrediente(String s, String ingrediente) {
       Ingrediente in = buscarIngrediente(ingrediente);
       if(in == null){
           in = new Ingrediente();
           in.setName(ingrediente);
           in.setTraduccion(s);
           ingredienteRepository.save(in);
           return true;
       }else {
           return false;
       }
    }

    @Override
    public Ingrediente buscarIngrediente(String ingrediente) {
        Ingrediente in = ingredienteRepository.findIngredienteByName(ingrediente);
        return in;
    }

    @Override
    public List<Ingrediente> ingredientes() {
        List<Ingrediente> ingredienteList = ingredienteRepository.findAll();
        return ingredienteList;
    }
}
