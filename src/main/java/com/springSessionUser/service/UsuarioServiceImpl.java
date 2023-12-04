package com.springSessionUser.service;

import com.springSessionUser.entity.Persona;
import com.springSessionUser.entity.RegistroDTO;
import com.springSessionUser.entity.Session;
import com.springSessionUser.entity.Usuario;
import com.springSessionUser.repository.PersonaRepository;
import com.springSessionUser.repository.SessionRepository;
import com.springSessionUser.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PersonaRepository personaRepository;
    private SessionRepository sessionRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Usuario registrarUsuario(RegistroDTO registroDTO) {
        // Validaciones del nombre de usuario
        validarNombreUsuario(registroDTO.getUserName());

        // Validaciones de la contraseña
        validarContrasena(registroDTO.getPassword());

        // Validaciones de la identificación
        validarIdentificacion(registroDTO.getIdentificacion());

        String mailGenerado = genearEmail(registroDTO.getNombres(), registroDTO.getApellidos());

        String mensaje = "";
        //Comprobar si el usuario existe

        Usuario usuarioDB = usuarioRepository.findByUserNameOrMail (registroDTO.getUserName(), mailGenerado);
        if( usuarioDB != null  ){
            return null; // este usuario ya existe
        }

        //Comprobar si la persona existe
        Persona personaDB = personaRepository.findByIdentificacion (registroDTO.getIdentificacion());
        if( personaDB != null ){
            return null; // esta persona ya existe
        }

        // Llamar al procedimiento almacenado
        String sql = "SELECT * FROM crear_usuario_y_persona(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Long  idCreado = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                registroDTO.getUserName(),
                registroDTO.getPassword(),
                mailGenerado,
                registroDTO.getSessionActive(),
                registroDTO.getNombres(),
                registroDTO.getApellidos(),
                registroDTO.getIdentificacion(),
                registroDTO.getFechaNacimiento(),
                registroDTO.getStatus()
        );


            usuarioDB = usuarioRepository.findByUserName ( mailGenerado );
            return usuarioDB;




    }

    private String genearEmail(String nombres, String apellidos) {
        // Construir el correo electrónico con base en nombres y apellidos
        String nombresAux = nombres.toLowerCase().replaceAll("\\s+", "");
        String apellidosAux = apellidos.toLowerCase().replaceAll("\\s+", "");
        String correoGenerado = nombresAux.charAt(0) + apellidosAux + "@mail.com";

        // Verificar si el correo ya existe en la tabla Usuario
        String correoFinal = verificarCorreoExistente(correoGenerado);

        return correoFinal;
    }
    private String verificarCorreoExistente(String correo) {
        // Verificar si el correo ya existe en la tabla Usuario
        int contador = 1;
        String correoFinal = correo;
        while (correoExistente(correoFinal)) {
            contador++;
            correoFinal = correo.substring(0, correo.indexOf('@')) + contador + correo.substring(correo.indexOf('@'));
        }
        return correoFinal;
    }

    private boolean correoExistente(String correo) {
        Usuario usuario = usuarioRepository.findByMail(correo);
        if (usuario != null)
            return true;
        else
            return false;
    }

    @Override
    public Usuario iniciarSesion(String userName, String email, String password) {

        // Obtén el usuario por nombre de usuario o correo
        Usuario usuario = usuarioRepository.findByUserNameOrMail(userName, email);


        // Verifica la contraseña y otras lógicas de inicio de sesión
        if(usuario == null)
            return null;
        if(!Objects.equals(usuario.getPassword(), password)){
            switch (usuario.getStatus()){
                case "3 intentos":
                    usuario.setStatus("2 intentos");
                    break;
                case "2 intentos":
                    usuario.setStatus("1 intentos");
                    break;
                case "1 intentos":
                    usuario.setStatus("bloqueado");
            }
        }
        else {
            usuario.setStatus("3 intentos");
        }

        // si se intenta iniciar sesion 3 veces y es fallido el usuario pasa a estado bloqueado
        if(usuario.getStatus() == "bloqueado"){
            return null;
        }


        // Crea una nueva sesión
        Session nuevaSesion = new Session();
        nuevaSesion.setFechaIngreso(LocalDate.from(LocalDateTime.now()));
        Long usuarioId = usuarioRepository.findByMail(email).getIdUsuario();
        nuevaSesion.setUsuarioId(usuarioId);

        // Agrega la sesión al usuario
        sessionRepository.save(nuevaSesion);

        // Actualiza la entidad usuario en la base de datos
        usuarioRepository.save(usuario);

        // Retorna el usuario y el token de sesión
        return usuario;
    }

    @Override
    public void cerrarSesion(Long usuarioId) {
        // Implementar lógica de cierre de sesión
        // ...
    }


    @Override
    public List<Usuario> findUsuariosAll() {
        return usuarioRepository.findAll();
    }


    private void validarNombreUsuario(String nombreUsuario) {
        // a. No contener signos
        if (!nombreUsuario.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("El nombre de usuario no puede contener signos.");
        }

        // b. No debe estar duplicado
        if (usuarioRepository.findByUserName(nombreUsuario) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado.");
        }

        // c. Al menos un número
        if (!nombreUsuario.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre de usuario debe contener al menos un número.");
        }

        // d. Al menos una letra mayúscula
        if (!nombreUsuario.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("El nombre de usuario debe contener al menos una letra mayúscula.");
        }

        // e. Longitud máxima de 20 dígitos y mínima de 8 dígitos
        if (nombreUsuario.length() < 8 || nombreUsuario.length() > 20) {
            throw new IllegalArgumentException("El nombre de usuario debe tener entre 8 y 20 caracteres.");
        }
    }

    private void validarContrasena(String contrasena) {
        // a. Debe tener al menos 8 dígitos
        if (contrasena.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }

        // b. Debe tener al menos una letra mayúscula
        if (!contrasena.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula.");
        }

        // c. No debe contener espacios
        if (contrasena.contains(" ")) {
            throw new IllegalArgumentException("La contraseña no puede contener espacios.");
        }

        // d. Debe tener al menos un signo (caracter especial)
        if (!contrasena.matches(".*[^a-zA-Z0-9].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un carácter especial.");
        }
    }

    private void validarIdentificacion(String identificacion) {
        // a. Debe tener 10 dígitos
        if (identificacion.length() != 10) {
            throw new IllegalArgumentException("La identificación debe tener 10 dígitos.");
        }

        // b. Solo números
        if (!identificacion.matches("\\d+")) {
            throw new IllegalArgumentException("La identificación debe contener solo números.");
        }

        // c. Validar que no tenga seguido 4 veces seguidas un número (Ejemplo: 1008888471)
        if (identificacion.matches(".*(\\d)\\1{3,}.*")) {
            throw new IllegalArgumentException("La identificación no puede tener 4 números iguales seguidos.");
        }
    }
}
