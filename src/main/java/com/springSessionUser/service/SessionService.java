package com.springSessionUser.service;

import com.springSessionUser.entity.Session;
import com.springSessionUser.entity.Usuario;

import java.util.List;

public interface SessionService {
    public List<Session>  sessionByUsuarioId (Long usuarioId);
    public List<Session> findSessionsAll();
    public Session getSession(Long id);

    public Session createSession(Session session);
    public Session updateSession(Session session);

}
