CREATE TABLE Persona (
    id_persona SERIAL PRIMARY KEY,
    nombres VARCHAR(60),
    apellidos VARCHAR(60),
    identificacion VARCHAR(60),
    fecha_nacimiento DATE
);

INSERT INTO Persona (id_persona, nombres, apellidos, identificacion, fecha_nacimiento)
VALUES (1, 'Juan', 'Perez', '1234567890', '1990-01-15');

INSERT INTO Persona (nombres, apellidos, identificacion, fecha_nacimiento)
VALUES ( 'Juan', 'Perez', '1234567890', '1990-01-15');


CREATE TABLE Usuario (
    id_usuario SERIAL PRIMARY KEY,
    user_name VARCHAR(50),
    password VARCHAR(50),
    mail VARCHAR(120),
    session_active CHAR(1),
    persona_id BIGINT,
    status VARCHAR(20),
    FOREIGN KEY (persona_id) REFERENCES Persona(id_persona)
);

INSERT INTO Usuario (user_name, password, mail, session_active, persona_id, status)
VALUES ('juanperez', 'contraseña123', 'juanperez@mail.com', 'N', 1, 'Activo');
INSERT INTO Usuario (user_name, password, mail, session_active, persona_id, status)
VALUES ('pedrin', 'contraseña123', 'pedrin@mail.com', 'Y', 1, 'Activo');


Select * from Usuario;
Select * from Persona;

-- ROLES
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    rol_name VARCHAR(50)
);
INSERT INTO rol (rol_name) VALUES ('Admin');
INSERT INTO rol (rol_name) VALUES ('Normal');
INSERT INTO rol (rol_name) VALUES ('loggin de un usuario');
Select * from rol;

CREATE TABLE rol_usuarios (
    rol_idRol BIGINT,
    usuario_idUsuario BIGINT,
	FOREIGN KEY (rol_idRol) REFERENCES rol(id_rol),
	FOREIGN KEY (usuario_idUsuario) REFERENCES USUARIO(id_usuario)
);
INSERT INTO rol_usuarios (rol_idRol, usuario_idUsuario) VALUES (3,1);



CREATE TABLE rolopciones (
    id_ipcion SERIAL PRIMARY KEY,
    nombre_opcion VARCHAR(50)
);
INSERT INTO rolopciones (nombre_opcion) VALUES ('1');
INSERT INTO rolopciones (nombre_opcion) VALUES ('2');

CREATE TABLE rol_rolOpciones (
    rol_idRol BIGINT,
    rolOpciones_idopcion BIGINT,
	FOREIGN KEY (rol_idRol) REFERENCES rol(id_rol),
	FOREIGN KEY (rolOpciones_idopcion) REFERENCES rolopciones(id_ipcion)
);
INSERT INTO rol_rolOpciones (rol_idRol, rolOpciones_idopcion) VALUES (3,2);


-- SESION
CREATE TABLE sessions (
    fecha_ingreso TIMESTAMP,
    fecha_cierre TIMESTAMP,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES USUARIO(id_usuario)
);
INSERT INTO sessions (fecha_ingreso, fecha_cierre, usuario_id)
VALUES 
    ('2023-12-03 12:00:00'::timestamp, '2023-12-03 17:00:00'::timestamp, 1);
SELECT * FROM sessions;




--crear funcion para ingresar un usuario y una persona
CREATE OR REPLACE FUNCTION crear_usuario_y_persona(
    p_user_name VARCHAR(50),
    p_password VARCHAR(50),
    p_mail VARCHAR(120),
    p_session_active CHAR(1),
    p_nombres VARCHAR(60),
    p_apellidos VARCHAR(60),
    p_identificacion VARCHAR(60),
    p_fecha_nacimiento DATE,
    p_status VARCHAR(20), 
    OUT p_id_usuario BIGINT
)
AS $$
DECLARE
    v_id_persona BIGINT;
BEGIN
    -- Insertar en la tabla Persona
    INSERT INTO Persona (nombres, apellidos, identificacion, fecha_nacimiento)
    VALUES (p_nombres, p_apellidos, p_identificacion, p_fecha_nacimiento)
    RETURNING id_persona INTO v_id_persona;

    -- Insertar en la tabla Usuario
    INSERT INTO Usuario (user_name, password, mail, session_active, persona_id, status)
    VALUES (p_user_name, p_password, p_mail, p_session_active, v_id_persona, p_status)
    RETURNING id_usuario INTO p_id_usuario;
END;
$$ LANGUAGE plpgsql;

--llamar a la funcion
DO $$
DECLARE
    v_id_usuario BIGINT;
BEGIN
    v_id_usuario := (SELECT * FROM crear_usuario_y_persona(
        'nombreUsuario',
        'contraseña123',
        'jpiguavel@mail.com',
        'N',
        'Juan',
        'Piguave Loor',
        '1206505024',
        '2000-01-01',
        'Activo'
    ));

    -- Mostrar el ID del usuario generado
    RAISE NOTICE 'ID de usuario generado: %', v_id_usuario;
END;
$$;
Select * from Usuario;
Select * from Persona;



