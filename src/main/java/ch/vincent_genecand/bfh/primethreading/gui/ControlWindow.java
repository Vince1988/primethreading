package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;

import javax.swing.JFrame;

public class ControlWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String TITLE = "Thread Control";

    public ControlWindow(ControlPanel controlPanel) {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);
        // this.setBackground(Color.WHITE);
        this.setBackground(new Color(0, 0, 0, 0));

        this.setContentPane(controlPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
