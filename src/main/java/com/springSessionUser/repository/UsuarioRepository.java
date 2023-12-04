package com.springSessionUser.repository;

import com.springSessionUser.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByUserName(String userName);
    public Usuario findByMail(String mail);
    public Usuario findByUserNameOrMail(String userName, String mail);


}
