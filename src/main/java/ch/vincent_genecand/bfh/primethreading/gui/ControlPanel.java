package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import ch.vincent_genecand.bfh.primethreading.version1.PrimeState;

public class ControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Map<String, ControlLamp> lamps;

    public ControlPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(230, 50));
        this.setBackground(Color.WHITE);

        this.lamps = new HashMap<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (Entry<String, ControlLamp> entry : this.lamps.entrySet()) {
            entry.getValue().draw(g2);
        }

    }

    public void addLamp(String key) {
        ControlLamp lamp = new ControlLamp();
        int n = this.lamps.size();

        lamp.setBounds(5 + (n * 45), 5, 20);

        this.lamps.put(key, lamp);
    }

    public void updateLamp(String key, PrimeState state) {
        this.lamps.get(key).setColor(state.getColor());
    }
}
