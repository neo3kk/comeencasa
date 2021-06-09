package com.rest.comeencasa.service;

import com.rest.comeencasa.entities.*;

import java.util.List;

public interface AlergenoService {
    boolean addalergeno(String alergeno);

    boolean adddAlergenoUsuario(AlergenosUsuario alergeno);

    Alergeno buscarAlergeno(String alergeno);

    List<Alergeno> getAlergenos();

    boolean delete(String alergeno);
    AlergenoDTO makeAlergenoDTO(Alergeno alergeno);
    List<AlergenoDTO> makeListAlergenoDTO(List<Alergeno> alergenoList);
}
