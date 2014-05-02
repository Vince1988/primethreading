package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class ControlLamp {

    private Color color;
    private int x;
    private int y;
    private int radius;

    public ControlLamp() {

    }

    public void setBounds(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fillOval(this.x, this.y, 2 * this.radius, 2 * this.radius);
        g.setColor(Color.BLACK);
        g.drawOval(this.x, this.y, 2 * this.radius, 2 * this.radius);
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
