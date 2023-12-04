package com.springSessionUser.controller;

import com.springSessionUser.entity.Session;
import com.springSessionUser.entity.Usuario;
import com.springSessionUser.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<Session>> listarSessiones( ) {
        List<Session> sessions =  new ArrayList<>();
        sessions = sessionService.findSessionsAll();
        return  ResponseEntity.ok(sessions);
    }
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<Session>> findByUsuarioId(@PathVariable("id") long usuarioId ) {
        List<Session> sessions =  sessionService.sessionByUsuarioId(usuarioId);
        return  ResponseEntity.ok(sessions);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Session> findById(@PathVariable("id") Long id) {
        Session session =  sessionService.getSession(id);
        if (null==session){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    @PostMapping
    public ResponseEntity<Session> createSession( @RequestBody Session session){

        Session sessionCreate =  sessionService.createSession(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionCreate);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable("id") Long id, @RequestBody Session session){
        session.setIdSession(id);
        Session sessionDB =  sessionService.updateSession(session);
        if (sessionDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sessionDB);
    }

}
