# Biblioteca Virtual Backend — Sistema Web con Docker y CI/CD

Sistema web de gestión de biblioteca desarrollado con arquitectura moderna, contenedores y automatización CI/CD.

---

## Autor

Anghelo M. P.  
Estudiante de Ingeniería de Software  
Universidad Tecnológica del Perú

---

## Tecnologías usadas

### Backend

- Java 21
- Spring Boot
- Spring Security + JWT
- PostgreSQL
- Swagger OpenAPI
- Docker

### Frontend

- Angular
- Nginx
- Docker

### DevOps

- Docker & Docker Compose
- GitHub Actions (CI/CD)
- Docker Hub

---

## Arquitectura

- Frontend Angular desplegado en Nginx
- Backend Spring Boot con API REST
- Base de datos PostgreSQL
- Comunicación mediante red Docker
- Persistencia con volúmenes Docker

---

## Requisitos

- Docker
- Docker Compose

> No se requiere Java, Node ni PostgreSQL instalados localmente.

---

## Ejecución del proyecto

Clonar el repositorio:

```bash
git clone https://github.com/AngheloMP10/biblioteca-virtual-backend.git
cd biblioteca-virtual-backend
```

```
biblioteca-virtual-backend
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
│  │  │           │  └─ SecurityConfig.java
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
│  │  │           │  ├─ PrestamoDTO.java
│  │  │           │  ├─ RegisterRequest.java
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
│  │  │           │  └─ PrestamoService.java
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