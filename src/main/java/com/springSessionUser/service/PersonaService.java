package com.springSessionUser.service;

import com.springSessionUser.entity.Persona;
import com.springSessionUser.entity.Usuario;

import java.util.List;

public interface PersonaService {
    public List<Persona> findPersonaAll();
    public Persona findByIdentificacion(String identificacion);
    public Persona createPersona(Persona persona);
}
