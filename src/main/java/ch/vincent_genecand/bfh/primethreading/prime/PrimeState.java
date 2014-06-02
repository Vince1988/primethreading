package ch.vincent_genecand.bfh.primethreading.prime;

import java.awt.Color;

public enum PrimeState {
    RUNNING(new Color(255, 200, 0)), SLEEPING(new Color(255, 255, 0, 75)), STOPPED(Color.RED), FOUND(Color.GREEN);

    private final Color color;

    private PrimeState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
