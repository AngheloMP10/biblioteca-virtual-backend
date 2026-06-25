# Biblioteca Virtual - Backend

Backend del sistema **Biblioteca Virtual**, una aplicación web orientada a la gestión de préstamos bibliotecarios, administración de libros y control de usuarios mediante acceso por roles.

El sistema permite gestionar el catálogo de libros, solicitudes de préstamo, autores, géneros y métricas administrativas, además de incorporar mecanismos de seguridad como autenticación JWT y verificación en dos pasos (2FA).

---

## Autor

**Anghelo M. P.**
Estudiante de Ingeniería de Software
Universidad Tecnológica del Perú

---

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Docker
- WebSockets
- Cloudinary

---

## Funcionalidades principales

- Autenticación y autorización por roles:
  - ADMIN
  - BIBLIOTECARIO
  - USER

- Gestión de libros

- Gestión de autores

- Gestión de géneros

- Gestión de préstamos

- Dashboard administrativo

- Autenticación en dos factores (2FA)

- Notificaciones en tiempo real

---

## Arquitectura general

El backend expone una API REST desarrollada con Spring Boot que se comunica con:

- Frontend en Angular
- Base de datos PostgreSQL
- Servicios externos de almacenamiento multimedia

La arquitectura está diseñada para ser modular, escalable y fácil de mantener.

---

## Requisitos

Para ejecutar el proyecto se recomienda contar con:

- Java 21
- Maven
- PostgreSQL

O alternativamente:

- Docker
- Docker Compose

---

## Ejecución del proyecto

Clonar el repositorio:

```bash
git clone https://github.com/AngheloMP10/biblioteca-virtual-backend.git
cd biblioteca-virtual-backend
```

Ejecutar con Maven:

```bash
./mvnw spring-boot:run
```

O mediante Docker:

```bash
docker compose up --build
```

---

## Estado del proyecto

Proyecto en desarrollo activo.
Nuevas funcionalidades, mejoras y optimizaciones seguirán incorporándose en futuras versiones.

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
│  │     ├─ application-local.properties
│  │     ├─ application-prod.properties
│  │     ├─ data.sql
│  │     ├─ static
│  │     └─ templates
│  └─ test
│     └─ java
│        └─ com
│           └─ biblio
│              └─ virtual
│                 ├─ BibliotecaVirtualApplicationTests.java
│                 ├─ controller
│                 │  └─ GeneroControllerTest.java
│                 └─ service
│                    └─ LibroServiceTest.java
└─ system.properties

```
