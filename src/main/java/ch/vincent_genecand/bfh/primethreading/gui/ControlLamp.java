package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import ch.vincent_genecand.bfh.primethreading.version1.Prime;

public class ControlLamp {

    private final Prime prime;

    private int x;
    private int y;
    private int radius;

    public ControlLamp(Prime prime) {
        this.prime = prime;
    }

    public void setBounds(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Graphics2D g) {
        g.setColor(this.prime.getPrimeState().getColor());
        g.fillOval(this.x, this.y, 2 * this.radius, 2 * this.radius);
        g.setColor(Color.BLACK);
        g.drawOval(this.x, this.y, 2 * this.radius, 2 * this.radius);
        g.drawString(String.valueOf(this.prime.getHighestPrimeNumber()), this.x + this.radius / 2, this.y + this.radius);
    }

}
