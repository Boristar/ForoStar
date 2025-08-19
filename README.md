# 🚀 Foro API - Challenge Alura Latam

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green?style=for-the-badge&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange?style=for-the-badge&logo=mysql)
![Security](https://img.shields.io/badge/Security-JWT-purple?style=for-the-badge&logo=jsonwebtokens)

Bienvenido a la API REST del Foro, un proyecto desarrollado como parte del Challenge de Back-End de Alura Latam. Esta API robusta y segura permite la gestión completa de un sistema de foros, incluyendo la autenticación de usuarios y la administración de tópicos de discusión.

---

## ✨ Características Principales

*   **API RESTful:** Endpoints bien definidos para operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los tópicos del foro.
*   **Seguridad con JWT:** Autenticación stateless utilizando JSON Web Tokens para proteger los endpoints, asegurando que solo los usuarios autorizados puedan realizar acciones.
*   **Base de Datos Relacional:** Persistencia de datos gestionada con Spring Data JPA y Hibernate sobre una base de datos MySQL.
*   **Migraciones con Flyway:** Creación y versionado del esquema de la base de datos de forma automática, garantizando consistencia en cualquier entorno.
*   **Validaciones:** Uso de `jakarta.validation` para asegurar la integridad y correctitud de los datos de entrada a la API.
*   **Borrado Lógico:** Los tópicos no se eliminan permanentemente, sino que se marcan como inactivos, preservando el historial y permitiendo una fácil recuperación.

---

## 🛠️ Stack Tecnológico

| Componente      | Tecnología                                     |
| --------------- | ---------------------------------------------- |
| **Lenguaje**    | Java 17                                        |
| **Framework**   | Spring Boot 3                                  |
| **Seguridad**   | Spring Security, JWT (Auth0 Java JWT)          |
| **Base de Datos** | Spring Data JPA, Hibernate, Flyway, MySQL      |
| **Build Tool**  | Maven                                          |
| **Pruebas**     | Insomnia / Postman                             |

---

## 🚀 Puesta en Marcha

Sigue estos pasos para configurar y ejecutar el proyecto en tu máquina local.

### **1. Prerrequisitos**
Asegúrate de tener instalado:
*   **JDK 17** o superior.
*   **Apache Maven** 3.8 o superior.
*   **MySQL Server** 8.0 o superior.
*   Un cliente de API como **Insomnia** o **Postman**.

### **2. Configuración del Proyecto**

**(a) Clona el Repositorio**
```bash
git clone <url-del-repositorio>
cd Foro
(b) Crea la Base de Datos en MySQL
Abre tu cliente de MySQL (como MySQL Workbench) y ejecuta el siguiente comando para crear la base de datos vacía:
code
SQL
CREATE DATABASE foro;
¡No crees ninguna tabla! Flyway lo hará por ti automáticamente en el primer arranque.
(c) Configura las Credenciales de la Base de Datos
Abre el archivo src/main/resources/application.properties y modifica estas líneas con tu usuario y contraseña de MySQL:
code
Properties
spring.datasource.username=tu_usuario_mysql
spring.datasource.password=tu_contraseña_mysql
3. Ejecución
Abre una terminal en la raíz del proyecto y ejecuta el siguiente comando de Maven:
code
Bash
mvn spring-boot:run
La aplicación compilará, ejecutará la migración de la base de datos y se iniciará en http://localhost:8080.
📖 Guía de Uso de la API con Insomnia
A continuación se detalla cómo interactuar con cada uno de los endpoints de la API.
Paso Cero: Autenticación (/login) 🔑
Antes de hacer cualquier otra cosa, necesitas obtener un token de autenticación.
Método: POST
Endpoint: http://localhost:8080/login
Body (JSON):
code
JSON
{
  "login": "admin",
  "clave": "1234"
}
Respuesta Exitosa (200 OK):
code
JSON
{
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
Acción: Copia el jwtToken completo. Lo necesitarás para todas las demás peticiones. En Insomnia, ve a la pestaña Auth, selecciona Bearer Token y pega esta clave en el campo TOKEN.
Operaciones CRUD para Tópicos (/topicos)
1. Listar todos los Tópicos 📄
Método: GET
Endpoint: http://localhost:8080/topicos
Autenticación: Requerida (Bearer Token).
Acción: Simplemente envía la petición.
Respuesta Exitosa (200 OK): Recibirás una lista paginada con todos los tópicos activos en el foro.
2. Crear un Nuevo Tópico ✨
Método: POST
Endpoint: http://localhost:8080/topicos
Autenticación: Requerida (Bearer Token).
Body (JSON): Proporciona los detalles del nuevo tópico. idUsuario y idCurso deben existir en la base de datos.
code
JSON
{
  "titulo": "Duda sobre Streams en Java",
  "mensaje": "¿Cuál es la diferencia de rendimiento entre un parallelStream y un stream normal?",
  "idUsuario": 2,
  "idCurso": 2
}
Acción: Envía la petición con el cuerpo y el token.
Respuesta Exitosa (201 Created): Recibirás el tópico recién creado, ahora con su id y fecha de creación.
3. Actualizar un Tópico Existente ✏️
Método: PUT
Endpoint: http://localhost:8080/topicos
Autenticación: Requerida (Bearer Token).
Body (JSON): Debes incluir el id del tópico a modificar, junto con los campos que deseas cambiar.
code
JSON
{
    "id": 2,
    "titulo": "ArrayList vs LinkedList (Aclarado)",
    "mensaje": "Investigué más y encontré la respuesta. ¡Gracias!"
}
Acción: Envía la petición con el cuerpo y el token.
Respuesta Exitosa (200 OK): Recibirás el tópico completo con los datos ya actualizados.
4. Eliminar un Tópico 🗑️
Método: DELETE
Endpoint: http://localhost:8080/topicos/{id}
Ejemplo: http://localhost:8080/topicos/3
Autenticación: Requerida (Bearer Token).
Acción: Envía la petición a la URL con el ID del tópico que deseas eliminar. No se necesita cuerpo (Body).
Respuesta Exitosa (204 No Content): No recibirás contenido en la respuesta, lo cual confirma que el borrado (lógico) fue exitoso.
Creado por https://github.com/Boristar/
