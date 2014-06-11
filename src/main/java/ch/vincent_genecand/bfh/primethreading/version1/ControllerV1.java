package ch.vincent_genecand.bfh.primethreading.version1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.vincent_genecand.bfh.primethreading.gui.ControlLamp;
import ch.vincent_genecand.bfh.primethreading.gui.ControlPanel;
import ch.vincent_genecand.bfh.primethreading.gui.ControlWindow;
import ch.vincent_genecand.bfh.primethreading.prime.Prime;

public class ControllerV1 {

    private ControlPanel panel;
    private final int threads;
    private final int limit;

    private final ExecutorService executorService;

    private Prime mainPrime;
    private Future<List<Long>> mainFuturePrime;

    private final List<Prime> groupPrimes;
    private final List<Future<List<Long>>> groupFuturePrimes;

    public ControllerV1(int threads, int limit, boolean gui) {
        this.threads = threads * threads;
        this.limit = limit;

        this.groupFuturePrimes = new ArrayList<>();
        this.groupPrimes = new ArrayList<>();

        this.executorService = Executors.newFixedThreadPool(1 + this.threads);

        this.initPrimes(gui);

        if (gui) {
            this.panel = new ControlPanel(new ControlLamp(this.mainPrime), this.initLamps());
            new ControlWindow(this.panel);
        }

        this.startPrimes();

        while (!this.mainFuturePrime.isDone()) {
            if (gui) {
                this.panel.repaint();
            }
        }

        this.executorService.shutdownNow();

        if (gui) {
            while (!this.executorService.isTerminated()) {
                this.panel.repaint();
            }
        }

        System.out.println("done");
    }

    private Set<ControlLamp> initLamps() {
        Set<ControlLamp> lamps = new HashSet<>();

        for (Prime prime : this.groupPrimes) {
            ControlLamp lamp = new ControlLamp(prime);
            lamps.add(lamp);
        }

        return lamps;
    }

    private void initPrimes(boolean gui) {
        this.mainPrime = new Prime("Main", this.limit, gui);
        for (int i = 0; i < this.threads; i++) {
            this.groupPrimes.add(new Prime("#" + i, gui));
        }
    }

    private void startPrimes() {
        this.mainFuturePrime = this.executorService.submit(this.mainPrime);
        for (Prime prime : this.groupPrimes) {
            this.groupFuturePrimes.add(this.executorService.submit(prime));
        }
    }

}
