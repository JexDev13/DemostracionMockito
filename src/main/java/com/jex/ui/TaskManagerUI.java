package com.jex.ui;

import com.jex.model.Task;
import com.jex.repository.InMemoryTaskRepository;
import com.jex.service.TaskService;
import com.jex.service.imp.TaskServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManagerUI extends JFrame {

    private final TaskService service;
    private final DefaultListModel<Task> listModel;
    private final JList<Task> taskList;

    public TaskManagerUI() {
        service = new TaskServiceImpl(new InMemoryTaskRepository());
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new TaskCheckboxRenderer());

        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Task task = listModel.get(index);
                    task.setCompleted(!task.isCompleted());
                    service.updateTask(task);
                    taskList.repaint();
                }
            }
        });

        setTitle("Gestor de Tareas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JButton addButton = new JButton("Agregar");
        JButton deleteButton = new JButton("Eliminar");
        JButton updateButton = new JButton("Actualizar");

        addButton.addActionListener(this::addTask);
        deleteButton.addActionListener(this::deleteTask);
        updateButton.addActionListener(this::updateTask);

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(updateButton);

        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void addTask(ActionEvent e) {
        String title = JOptionPane.showInputDialog("Título:");
        String desc = JOptionPane.showInputDialog("Descripción:");
        if (title != null && desc != null) {
            Task task = new Task(listModel.size() + 1, title, desc);
            service.createTask(task);
            listModel.addElement(task);
        }
    }

    private void deleteTask(ActionEvent e) {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            service.deleteTask(selected.getId());
            listModel.removeElement(selected);
        }
    }

    private void updateTask(ActionEvent e) {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            String newTitle = JOptionPane.showInputDialog("Nuevo título:", selected.getTitle());
            String newDesc = JOptionPane.showInputDialog("Nueva descripción:", selected.getDescription());
            if (newTitle != null && newDesc != null) {
                selected.setTitle(newTitle);
                selected.setDescription(newDesc);
                service.updateTask(selected);
                taskList.repaint();
            }
        }
    }

    static class TaskCheckboxRenderer extends JCheckBox implements ListCellRenderer<Task> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText(task.getTitle() + " - " + " Descripción: " + task.getDescription() +" Estado: " + (task.isCompleted() ? "Hecho" : "Pendiente"));
            setSelected(task.isCompleted());
            setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
            setForeground(Color.BLACK);
            return this;
        }
    }
}
