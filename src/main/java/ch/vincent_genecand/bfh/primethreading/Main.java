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
            this.updateLamps();
            this.panel.repaint();
        }
    }

    private void updateLamps() {
        for (Prime prime : this.primes) {
            this.panel.updateLamp(prime.getName(), prime.getPrimeState());
        }
    }

    private void initLamps() {
        for (Prime prime : this.primes) {
            this.panel.addLamp(prime.getName());
        }
    }

    private void initPrimes() {
        for (int i = 0; i < 5; i++) {
            this.primes.add(new Prime(this.threadGroup, "#" + i, 10));
        }
    }

    private void startPrimes() {
        for (Prime prime : this.primes) {
            prime.start();
        }
    }

    public static void main(String[] args) {
        new Main();
        // List<Prime> primes = new ArrayList<>();
        // ThreadGroup tg = new ThreadGroup("Unlimited Prime Generators");
        // Prime prime = new Prime("Limited Prime Generator", 25);
        //
        // while (prime.isAlive()) {
        // try {
        // System.out.print(prime);
        // for (Prime t : primes) {
        // System.out.print(t);
        // }
        // System.out.println();
        //
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
        // tg.interrupt();
        //
        // System.out.print(prime);
        // for (Prime t : primes) {
        // System.out.print(t);
        // }
    }
}
