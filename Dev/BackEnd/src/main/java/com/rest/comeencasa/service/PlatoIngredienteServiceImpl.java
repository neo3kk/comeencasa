package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.PlatoIngrediente;
import com.rest.comeencasa.repos.PlatoIngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlatoIngredienteServiceImpl implements PlatoIngredienteService{
    @Autowired
    PlatoIngredienteRepository platoIngredienteRepository;

    @Override
    public void save(PlatoIngrediente platoIngrediente) {
        platoIngredienteRepository.save(platoIngrediente);
    }
}
