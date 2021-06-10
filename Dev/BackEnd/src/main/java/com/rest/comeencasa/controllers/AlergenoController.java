package com.rest.comeencasa.controllers;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rest.comeencasa.entities.Alergeno;
import com.rest.comeencasa.entities.AlergenoDTO;
import com.rest.comeencasa.service.AlergenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AlergenoController {
    Gson gson = new Gson();

    @Autowired
    AlergenoService alergenoService;

    @PostConstruct
    private void postConstruct() throws IOException {


       File resource = new ClassPathResource(
                "alergenos.json").getFile();
        String alergenos = new String(
                Files.readAllBytes(resource.toPath()));

        Type AlergenoListType = new TypeToken<ArrayList<Alergeno>>(){}.getType();

        ArrayList<Alergeno> alergenoArrayList = gson.fromJson(alergenos, AlergenoListType);

        for(Alergeno alergeno : alergenoArrayList) {
            String al = alergeno.getName();
            boolean add = alergenoService.addalergeno(al);
        }
    }

    @GetMapping("/profile/getAllAlergenos")
    public ResponseEntity<String> getAllAlergenos() {
        List<Alergeno> alergenoList = alergenoService.getAlergenos();
        List<AlergenoDTO> alergenoDTOS = alergenoService.makeListAlergenoDTO(alergenoList);
        return new ResponseEntity<>(gson.toJson(alergenoDTOS), HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/admin/addAlergeno")
    public ResponseEntity<String> addAlergeno(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String alergeno = map.get("name");

        boolean add = alergenoService.addalergeno(alergeno);
        if (add) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Transactional
    @DeleteMapping("/admin/deleteAlergeno")
    public ResponseEntity<String> deleteAlergeno(@RequestBody String payload) {
        Map<String, String> map = gson.fromJson(payload, HashMap.class);
        String alergeno = map.get("name");
        boolean del = alergenoService.delete(alergeno);
        if (del) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}