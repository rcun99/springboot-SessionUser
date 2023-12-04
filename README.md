# Session y Register
nota: las validaciones para registrar el usuario funcionan pero se muestran en la consola por falta de tiempo, si alguna validacion detecta que está mal ingresado un campo da internal server error

## Usuarios
GET: 
    http://localhost:8091/usuarios

## POST: 
    http://localhost:8091/usuarios/registro
  
    {
    "userName": "Usuariovalido4",
    "password": "Contraseña123!",
    "mail": "correo@example.com",
    "sessionActive": "N",
    "nombres": "Josefo",
    "apellidos": "Cun Banderas",
    "identificacion": "0487654321",
    "fechaNacimiento": "2000-01-01",
    "status": "Activo"
  }
