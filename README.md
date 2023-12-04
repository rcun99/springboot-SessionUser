# Session y Register
nota: las validaciones para registrar el usuario funcionan pero se muestran en la consola por falta de tiempo, si alguna validacion detecta que está mal ingresado un campo da internal server error

### Usuarios
GET: 
    http://localhost:8091/usuarios


POST: 
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

### Sessions

all sessions:
GET:  
    http://localhost:8091/session

sessions by userId:
GET:
    http://localhost:8091/session/user/2

session by Id
GET:
    http://localhost:8091/session/1

create session
POST:
    http://localhost:8091/session

    {
      "fechaIngreso": "2023-12-03T18:00:00",
      "fechaCierre": "2023-12-03T19:00:00",
      "usuarioId": 2
    }

uppdate session
PUT:
    http://localhost:8091/session/3
    
    {
      "fechaIngreso": "2023-12-03T20:00:00",
      "fechaCierre": "2023-12-04T00:00:00",
      "usuarioId": 3
    }
        
  
