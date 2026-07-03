package br.com.biblioteca.view;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrontEnd frontEnd = new FrontEnd();
            frontEnd.setVisible(true);
        });
    }
}
