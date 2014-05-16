package ch.vincent_genecand.bfh.primethreading.version1;

import java.awt.Color;

public enum PrimeState {
    RUNNING(Color.BLUE), SLEEPING(Color.ORANGE), STOPPED(Color.RED);

    private final Color color;

    private PrimeState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
