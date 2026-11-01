# RepairHarnees — Sistema de Gestión de Taller y Servicio Técnico

Aplicación web para el control del flujo de reparación de equipos de oficina del cliente
**Soluciones de Oficina Diaz**. Reemplaza el control manual en hojas sueltas por un sistema
que sigue a cada equipo desde que entra al taller hasta que el cliente lo retira.

## Idea base del proyecto

Soluciones de Oficina Diaz repara equipos de oficina. El control actual se lleva en hojas
suelas, lo que deja de funcionar cuando entran varios equipos a la vez: impresoras iguales
que nadie distingue, clientes que llaman y nadie sabe en qué va su equipo, cotizaciones
demoradas por buscar el papel del diagnóstico y equipos reparados que se quedan semanas sin
que nadie avise al dueño.

El sistema es una aplicación web que funciona igual en computadora, tablet y teléfono, y
sigue al equipo por todo el taller. La pieza central es un **código interno único**: cada
equipo recibe el suyo al momento de la recepción, y a ese código quedan amarrados el
diagnóstico, la cotización, la respuesta del cliente y el aviso de entrega.

Dentro del taller lo usan 7 personas: 2 en recepción, 4 técnicos y 1 administrador.
El cliente no entra al sistema ni necesita cuenta, pero recibe el comprobante al dejar su
equipo, responde la cotización y recibe el aviso cuando la reparación termina.

## Roles del sistema

| Rol | Acceso |
|---|---|
| **Administrador** | Todo: gestión de usuarios, clientes, recepciones, órdenes, comprobantes y logs |
| **Recepcionista** | Clientes, recepciones, órdenes y comprobantes |
| **Reparador** | Sin funciones asignadas por ahora (incrementos futuros) |

## Arquitectura

Clean Architecture en el backend + SPA en el frontend, todo orquestado con Docker Compose.

```
RepairHarnees/
├── docker-compose.yml          # postgres + backend + frontend
├── backend/                    # API REST (Java 21, Spring Boot)
│   ├── src/main/java/com/repairharnees/
│   │   ├── domain/             # Entidades, value objects, interfaces de repositorio
│   │   ├── application/        # Casos de uso, DTOs, validadores, puertos
│   │   ├── infrastructure/     # Adaptadores JPA, JWT, PDF, UnitOfWork
│   │   └── api/                # Controladores REST, excepciones, OpenAPI
│   └── Dockerfile              # multi-stage (Maven build + JRE runtime)
└── frontend/                   # SPA (React + Vite)
    ├── src/
    │   ├── api/                # cliente axios + interceptores JWT
    │   ├── context/            # AuthContext (estado global de autenticación)
    │   ├── components/         # Sidebar (por rol), ProtectedRoute
    │   └── pages/              # Login, Dashboard, Usuarios, Clientes, Recepciones, Ordenes, Comprobantes, Logs
    ├── Dockerfile              # multi-stage (node build + nginx)
    └── nginx.conf              # SPA fallback + proxy /api al backend
```

**Backend (Clean Architecture):**
- **Domain**: `Cliente`, `Equipo`, `OrdenServicio`, `Usuario`, `ComprobanteLog`, value object `CodigoInterno`. Interfaces de repositorio (`IClienteRepository`, etc.).
- **Application**: casos de uso (`RegistrarRecepcionUseCase`, `GenerarComprobanteUseCase`, CRUDs), DTOs, validadores (`RecepcionValidator`), puertos (`IUnitOfWork`, `IPdfGenerator`).
- **Infrastructure**: adaptadores JPA (Spring Data), `UnitOfWorkImpl`, generador de PDF (OpenPDF), seguridad JWT (`JwtService`, `JwtAuthFilter`), `SecurityConfig`.
- **API**: controladores REST, `GlobalExceptionHandler`, configuración OpenAPI/Swagger.

**Patrones aplicados:** Repository, Unit of Work, Use Case, DTO, Validator, Value Object, Ports & Adapters.

**Frontend:** SPA con React Router 6. Estado global de auth con Context API. Axios con interceptores para JWT. Bootstrap 5 para UI. Menú lateral dinámico por rol.

## Stack tecnológico

| Capa | Tecnología |
|---|---|
| Backend | Java 21, Spring Boot 3.3, Maven |
| Seguridad | Spring Security, JWT (jjwt), BCrypt |
| Persistencia | Spring Data JPA, PostgreSQL 16 |
| PDF | OpenPDF |
| Documentación API | SpringDoc OpenAPI (Swagger UI) |
| Frontend | React 18, Vite, React Router 6, Bootstrap 5, Axios |
| Servidor web (front) | Nginx (alpine) |
| Orquestación | Docker Compose |

## Funcionalidades (incremento actual — Sprint 1)

- **HU-01**: Registrar la recepción de un equipo con código interno único.
- **HU-02**: Emitir el comprobante de recepción en PDF.
- CRUD de usuarios, clientes y órdenes de servicio.
- Sistema de roles (recepcionista, reparador, administrador) con JWT.
- Auditoría: log de quién imprimió cada comprobante, cuándo y por qué.
- Swagger UI integrado con autenticación Bearer.

## Guía de instalación en Docker

### Prerrequisitos
- Docker y Docker Compose instalados.
- Puertos libres: 3000 (frontend), 8080 (backend), 5432 (postgres).

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/RepairHarnees/RepairHarnees-V1.git
cd RepairHarnees-V1

# 2. (Opcional) Elegir rama
#    main  -> solo la API
#    dev   -> API + frontend
git checkout dev

# 3. Levantar todo
docker-compose up -d --build

# 4. Verificar
# Frontend:  http://localhost:3000
# Swagger:   http://localhost:8080/swagger-ui.html
```

### Usuarios de prueba (creados automáticamente)

| Usuario | Contraseña | Rol |
|---|---|---|
| admin | admin123 | ADMINISTRADOR |
| recepcion | recepcion123 | RECEPCIONISTA |
| reparador | reparador123 | REPARADOR |

### Comandos útiles

```bash
# Ver estado de los contenedores
docker-compose ps

# Ver logs del backend
docker-compose logs -f backend

# Detener todo
docker-compose down

# Detener y borrar datos de la base
docker-compose down -v
```

### Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/login` | Login (retorna JWT) |
| POST | `/api/recepciones` | Registrar recepción |
| GET | `/api/ordenes` | Listar órdenes |
| GET | `/api/ordenes/{codigo}` | Obtener orden |
| PUT | `/api/ordenes/{codigo}` | Actualizar orden |
| DELETE | `/api/ordenes/{codigo}` | Eliminar orden |
| GET | `/api/ordenes/{codigo}/comprobante` | Descargar comprobante PDF |
| POST | `/api/clientes` | Crear cliente |
| GET | `/api/clientes` | Listar clientes |
| POST | `/api/clientes/{id}/recepciones` | Recepción vinculada a cliente |
| GET/POST/PUT/DELETE | `/api/usuarios` | CRUD de usuarios (solo admin) |
| GET | `/api/logs/comprobantes` | Logs de auditoría |

## Ramas del repositorio

- **main**: únicamente la API (backend + docker-compose con postgres y backend).
- **dev**: API + frontend React integrado en docker-compose.

## Licencia

Proyecto académico — ITLA, Programación III, Sección 2026-C-3.
