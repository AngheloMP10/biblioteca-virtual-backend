# Biblioteca Virtual - Backend

Backend del sistema **Biblioteca Virtual**, una API REST desarrollada con **Spring Boot** para la gestión de préstamos bibliotecarios. El sistema permite administrar usuarios, libros, autores, géneros y préstamos, incorporando mecanismos de seguridad como autenticación mediante JWT, autenticación en dos factores (2FA) y control de acceso basado en roles.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- PostgreSQL
- Spring Data JPA
- Docker
- Cloudinary
- WebSocket
- Maven
- GitHub Actions

---

## Funcionalidades

- Autenticación mediante JWT.
- Autenticación en dos factores (2FA).
- Control de acceso por roles (ADMIN, BIBLIOTECARIO y USER).
- Gestión de libros, autores y géneros.
- Gestión del ciclo completo de préstamos.
- Dashboard administrativo con métricas.
- Carga de imágenes mediante Cloudinary.
- Notificaciones en tiempo real mediante WebSocket.
- API REST documentada con Swagger/OpenAPI.

---

## Arquitectura

El backend sigue una arquitectura en capas compuesta por:

- **Controllers:** Exponen la API REST.
- **Services:** Implementan la lógica de negocio.
- **Repositories:** Acceso a datos mediante Spring Data JPA.
- **Models:** Entidades persistentes.
- **DTOs y Mappers:** Transferencia y transformación de datos.
- **Security:** Configuración de JWT, filtros y control de acceso.

---

## Requisitos

Para ejecutar el proyecto localmente se requiere:

- Java 21
- Maven
- PostgreSQL

O alternativamente:

- Docker
- Docker Compose

---

# Instalación y ejecución

## Clonar el repositorio

```bash
git clone https://github.com/AngheloMP10/biblioteca-virtual-backend.git
cd biblioteca-virtual-backend
```

---

## Variables de entorno

El proyecto utiliza variables de entorno para proteger información sensible.

Como referencia se incluye el archivo:

```text
src/main/resources/application-local-example.properties
```

Entre las principales variables utilizadas se encuentran:

```text
DB_URL_LOCAL
DB_USERNAME_LOCAL
DB_PASSWORD_LOCAL

JWT_SECRET_LOCAL

CLOUDINARY_CLOUD_NAME_LOCAL
CLOUDINARY_API_KEY_LOCAL
CLOUDINARY_API_SECRET_LOCAL
```

---

## Ejecución local

Ejecutar utilizando el perfil **local**:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

O directamente desde Spring Tools o IntelliJ seleccionando el perfil:

```text
local
```

---

## Ejecución con Docker

```bash
docker compose up --build
```

La aplicación utilizará automáticamente el perfil **docker**.

---

## Documentación de la API

Una vez iniciado el proyecto, la documentación Swagger estará disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Pruebas

El proyecto incluye:

- Pruebas unitarias.
- Pruebas de integración.
- MockMvc.
- JUnit 5.

Ejecutarlas mediante:

```bash
./mvnw test
```

---

## Despliegue

El proyecto está preparado para despliegue mediante:

- Backend: Render
- Base de datos: Supabase PostgreSQL
- Contenedores Docker
- GitHub Actions para CI/CD
- Docker Hub como registro de imágenes

```text
https://bucolic-horse-0d3efe.netlify.app/
```

---

## Autor

**Anghelo Mendoza Prado**

Estudiante de Ingeniería de Software  
Universidad Tecnológica del Perú

---

## Estructura del proyecto

