package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Image;
import com.rest.comeencasa.entities.Plato;

import com.rest.comeencasa.entities.PlatoDTO;

import com.rest.comeencasa.repos.ImageRepository;
import com.rest.comeencasa.repos.PlatoRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService{
    @Autowired
    PlatoRepository platoRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;

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
        platoDTO.setId(plato.getId());
        platoDTO.setTraduccion(plato.getTraduccion());
        platoDTO.setVisible(plato.isVisible());
        platoDTO.setAzucar(plato.getAzucar());
        platoDTO.setEnergia(plato.getEnergia());
        platoDTO.setGrasas(plato.getGrasas());
        platoDTO.setProteinas(plato.getProteinas());
        platoDTO.setImageUrl(plato.getImageUrl());
        return platoDTO;
    }

    @Override
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }

    @Override
    public void save(Plato plato){
        platoRepository.save(plato);
    }

    @Override
    public Plato findPlatoByName(String nombre) {
        return platoRepository.findPlatoByNombre(nombre);
    }

    public boolean exists(String nombre) {
        if (platoRepository.findPlatoByNombre(nombre) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String processAvatar(String file, String nombre) {
        if (file != null) {
            byte[] bytes = Base64.decodeBase64(file);
            Image image = new Image();
            image.setBytes(bytes);
            image.setFileName(nombre + "profile.png");
            Image i = imageService.getImageByFileName(nombre+"profile.png");
            if(i != null){
                imageService.delete(i);
            }
            imageRepository.save(image);
            return image.getFileName();
        }
        return null;
    }

    @Override
    public void deletePlato(Plato plato) {
        platoRepository.delete(plato);
    }

    @Override
    public Plato findPlatoById(Long id) {
        Plato plato = platoRepository.findAllById(id);
        if (plato!=null){
            return plato;
        }
        return null;
    }
}
