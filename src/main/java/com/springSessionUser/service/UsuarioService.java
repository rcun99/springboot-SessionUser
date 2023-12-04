package com.springSessionUser.service;

import com.springSessionUser.entity.Persona;
import com.springSessionUser.entity.RegistroDTO;
import com.springSessionUser.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario registrarUsuario(RegistroDTO registroDTO);
    Usuario iniciarSesion(String userName, String email, String password);
    void cerrarSesion(Long usuarioId);

    public List<Usuario> findUsuariosAll();

}