```
biblioteca-virtual
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ docker-compose.yml
├─ Dockerfile
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ README.md
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com
│  │  │     └─ biblio
│  │  │        └─ virtual
│  │  │           ├─ BibliotecaVirtualApplication.java
│  │  │           ├─ config
│  │  │           │  ├─ CloudinaryConfig.java
│  │  │           │  ├─ SecurityConfig.java
│  │  │           │  └─ WebSocketConfig.java
│  │  │           ├─ controller
│  │  │           │  ├─ AuthController.java
│  │  │           │  ├─ AutorController.java
│  │  │           │  ├─ DashboardController.java
│  │  │           │  ├─ GeneroController.java
│  │  │           │  ├─ LibrosController.java
│  │  │           │  ├─ MediaController.java
│  │  │           │  └─ PrestamoController.java
│  │  │           ├─ dto
│  │  │           │  ├─ AuthRequest.java
│  │  │           │  ├─ AuthResponse.java
│  │  │           │  ├─ AutorDTO.java
│  │  │           │  ├─ DashboardGeneroDTO.java
│  │  │           │  ├─ DashboardLibroDTO.java
│  │  │           │  ├─ DashboardMetricasDTO.java
│  │  │           │  ├─ DashboardResponseDTO.java
│  │  │           │  ├─ GeneroDTO.java
│  │  │           │  ├─ LibroDTO.java
│  │  │           │  ├─ NotificacionDTO.java
│  │  │           │  ├─ PrestamoDTO.java
│  │  │           │  ├─ RegisterRequest.java
│  │  │           │  ├─ TwoFactorRequest.java
│  │  │           │  ├─ TwoFactorResponse.java
│  │  │           │  └─ UsuarioDTO.java
│  │  │           ├─ filter
│  │  │           │  └─ JwtRequestFilter.java
│  │  │           ├─ mapper
│  │  │           │  ├─ AutorMapper.java
│  │  │           │  ├─ GeneroMapper.java
│  │  │           │  ├─ LibroMapper.java
│  │  │           │  ├─ PrestamoMapper.java
│  │  │           │  └─ UsuarioMapper.java
│  │  │           ├─ model
│  │  │           │  ├─ Autor.java
│  │  │           │  ├─ enums
│  │  │           │  │  └─ EstadoPrestamo.java
│  │  │           │  ├─ Genero.java
│  │  │           │  ├─ Libro.java
│  │  │           │  ├─ Prestamo.java
│  │  │           │  └─ Usuario.java
│  │  │           ├─ repository
│  │  │           │  ├─ IAutorRepository.java
│  │  │           │  ├─ IGeneroRepository.java
│  │  │           │  ├─ ILibroRepository.java
│  │  │           │  ├─ IPrestamoRepository.java
│  │  │           │  └─ IUsuarioRepository.java
│  │  │           ├─ security
│  │  │           │  ├─ RoleExpressions.java
│  │  │           │  └─ Roles.java
│  │  │           ├─ service
│  │  │           │  ├─ AutorService.java
│  │  │           │  ├─ CloudinaryService.java
│  │  │           │  ├─ CustomUserDetailsService.java
│  │  │           │  ├─ DashboardService.java
│  │  │           │  ├─ GeneroService.java
│  │  │           │  ├─ IAutorService.java
│  │  │           │  ├─ IDashboardService.java
│  │  │           │  ├─ IGeneroService.java
│  │  │           │  ├─ ILibroService.java
│  │  │           │  ├─ IPrestamoService.java
│  │  │           │  ├─ LibroService.java
│  │  │           │  ├─ NotificacionService.java
│  │  │           │  ├─ PrestamoService.java
│  │  │           │  └─ TwoFactorAuthService.java
│  │  │           └─ util
│  │  │              └─ JwtUtil.java
│  │  └─ resources
│  │     ├─ application-docker.properties
│  │     ├─ application-local-example.properties
│  │     ├─ application-prod.properties
│  │     ├─ application.properties
│  │     ├─ data.sql
│  │     ├─ static
│  │     └─ templates
│  └─ test
│     ├─ java
│     │  └─ com
│     │     └─ biblio
│     │        └─ virtual
│     │           ├─ BibliotecaVirtualApplicationTests.java
│     │           ├─ controller
│     │           │  ├─ GeneroControllerTest.java
│     │           │  ├─ GeneroIntegrationTest.java
│     │           │  └─ TestSecurityConfig.java
│     │           └─ service
│     │              ├─ LibroServiceTest.java
│     │              └─ PrestamoServiceTest.java
│     └─ resources
│        └─ application-test.properties
└─ system.properties

```
