package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Ingrediente;
import com.rest.comeencasa.entities.IngredienteDTO;
import com.rest.comeencasa.repos.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredienteServiceImpl implements IngredienteService {
    @Autowired
    IngredienteRepository ingredienteRepository;

    @Override
    public boolean addIngrediente(String traduccion, String ingrediente, double energy, double sugar, double fsat, double prot) {
        Ingrediente in = buscarIngrediente(ingrediente);
        if (in == null) {
            in = new Ingrediente();
            in.setName(ingrediente);
            in.setTraduccion(traduccion);
            in.setEnergia(energy);
            in.setAzucar(sugar);
            in.setGrasas(fsat);
            in.setProteinas(prot);
            ingredienteRepository.save(in);
            return true;
        } else {
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

    @Override
    public List<IngredienteDTO> createListIngredienteDTO(List<Ingrediente> ingredientes) {
        List<IngredienteDTO> listDto = new ArrayList<>();
        ingredientes.forEach(ingrediente -> {
            IngredienteDTO ingredienteDTO = makeIngredienteDTO(ingrediente);
            listDto.add(ingredienteDTO);
        });
        return listDto;
    }

    @Override
    public Ingrediente findIngredienteById(Long id) {
        return ingredienteRepository.findIngredienteById(id);
    }

    @Override
    public boolean delete(String ingrediente) {
        Ingrediente in = buscarIngrediente(ingrediente);
        if (in != null) {
            ingredienteRepository.delete(in);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IngredienteDTO makeIngredienteDTO(Ingrediente ingrediente) {
        IngredienteDTO ingredienteDTO = new IngredienteDTO();
        ingredienteDTO.setId(ingrediente.getId());
        ingredienteDTO.setName(ingrediente.getName());
        ingredienteDTO.setTraduccion(ingrediente.getTraduccion());
        ingredienteDTO.setAzucar(ingrediente.getAzucar());
        ingredienteDTO.setEnergia(ingrediente.getEnergia());
        ingredienteDTO.setGrasas(ingrediente.getGrasas());
        ingredienteDTO.setProteinas(ingrediente.getProteinas());
        return ingredienteDTO;
    }
}
