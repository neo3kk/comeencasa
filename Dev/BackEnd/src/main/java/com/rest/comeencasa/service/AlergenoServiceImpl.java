package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.Alergeno;
import com.rest.comeencasa.entities.AlergenoDTO;
import com.rest.comeencasa.entities.AlergenosUsuario;
import com.rest.comeencasa.entities.Usuario;
import com.rest.comeencasa.repos.AlergenoRepository;
import com.rest.comeencasa.repos.AlergenosUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlergenoServiceImpl implements AlergenoService {
    @Autowired
    AlergenoRepository alergenoRepository;

    @Autowired
    AlergenosUsuarioRepository alergenosUsuarioRepository;

    @Override
    public boolean addalergeno(String alergeno) {
        Alergeno al = buscarAlergeno(alergeno);
        if (al == null) {
            al = new Alergeno();
            al.setName(alergeno);
            alergenoRepository.save(al);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean adddAlergenoUsuario(AlergenosUsuario alergeno) {
        alergenosUsuarioRepository.save(alergeno);
        return true;
    }

    @Override
    public Alergeno buscarAlergeno(String alergeno) {
        Alergeno al = alergenoRepository.findAlergenoByName(alergeno);
        return al;
    }

    @Override
    public List<Alergeno> getAlergenos() {
        List<Alergeno> alergenoList = alergenoRepository.findAll();
        return alergenoList;
    }

    @Override
    public boolean delete(String alergeno) {
        Alergeno al = buscarAlergeno(alergeno);
        if (al != null) {
            alergenoRepository.delete(al);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AlergenoDTO makeAlergenoDTO(Alergeno alergeno) {
        AlergenoDTO alergenoDTO = new AlergenoDTO();
        alergenoDTO.setId(alergeno.getId());
        alergenoDTO.setName(alergeno.getName());
        return alergenoDTO;
    }

    @Override
    public List<AlergenoDTO> makeListAlergenoDTO(List<Alergeno> alergenoList) {
        List<AlergenoDTO> listDto = new ArrayList<>();
        alergenoList.forEach(alergeno -> {
            AlergenoDTO alergenoDTO = makeAlergenoDTO(alergeno);
            listDto.add(alergenoDTO);
        });
        return listDto;
    }

    @Override
    public List<Alergeno> getUserAlergenos(Usuario user) {
        List<AlergenosUsuario> alergenoList = alergenosUsuarioRepository.findAlergenosUsuarioByUsuario(user);
        List<Alergeno> alergenos = new ArrayList<>();
        alergenoList.forEach(alergenosUsuario -> {
            alergenos.add(alergenosUsuario.getAlergeno());
        });
        return alergenos;
    }
}
