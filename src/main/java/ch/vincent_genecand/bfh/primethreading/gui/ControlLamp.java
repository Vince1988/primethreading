package ch.vincent_genecand.bfh.primethreading.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

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

    public void draw(Graphics2D g, Color border) {
        String txt = String.valueOf(this.prime.getHighestPrimeNumber());
        g.setFont(this.generateFont(g, txt));

        Rectangle2D bounds = g.getFontMetrics().getStringBounds(txt, g);
        int h = g.getFontMetrics().getAscent() - g.getFontMetrics().getLeading();
        int w = (int) bounds.getWidth();

        int sX = this.x + this.radius - w / 2;
        int sY = this.y + this.radius + h / 2;

        g.setColor(Color.BLACK);
        g.drawString(txt, sX + 2, sY + 2);
        g.setColor(this.prime.getPrimeState().getColor());
        g.drawString(txt, sX, sY);
    }

    private Font generateFont(Graphics2D g, String txt) {
        int fontSize = this.radius * 2;
        String fontFamily = "Trebuchet MS";
        int fontStyle = Font.PLAIN;
        Font font = new Font(fontFamily, fontStyle, fontSize);

        while (g.getFontMetrics(font).getStringBounds(txt, g).getWidth() > this.radius * 2 || g.getFontMetrics(font).getHeight() > this.radius * 2) {
            font = new Font(fontFamily, fontStyle, --fontSize);
        }

        return font;
    }
}
