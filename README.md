# ToDo App

## Descripción

ToDo es una aplicación de gestión de tareas que permite a los usuarios organizar y dar seguimiento a sus actividades diarias. La aplicación está construida con una arquitectura moderna de dos capas: un backend robusto desarrollado en Spring Boot y un frontend intuitivo implementado en React.js.

## Características Técnicas

### Backend

- **Java 17**: Utilización de las últimas características del lenguaje.
- **Spring Boot**: Framework para facilitar el desarrollo de aplicaciones Java con configuración mínima.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **Lombok**: Biblioteca para reducir el código.
- **JPA/Hibernate**: Marco de persistencia para el mapeo objeto-relacional.
- **MySQL**: Sistema de gestión de base de datos relacional para almacenamiento persistente.
- **Proyecciones**: Implementación de proyecciones JPA para optimizar las consultas a la base de datos.
- **Manejo de errores**: Sistema robusto de manejo de excepciones y errores a nivel global.
- **Mappers personalizados**: Conversión eficiente entre entidades de dominio y DTOs.
- **DTOs (Data Transfer Objects)**: Objetos para transferencia de datos entre capas de la aplicación.
- **Testing**:
  - **JUnit**:
    - Pruebas unitarias para validar la lógica de negocio y componentes individuales.
  - **Mockito**:
    - Simulación de dependencias externas para aislar componentes durante las pruebas.
    - Verificación del comportamiento conjunto de componentes en pruebas de integración.
    - Validación de interacciones entre servicios y repositorios.
  - **H2 Database**:
    - Base de datos en memoria utilizada específicamente para entornos de prueba.
    - Permite pruebas de integración rápidas sin necesidad de configurar una base de datos externa.
    - Verificación del correcto funcionamiento de los repositorios y consultas JPA.
  - **Spring Test**:
    - Pruebas de integración para validar el comportamiento de endpoints REST y sus respuestas HTTP.
    - Verificación del ciclo completo de solicitudes HTTP y respuestas del sistema.

### Frontend

- **React.js**: Biblioteca JavaScript para construir la interfaz de usuario de la aplicación.
- **Vite**: Herramienta de construcción que proporciona un entorno de desarrollo más rápido.
- **Hooks**: Implementación de useEffect y useState para gestión del estado y efectos secundarios.
- **Axios**: Cliente HTTP para consumir los endpoints del backend.

## API Documentation

La documentación de la API está disponible a través de Swagger UI en la siguiente URL cuando el backend está en ejecución:

http://localhost:8080/swagger-ui.html
