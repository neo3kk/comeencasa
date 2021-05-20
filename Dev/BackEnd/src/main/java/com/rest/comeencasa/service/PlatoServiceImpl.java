package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Plato;

import com.rest.comeencasa.entities.PlatoDTO;

import com.rest.comeencasa.repos.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlatoServiceImpl implements PlatoService{
    @Autowired
    PlatoRepository platoRepository;

    @Override
    public List<PlatoDTO> createListplatoDTO(List<Plato> platos) {
        List<PlatoDTO> listDto = new ArrayList<>();
        platos.forEach(plato -> {
            PlatoDTO platoDTO = makePlatoDto(plato);
            listDto.add(platoDTO);
        });
        return listDto;
    }

    @Override
    public PlatoDTO makePlatoDto(Plato plato) {
        PlatoDTO platoDTO = new PlatoDTO();
        platoDTO.setDescription(plato.getDescription());
        platoDTO.setTipo_de_plato(plato.getTipo_de_plato());
        platoDTO.setNombre(plato.getNombre());
        platoDTO.setPrecio(plato.getPrecio());
        return platoDTO;
    }

    @Override
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }
}
