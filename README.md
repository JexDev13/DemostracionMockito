# 🧪 Mockito Task Manager Demo

Este proyecto es una aplicación simple en Java diseñada con fines educativos para demostrar cómo realizar pruebas unitarias utilizando **Mockito**. Simula la gestión de tareas mediante una arquitectura por capas (Repositorio - Servicio), lo que permite aplicar y probar técnicas modernas de desarrollo basado en pruebas (TDD).

---

## 📌 Objetivo

Mostrar cómo:
- Crear mocks de dependencias (repositorios) con Mockito.
- Verificar interacciones entre clases.
- Simular comportamientos personalizados.

---

## 🛠️ Tecnologías usadas

- Java 17+
- JUnit 5
- Mockito
- Maven
- IntelliJ IDEA (opcional)

---

## 🧩 Estructura del proyecto
com.jex
├── model # Clase Task (modelo de datos)
├── repository # Interfaz TaskRepository simulada con mocks
├── service # Interfaz TaskService
├── service.imp # Implementación del servicio (TaskServiceImpl)
├── TaskServiceTest.java # Pruebas unitarias con Mockito


---

## 🔍 Casos cubiertos en las pruebas

- ✅ Crear una tarea válida
- 🚫 Crear una tarea con `null` o título vacío
- 🔍 Consultar tareas por ID (existente o no existente)
- ✏️ Actualizar tareas y cambiar estado de completado
- 🧯 Eliminar tareas por ID (válido o inválido)
- ⚠️ Verificación de errores intencionales (como permitir títulos vacíos)

---

## 🔄 Ejecución de pruebas

Asegúrate de tener Maven y Java instalados, luego corre:

```
mvn test
```
O ejecuta las pruebas directamente desde tu IDE.

💡 ¿Por qué Mockito?
Mockito permite aislar clases bajo prueba simulando sus dependencias. Esto permite:

Controlar el comportamiento de colaboraciones externas.

Verificar que se llamaron métodos esperados.

Escribir pruebas rápidas y confiables sin necesidad de base de datos o lógica compleja.

📚 Recursos útiles
GitHub
Mockito 
JUnit 5

🧑‍💻 Autor
Desarrollado por Grupo 3

📄 Licencia
Este proyecto está bajo la licencia MIT. Puedes usarlo, modificarlo y compartirlo libremente.
