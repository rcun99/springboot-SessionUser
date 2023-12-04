package com.springSessionUser.repository;

import com.springSessionUser.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    public Persona findByIdentificacion(String identificacion);

}
