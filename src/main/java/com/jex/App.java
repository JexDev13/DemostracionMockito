package com.jex;

import com.jex.ui.TaskManagerUI;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TaskManagerUI ui = new TaskManagerUI();
            ui.setVisible(true);
        });
    }
}
