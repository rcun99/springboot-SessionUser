package com.springSessionUser.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.springSessionUser.entity.Persona;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuario")
public class Usuario {
    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "mail", length = 120)
    private String mail;

    @Column(name = "session_active", length = 1)
    private char sessionActive;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @Column(name = "status", length = 20)
    String status;


}
