package com.springSessionUser.service;

import com.springSessionUser.entity.Persona;
import com.springSessionUser.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService{
    @Autowired
    PersonaRepository personaRepository;
    @Override
    public List<Persona> findPersonaAll() {
        return personaRepository.findAll();
    }

    @Override
    public Persona findByIdentificacion(String identificacion) {
        return personaRepository.findByIdentificacion(identificacion);
    }

    public Persona createPersona(Persona persona) {

        Persona personaDB = personaRepository.findByIdentificacion ( persona.getIdentificacion () );
        if (personaDB != null){
            return  null; // la persona existe
        }

        personaDB = personaRepository.save ( persona );
        return personaDB;
    }

}
