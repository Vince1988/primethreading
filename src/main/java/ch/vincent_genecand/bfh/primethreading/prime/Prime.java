package ch.vincent_genecand.bfh.primethreading.prime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

public class Prime extends Thread implements Callable<List<Long>> {

    private final List<Long> primeNumbers;
    private final boolean gui;
    private final long upperLimit;
    private final long lowerLimit;
    private final Random rnd;

    private PrimeState primeState;

    public Prime(ThreadGroup group, String name, boolean gui) {
        this(group, name, 0, 0, gui, true);
    }

    public Prime(String name, long upperLimit, boolean gui) {
        this(null, name, 0, upperLimit, gui, true);
    }

    public Prime(ThreadGroup group, String name, long lowerLimit, long upperLimit) {
        this(group, name, lowerLimit, upperLimit, false, false);
    }

    public Prime(ThreadGroup threadGroup, String name, long lowerLimit, long upperLimit, boolean gui, boolean sleep) {
        super(threadGroup, name);

        this.primeState = PrimeState.STOPPED;
        this.gui = gui;
        this.lowerLimit = lowerLimit > 2 ? lowerLimit : 2;
        this.upperLimit = upperLimit > 2 ? upperLimit : Long.MAX_VALUE;

        this.primeNumbers = new ArrayList<>();
        this.rnd = new Random(System.nanoTime());
    }

    public long getHighestPrimeNumber() {
        return this.primeNumbers.isEmpty() ? 0 : this.primeNumbers.get(this.primeNumbers.size() - 1);
    }

    private void calculatePrimeNumbers() {
        this.primeState = PrimeState.RUNNING;
        for (long i = this.lowerLimit; i < this.upperLimit && !this.isInterrupted(); i++) {
            if (Prime.isPrimeNumber(i)) {
                this.primeNumbers.add(i);
                this.sleep();
            }

        }
        this.primeState = PrimeState.STOPPED;
    }

    private void sleep() {
        try {
            this.toggle();
            Thread.sleep(this.rnd.nextInt(1001));
            this.toggle();
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }

    private void toggle() throws InterruptedException {
        switch (this.primeState) {
        case RUNNING:
            this.primeState = PrimeState.SLEEPING;
            break;
        case SLEEPING:
            this.primeState = PrimeState.RUNNING;
            if (this.gui) {
                Thread.sleep(80);
            }
            break;
        default:
            break;
        }
    }

    private boolean isNextPrimeNumber(long number) {
        for (long primeNumber : this.primeNumbers) {
            if (number % primeNumber == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Long> call() throws Exception {
        this.calculatePrimeNumbers();
        return this.primeNumbers;
    }

    @Override
    public void run() {
        this.calculatePrimeNumbers();
    }

    public final static boolean isPrimeNumber(long number) {
        if (number < 2) {
            return false;
        } else {
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    public final static Set<Long> combine(Prime... primes) {
        Set<Long> primeNumbers = new TreeSet<>();

        for (Prime prime : primes) {
            primeNumbers.addAll(prime.primeNumbers);
        }

        return Collections.unmodifiableSet(primeNumbers);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public PrimeState getPrimeState() {
        return this.primeState;
    }

}
