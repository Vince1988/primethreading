package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Set;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final int sideLength;
    private final int perRow;

    private final Set<ControlLamp> lamps;
    private final ControlLamp mainLamp;

    public ControlPanel(ControlLamp mainLamp, Set<ControlLamp> lamps) {
        this.mainLamp = mainLamp;
        this.lamps = lamps;

        this.perRow = (int) Math.sqrt(lamps.size());
        this.sideLength = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / (this.perRow + 1);

        this.setLayout(null);
        this.setPreferredSize(new Dimension((this.perRow + 1) * this.sideLength, (lamps.size() / this.perRow) * this.sideLength));
        this.setOpaque(false);

        int n = 0;
        this.mainLamp.setBounds(0, this.getPreferredSize().height / 2 - this.sideLength / 2, this.sideLength);
        for (ControlLamp lamp : this.lamps) {
            lamp.setBounds((n % this.perRow + 1) * this.sideLength, (n / this.perRow) * this.sideLength, this.sideLength);
            n++;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        this.mainLamp.draw(g2, Color.DARK_GRAY);
        for (ControlLamp lamp : this.lamps) {
            lamp.draw(g2, Color.GRAY);
        }

    }
}
