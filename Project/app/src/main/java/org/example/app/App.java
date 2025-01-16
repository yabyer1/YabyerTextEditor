package org.example.app;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.awt.Cursor.HAND_CURSOR;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("YabEdits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EditorPanel panel = new EditorPanel();
        panel.setPreferredSize(new Dimension(800, 600));

        frame.add(panel);
       JMenuBar menuBar = new JMenuBar();
       JMenu fileMenu = new JMenu("File");
       JMenuItem openItem = new JMenuItem("Open....");
       openItem.addActionListener(e ->{
           JFileChooser fileChooser = new JFileChooser();
           int res = fileChooser.showOpenDialog(frame);
           if(res == JFileChooser.APPROVE_OPTION) {
               File selectedFile = fileChooser.getSelectedFile();

               if (selectedFile != null) {
                   try {
                       panel.gapbuffer.LoadFile(selectedFile.getAbsolutePath(), panel.f);
                       panel.repaint();
                   } catch (IOException ex) {
                       ex.printStackTrace();
                       JOptionPane.showMessageDialog(frame, "Error loading file" + ex.getMessage()
                               , "File Load Error", JOptionPane.ERROR_MESSAGE);
                   }

               }
           }
           });
            fileMenu.add(openItem);
            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
