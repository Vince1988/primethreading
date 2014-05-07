package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ch.vincent_genecand.bfh.primethreading.version1.Prime;

public class ControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final List<ControlLamp> lamps;

    public ControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(230, 50));
        this.setBackground(Color.WHITE);

        this.lamps = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (ControlLamp lamp : this.lamps) {
            lamp.draw(g2);
        }

    }

    public void addLamp(Prime prime) {
        ControlLamp lamp = new ControlLamp(prime);
        int n = this.lamps.size();

        lamp.setBounds(5 + (n * 45), 5, 20);

        this.lamps.add(lamp);
    }
}
