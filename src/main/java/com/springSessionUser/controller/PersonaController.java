package com.springSessionUser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springSessionUser.entity.LoginRequest;
import com.springSessionUser.entity.Persona;
import com.springSessionUser.entity.Usuario;
import com.springSessionUser.service.PersonaService;
import com.springSessionUser.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;


    // -------------------Retrieve All Usuarios--------------------------------------------

    @GetMapping
    public ResponseEntity<List<Persona>> listarPersonas( ) {
        List<Persona> personas =  new ArrayList<>();
        personas = personaService.findPersonaAll();
        return  ResponseEntity.ok(personas);
    }


}
