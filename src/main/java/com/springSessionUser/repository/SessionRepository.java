package com.springSessionUser.repository;

import com.springSessionUser.entity.Session;
import com.springSessionUser.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    public List<Session> findByUsuarioId(Long userId);


}
