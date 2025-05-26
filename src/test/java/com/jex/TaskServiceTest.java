package com.jex;

import com.jex.model.Task;
import com.jex.repository.TaskRepository;
import com.jex.service.TaskService;
import com.jex.service.imp.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para {@link TaskServiceImpl}.
 * <p>
 * Esta clase utiliza Mockito para simular el comportamiento del repositorio {@link TaskRepository},
 * permitiendo probar la lógica de negocio del servicio sin depender de una implementación real del repositorio.
 * <p>
 * Mockito es un framework de mocking para pruebas en Java. Permite crear objetos simulados (mocks) que imitan
 * el comportamiento de dependencias reales. Es especialmente útil para pruebas unitarias donde queremos aislar
 * una unidad de código de sus dependencias.
 * <p>
 * Funciones clave de Mockito usadas en esta clase:
 * - {@code mock(Class)}: crea un mock de la clase dada.
 * - {@code when(...).thenReturn(...)}: define comportamiento simulado.
 * - {@code verify(...)}: verifica si un método fue invocado en el mock.
 * - {@code doThrow(...).when(...)}: simula excepciones lanzadas por métodos mockeados.
 */
public class TaskServiceTest {

    // CREAR
    /**
     * Verifica que una tarea se guarda correctamente usando el repositorio mockeado.
     */
    @Test
    public void testCreateAndRetrieveTask() {
        // 1. Creamos un mock del repositorio.
        //    Esto reemplaza el comportamiento real del repositorio con un objeto simulado.
        //    Nos permite controlar las respuestas y verificar interacciones.
        TaskRepository repo = mock(TaskRepository.class);

        // 2. Creamos una instancia del servicio usando el repositorio simulado.
        //    Así podemos probar la lógica del servicio sin depender de una base de datos real.
        TaskService service = new TaskServiceImpl(repo);

        // 3. Creamos una tarea de prueba que queremos guardar.
        Task task = new Task(1, "Leer libro", "Leer Java en 24 horas");

        // 4. Llamamos al método que queremos probar.
        //    En este caso, el servicio debería delegar la operación al repositorio.
        service.createTask(task);

        // 5. Verificamos que el método save del repositorio fue llamado con la tarea.
        //    Esta es la parte clave: confirmamos que la interacción esperada ocurrió.
        verify(repo).save(task);

        // ¿Qué estamos probando aquí?
        // ✔ Que el servicio llama correctamente al repositorio para guardar la tarea.
        // NOTA: No estamos validando el contenido del repositorio, ya que es simulado (mock).
    }


    /**
     * Verifica que intentar crear una tarea nula lanza una excepción.
     */
    @Test
    public void testCreateTaskWithNull() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);
        // assertThrows se usa para comprobar que una operación lanza una excepción específica.
        assertThrows(NullPointerException.class, () -> service.createTask(null));
    }

    // OBTENER

    /**
     * Verifica que una tarea existente se puede obtener correctamente.
     */
    @Test
    public void testGetTask() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        Task mockTask = new Task(2, "Escribir", "Escribir documentación");
        when(repo.findById(2)).thenReturn(Optional.of(mockTask));

        Task result = service.getTask(2);
        assertNotNull(result);
        assertEquals("Escribir", result.getTitle());
    }

    /**
     * Verifica que obtener una tarea inexistente retorna null.
     */
    @Test
    public void testGetTaskNotFound() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        when(repo.findById(99)).thenReturn(Optional.empty());

        assertNull(service.getTask(99));
    }

    /**
     * Verifica que el sistema rechaza tareas con título vacío (prueba fallida intencionalmente).
     */
    @Test
    public void testCreateTaskWithEmptyTitleFails() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);
        Task task = new Task(7, "", "Descripción vacía");

        service.createTask(task);
        assertFalse(task.getTitle().isEmpty(), "El título no debe estar vacío");
    }

    // ACTUALIZAR

    /**
     * Verifica que una tarea se puede actualizar correctamente usando el repositorio mockeado.
     */
    @Test
    public void testUpdateTask() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        Task task = new Task(1, "Actualizar tarea", "Actualizar descripción");
        service.updateTask(task);

        verify(repo).update(task);
    }

    /**
     * Verifica que actualizar una tarea nula lanza una excepción.
     */
    @Test
    public void testUpdateTaskNull() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        assertThrows(NullPointerException.class, () -> service.updateTask(null));
    }

    /**
     * Verifica que se puede cambiar el estado de completado de una tarea y que se actualiza correctamente.
     */
    @Test
    public void testToggleCompletedStatus() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        Task task = new Task(5, "Tarea de prueba", "Cambiar estado");
        task.setCompleted(false);

        task.setCompleted(true);
        service.updateTask(task);

        verify(repo).update(task);
        assertTrue(task.isCompleted());
    }

    /**
     * Verifica que cambiar dos veces el estado de completado revierte al valor inicial.
     */
    @Test
    public void testToggleStatusTwice() {
        Task task = new Task(6, "Test doble toggle", "Probar toggle 2 veces");
        assertFalse(task.isCompleted());
        task.setCompleted(true);
        assertTrue(task.isCompleted());
        task.setCompleted(false);
        assertFalse(task.isCompleted());
    }

    /**
     * Verifica que intentar actualizar una tarea inexistente lanza una excepción.
     */
    @Test
    public void testUpdateNonexistentTaskFails() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);
        Task task = new Task(8, "No existe", "No está guardado");

        doThrow(new IllegalStateException("La tarea no existe")).when(repo).update(task);
        assertThrows(IllegalStateException.class, () -> service.updateTask(task));
    }

    // ELIMINAR

    /**
     * Verifica que una tarea se elimina correctamente según su ID.
     */
    @Test
    public void testDeleteTask() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        service.deleteTask(4);
        verify(repo).delete(4);
    }

    /**
     * Verifica que intentar eliminar una tarea con ID inválido lanza una excepción.
     */
    @Test
    public void testDeleteTaskInvalidId() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        doThrow(new IllegalArgumentException("ID inválido")).when(repo).delete(-1);
        assertThrows(IllegalArgumentException.class, () -> service.deleteTask(-1));
    }

    /**
     * Verifica que intentar eliminar una tarea con ID cero lanza una excepción.
     */
    @Test
    public void testDeleteNullIdFails() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskService service = new TaskServiceImpl(repo);

        doThrow(new IllegalArgumentException("ID no puede ser nulo")).when(repo).delete(0);
        assertThrows(IllegalArgumentException.class, () -> service.deleteTask(0));
    }
}
