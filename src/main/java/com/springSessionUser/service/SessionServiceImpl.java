package com.springSessionUser.service;

import com.springSessionUser.entity.Session;
import com.springSessionUser.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SessionServiceImpl implements SessionService{


    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public List<Session> sessionByUsuarioId(Long usuarioId) {
        List<Session> sessions = sessionRepository.findByUsuarioId(usuarioId);
        return sessions;
    }


    @Override
    public List<Session> findSessionsAll() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions;
    }

    @Override
    public Session getSession(Long id) {
        return sessionRepository.findById(id).orElse(null);

    }


    @Override
    public Session createSession(Session session) {

        return sessionRepository.save(session);
    }

    @Override
    public Session updateSession(Session session) {
        Session sessionDB = sessionRepository.findById(session.getIdSession()).orElse(null);
        if (null == sessionDB){
            return null;
        }
        sessionDB.setUsuarioId(session.getUsuarioId());
        sessionDB.setFechaIngreso(session.getFechaIngreso());
        sessionDB.setFechaCierre(session.getFechaCierre());
        sessionDB.setIdSession(session.getIdSession());
        return sessionRepository.save(session);
    }
}
