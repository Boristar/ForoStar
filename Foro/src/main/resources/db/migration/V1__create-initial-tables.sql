-- Creación de la tabla de usuarios
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(300) NOT NULL,
    PRIMARY KEY (id)
);

-- Creación de la tabla de cursos
CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- Creación de la tabla de tópicos
CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL UNIQUE,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL,
    autor_id BIGINT,
    curso_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Insertar datos de prueba
-- El usuario es 'admin' y la contraseña es '1234' (ya encriptada con BCrypt)
INSERT INTO usuarios (login, clave) VALUES ('admin', '$2a$10$jPUU38fh6tOdmGUU/2Iiju6nGPBNaTdnfIhZGCq642r6/TpeS1n9G');

-- Insertar un curso de prueba
INSERT INTO cursos (nombre, categoria) VALUES ('Spring Boot', 'Programacion');