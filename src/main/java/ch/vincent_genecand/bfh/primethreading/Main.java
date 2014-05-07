package ch.vincent_genecand.bfh.primethreading;

import java.util.ArrayList;
import java.util.List;

import ch.vincent_genecand.bfh.primethreading.gui.ControlPanel;
import ch.vincent_genecand.bfh.primethreading.gui.ControlWindow;
import ch.vincent_genecand.bfh.primethreading.version1.Prime;

final class Main {

    private final ControlWindow window;
    private final ControlPanel panel;
    private final List<Prime> primes;
    private final ThreadGroup threadGroup;

    private Main() {
        this.panel = new ControlPanel();
        this.window = new ControlWindow(this.panel);
        this.primes = new ArrayList<>();
        this.threadGroup = new ThreadGroup("Prime Generators");

        this.initPrimes();
        this.startPrimes();
        this.initLamps();

        while (true) {
            this.panel.repaint();
        }
    }

    private void initLamps() {
        for (Prime prime : this.primes) {
            this.panel.addLamp(prime);
        }
    }

    private void initPrimes() {
        for (int i = 0; i < 5; i++) {
            this.primes.add(new Prime(this.threadGroup, "#" + i, 100));
        }
    }

    private void startPrimes() {
        for (Prime prime : this.primes) {
            prime.start();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
