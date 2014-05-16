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

    private final int radius;
    private final int border;
    private final int perRow;

    private final Set<ControlLamp> lamps;
    private final ControlLamp mainLamp;

    public ControlPanel(ControlLamp mainLamp, Set<ControlLamp> lamps) {
        this.perRow = (int) Math.sqrt(lamps.size());
        this.radius = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / (this.perRow + 1)) / 2;
        this.border = 0;
        this.setLayout(null);
        this.setPreferredSize(new Dimension((this.perRow + 1) * (this.radius * 2 + this.border) + this.border, (lamps.size() / this.perRow)
                * (2 * this.radius + this.border) + this.border));
        this.setOpaque(false);

        this.mainLamp = mainLamp;
        this.lamps = lamps;

        int n = 0;
        this.mainLamp.setBounds(this.border, this.border, this.radius);
        for (ControlLamp lamp : this.lamps) {
            lamp.setBounds(this.border + ((n % this.perRow + 1) * (2 * this.radius + this.border)), this.border + (n / this.perRow)
                    * (2 * this.radius + this.border), this.radius);
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
