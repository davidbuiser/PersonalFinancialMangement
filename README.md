# Personal Financial Management

## 1. Resumen del proyecto

Backend REST para gestionar gastos personales, construido con Spring Boot y arquitectura por capas inspirada en puertos y adaptadores.

Funciones principales:
- Registrar gastos
- Consultar historial de gastos
- Calcular gasto acumulado y avisar cuando se supera un limite presupuestario

## 2. Estado funcional actual

Actualmente el backend incluye:
- Endpoint para crear gastos
- Endpoint para listar historial de gastos
- Validaciones de entrada para proteger la API
- Respuestas JSON estandarizadas para frontend
- Manejo global de errores
- CORS configurado para consumir desde frontend React en desarrollo
- Persistencia con H2 en memoria mediante Spring Data JPA

## 3. Arquitectura

Se aplica una separacion por responsabilidades:

- Dominio:
  - Modelos de negocio y reglas del dominio
  - Puerto de salida para persistencia
- Aplicacion:
  - Casos de uso que orquestan la logica de negocio
- Infraestructura:
  - Adaptadores de entrada (controladores web)
  - Adaptadores de salida (implementacion JPA)
- Configuracion:
  - Inyeccion manual de casos de uso
  - Configuracion CORS

Flujo de registro de gasto:
1. Cliente envia solicitud HTTP
2. Controlador valida y transforma datos a modelo de dominio
3. Caso de uso registra el gasto
4. Repositorio de infraestructura persiste con JPA
5. Se responde en formato JSON uniforme

## 4. Estructura de carpetas

- src/main/java/org/codefactory/team07/personalfinancialmanagement
  - application/usecase
    - RegisterExpenseUseCase
    - GetExpenseHistoryUseCase
  - domain/model
    - Expense
    - Category
  - domain/port/out
    - ExpenseRepository
  - infrastructure/adapter/in/web
    - ExpenseController
    - ExpenseDTO
    - ExpenseResponseDTO
    - ApiResponse
    - GlobalExceptionHandler
  - infrastructure/adapter/out/persistance
    - ExpenseEntity
    - JpaExpenseRepository
    - ExpenseRepositoryImpl
  - config
    - BeanConfiguration
    - WebConfig

## 5. Stack tecnologico

- Java 21
- Spring Boot 3.5.13
- Spring Web
- Spring Data JPA
- Spring Validation
- H2 Database
- Lombok
- Maven

## 6. Requisitos para ejecutar

- JDK 21 instalado
- Variable JAVA_HOME apuntando al JDK 21
- Maven opcional (el proyecto incluye Maven Wrapper)

## 7. Configuracion de entorno

Archivo principal de configuracion:
- src/main/resources/application.properties

Propiedades relevantes:
- spring.datasource.url=jdbc:h2:mem:testdb
- spring.datasource.driverClassName=org.h2.Driver
- spring.datasource.username=sa
- spring.datasource.password=
- spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
- spring.jpa.hibernate.ddl-auto=update
- spring.h2.console.enabled=true
- app.cors.allowed-origins=http://localhost:5173,http://localhost:3000

## 8. Ejecucion del proyecto

Con Maven Wrapper en Windows:

    mvnw.cmd spring-boot:run

Con Maven Wrapper en Linux/macOS:

    ./mvnw spring-boot:run

Aplicacion disponible en:
- http://localhost:8080

## 9. Base de datos H2

Tipo:
- En memoria

Implicaciones:
- Los datos se pierden al reiniciar la aplicacion
- Adecuado para desarrollo y pruebas rapidas

Consola H2:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Usuario: sa
- Password: vacio

Tabla principal:
- expenses
  - id
  - description
  - amount
  - category
  - date

## 10. API REST

### 10.1 Registrar gasto

Metodo y ruta:
- POST /expenses

Request body (JSON):

    {
      "description": "Mercado semanal",
      "amount": 125000.50,
      "category": "ALIMENTACION",
      "date": "2026-04-05"
    }

Respuesta exitosa:

    {
      "success": true,
      "message": "Gasto registrado exitosamente.",
      "data": null,
      "timestamp": "2026-04-05T20:30:00.123"
    }

