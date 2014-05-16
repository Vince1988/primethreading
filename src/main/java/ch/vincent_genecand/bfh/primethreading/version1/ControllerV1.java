package ch.vincent_genecand.bfh.primethreading.version1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.vincent_genecand.bfh.primethreading.gui.ControlLamp;
import ch.vincent_genecand.bfh.primethreading.gui.ControlPanel;
import ch.vincent_genecand.bfh.primethreading.gui.ControlWindow;

public class ControllerV1 {

    private final ControlPanel panel;
    private final List<Prime> primes;
    private final Prime mainPrime;
    private final ThreadGroup threadGroup;
    private final int threads;

    public ControllerV1(int threads, int limit) {
        this.threads = threads;
        this.primes = new ArrayList<>();
        this.mainPrime = new Prime("Main", limit);
        this.threadGroup = new ThreadGroup("Prime Generators");

        this.initPrimes();

        this.panel = new ControlPanel(new ControlLamp(this.mainPrime), this.initLamps());

        new ControlWindow(this.panel);

        this.startPrimes();

        while (this.mainPrime.isAlive() || this.threadGroup.activeCount() > 0) {
            if (!this.mainPrime.isAlive()) {
                this.threadGroup.interrupt();
            }
            this.panel.repaint();
        }
        System.out.println("done");
    }

    private Set<ControlLamp> initLamps() {
        Set<ControlLamp> lamps = new HashSet<>();

        for (Prime prime : this.primes) {
            ControlLamp lamp = new ControlLamp(prime);
            lamps.add(lamp);
        }

        return lamps;
    }

    private void initPrimes() {
        for (int i = 0; i < this.threads * this.threads; i++) {
            this.primes.add(new Prime(this.threadGroup, "#" + i));
        }
    }

    private void startPrimes() {
        this.mainPrime.start();
        for (Prime prime : this.primes) {
            prime.start();
        }
    }

}