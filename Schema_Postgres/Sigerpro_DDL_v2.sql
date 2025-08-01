-- Crear el esquema
CREATE SCHEMA sigerpro_v4;

-- Usar el esquema
SET search_path TO sigerpro_v4;

-- Tabla Usuario
CREATE TABLE Usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(100),
    correo VARCHAR(100) UNIQUE,
    contrasenia_hash VARCHAR(255)
);

-- Tabla Persona
CREATE TABLE Persona (
    id_persona SERIAL PRIMARY KEY,
    nombre VARCHAR(40),
    apellido_p VARCHAR(30),
    apellido_m VARCHAR(30),
    correo VARCHAR(100)
);

-- Tabla Departamento
CREATE TABLE Departamento (
    id_departamento SERIAL PRIMARY KEY,
    nombre_departamento VARCHAR(150)
);

-- Tabla Empresa
CREATE TABLE Empresa (
    id_empresa SERIAL PRIMARY KEY,
    rfc VARCHAR(15) UNIQUE,
    nombre_empresa VARCHAR(200) UNIQUE,
    descripcion VARCHAR(255),
    numero_telefonico VARCHAR(18)
);

-- Tabla Proyecto
CREATE TABLE Proyecto (
    id_proyecto SERIAL PRIMARY KEY,
    nombre_proyecto VARCHAR(254),
    descripcion VARCHAR(150),
    estado VARCHAR(40),
    fecha_registro DATE,
    num_alumnos_sug INT,
    URL_documento VARCHAR(250),
    fecha_inicio DATE,
    fecha_fin DATE,
    id_empresa INT NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);

-- Tabla Expediente
CREATE TABLE Expediente (
    id_expediente SERIAL PRIMARY KEY,
    nombre_expediente VARCHAR(150),
    fecha_creacion DATE
);

-- Tabla Documento
CREATE TABLE Documento (
    id_documento SERIAL PRIMARY KEY,
    nombre_documento VARCHAR(150),
    URL VARCHAR(250),
    fecha_de_carga DATE,
    id_expediente INT,
    FOREIGN KEY (id_expediente) REFERENCES Expediente(id_expediente)
);

-- Tabla Estudiante
CREATE TABLE Estudiante (
    num_control VARCHAR(12) PRIMARY KEY,
    semestre INT NOT NULL,
    id_persona INT UNIQUE NOT NULL,
    id_expediente INT,
    id_proyecto INT,
    id_departamento INT NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona),
    FOREIGN KEY (id_expediente) REFERENCES Expediente(id_expediente),
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto),
    FOREIGN KEY (id_departamento) REFERENCES Departamento(id_departamento)
);

-- Tabla Docente
CREATE TABLE Docente (
    no_tarjeta VARCHAR(20) PRIMARY KEY,
    id_persona INT UNIQUE NOT NULL,
    id_departamento INT NOT NULL,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona),
    FOREIGN KEY (id_departamento) REFERENCES Departamento(id_departamento)
);

-- Tabla debil Proyecto-Docente
CREATE TABLE Docente_proyecto (
    id_proyecto INT NOT NULL,
    no_tarjeta VARCHAR(20) NOT NULL,
    rol_docente VARCHAR(100),
    PRIMARY KEY (id_proyecto, no_tarjeta),
    FOREIGN KEY (id_proyecto) REFERENCES Proyecto(id_proyecto),
    FOREIGN KEY (no_tarjeta) REFERENCES Docente(no_tarjeta)
);