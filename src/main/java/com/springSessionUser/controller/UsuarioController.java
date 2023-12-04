package com.springSessionUser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springSessionUser.entity.LoginRequest;
import com.springSessionUser.entity.Persona;
import com.springSessionUser.entity.RegistroDTO;
import com.springSessionUser.entity.Usuario;
import com.springSessionUser.service.PersonaService;
import com.springSessionUser.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService personaService;


    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios( ) {
        List<Usuario> usuarios =  new ArrayList<>();
        usuarios = usuarioService.findUsuariosAll();
        return  ResponseEntity.ok(usuarios);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegistroDTO registroDTO, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = this.formatMessage(result);
            System.out.println("Error de validación en el registro de usuario: " + errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Usuario userDB = usuarioService.registrarUsuario(registroDTO);

        return  ResponseEntity.status( HttpStatus.CREATED).body(userDB);
    }

    @PostMapping("/crearpersona")
    public ResponseEntity<Persona> crearPersona(@RequestBody Persona persona, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = this.formatMessage(result);
            System.out.println("Error de validación en el registro de usuario: " + errorMessage);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Persona personaDB = personaService.createPersona(persona);

        return  ResponseEntity.status( HttpStatus.CREATED).body(personaDB);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String userNameOrMail = loginRequest.getUserName() != null
                ? loginRequest.getUserName()
                : loginRequest.getMail();
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam Long usuarioId) {
        // Implementar lógica de cierre de sesión
        // Retorna un mensaje de éxito
        return null;
    }




    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
