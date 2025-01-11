package org.example.app;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("YabEdits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EditorPanel panel = new EditorPanel();
        panel.setPreferredSize(new Dimension(800, 600));
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