Nota de negocio:
- Si el total acumulado supera el limite, el mensaje cambia a alerta de presupuesto excedido.

### 10.2 Obtener historial de gastos

Metodo y ruta:
- GET /expenses

Respuesta exitosa:

    {
      "success": true,
      "message": "Historial obtenido correctamente",
      "data": [
        {
          "description": "Mercado semanal",
          "amount": 125000.50,
          "category": "ALIMENTACION",
          "date": "2026-04-05"
        }
      ],
      "timestamp": "2026-04-05T20:31:00.456"
    }

## 11. Contrato de respuesta estandar

Formato comun para todas las respuestas:
- success: boolean
- message: texto funcional
- data: objeto o arreglo con datos
- timestamp: fecha y hora de respuesta

Ventaja para frontend:
- React puede implementar una sola estrategia de parseo para exito y error.

## 12. Validaciones de entrada

Sobre ExpenseDTO:
- description: obligatorio
- amount: mayor a 0
- category: obligatoria
- date: obligatoria y no futura

Categorias permitidas:
- ALIMENTACION
- TRANSPORTE
- VIVIENDA
- ENTRETENIMIENTO
- SALUD
- EDUCACION

## 13. Manejo global de errores

El backend centraliza errores en GlobalExceptionHandler.

Escenarios cubiertos:
- Error de validacion de campos
- Argumentos invalidos de negocio
- JSON mal formado
- Error interno no controlado

Ejemplo de error de validacion:

    {
      "success": false,
      "message": "Error de validación",
      "data": {
        "amount": "El monto debe ser mayor a 0"
      },
      "timestamp": "2026-04-05T20:32:00.000"
    }

## 14. CORS para frontend React

CORS habilitado para desarrollo en:
- http://localhost:5173
- http://localhost:3000

Si el frontend corre en otro origen, ajustar propiedad:
- app.cors.allowed-origins

## 15. Regla de negocio de presupuesto

Caso de uso:
- RegisterExpenseUseCase

Regla:
- Se calcula gasto total acumulado
- Si total > 2000000.0, la API devuelve mensaje de alerta

## 16. Pruebas

Estado actual:
- Existe prueba basica de contexto de Spring:
  - PersonalfinancialmanagementApplicationTests

Ejecutar pruebas:

    mvnw.cmd test

## 17. Guia de integracion con React + Tailwind

### 17.1 Configuracion sugerida de frontend

- Cliente HTTP: fetch o axios
- URL base del backend: http://localhost:8080
- Manejar respuestas usando campos success, message, data

### 17.2 Flujo recomendado de UI

1. Formulario de registro de gasto
2. Validacion en cliente antes de enviar
3. POST /expenses
4. Mostrar mensaje desde message
5. Refrescar tabla con GET /expenses

### 17.3 Estrategia de errores en frontend

- Si success es false, mostrar message general
- Si data contiene mapa de campos, mostrar errores por input

## 18. Recomendaciones para produccion

Para pasar a produccion se recomienda:
- Cambiar H2 en memoria por PostgreSQL persistente (pensado para el próximo sprint)
- Usar perfiles por entorno (dev, test, prod)
- Mover limite de presupuesto a base de datos o configuracion externa
- Incorporar autenticacion y autorizacion
- Agregar pruebas unitarias e integracion por endpoint
- Versionar API (por ejemplo /api/v1)

## 19. Solucion de problemas frecuentes

1. Error JAVA_HOME no definido
- Definir JAVA_HOME apuntando al JDK 21

2. Error de CORS en navegador
- Revisar app.cors.allowed-origins
- Confirmar puerto real del frontend

3. Datos desaparecen al reiniciar
- Es comportamiento esperado de H2 en memoria

4. Error categoria invalida
- Enviar categoria exactamente con uno de los valores del enum

## 20. Informacion de mantenimiento

- Proyecto base: Spring Boot
- Gestion de dependencias: Maven
- Convencion principal: separacion por capas y contratos por puerto

---

Si se requiere, en una siguiente iteracion se puede agregar documentacion OpenAPI/Swagger para generar especificacion formal y coleccion de pruebas automaticamente.
