package ch.vincent_genecand.bfh.primethreading.prime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

public class Prime implements Callable<List<Long>> {

    private final String name;
    private final long upperLimit;
    private final long lowerLimit;

    private final List<Long> primeNumbers;

    private final boolean gui;
    private final boolean randomSleep;

    private final Random rnd;

    private PrimeState primeState;

    public Prime(String name, boolean gui) {
        this(name, 0, 0, gui, true);
    }

    public Prime(String name, long upperLimit, boolean gui) {
        this(name, 0, upperLimit, gui, true);
    }

    public Prime(String name, long lowerLimit, long upperLimit) {
        this(name, lowerLimit, upperLimit, false, false);
    }

    public Prime(String name, long lowerLimit, long upperLimit, boolean gui, boolean randomSleep) {
        this.name = name;
        this.primeState = PrimeState.STOPPED;
        this.gui = gui;
        this.randomSleep = randomSleep;
        this.lowerLimit = lowerLimit > 2 ? lowerLimit : 2;
        this.upperLimit = upperLimit > 2 ? upperLimit : Long.MAX_VALUE;

        this.primeNumbers = new ArrayList<>();
        this.rnd = new Random(System.nanoTime());
    }

    private void calculatePrimeNumbers() {
        this.primeState = PrimeState.RUNNING;
        for (long i = this.lowerLimit; i < this.upperLimit && !Thread.currentThread().isInterrupted(); i++) {
            if (this.isPrimeNumber(i)) {
                this.primeNumbers.add(i);
            }
        }
        this.primeState = PrimeState.STOPPED;
    }

    private void sleep() {
        try {
            this.toggle();
            if (this.randomSleep) {
                Thread.sleep(this.rnd.nextInt(1001));
            } else {
                Thread.sleep(1);
            }
            this.toggle();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
        this.sleep();
        for (long primeNumber : this.primeNumbers) {
            if (number % primeNumber == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isPrimeNumber(long number) {
        this.sleep();
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

    @Override
    public List<Long> call() {
        this.calculatePrimeNumbers();

        return this.primeNumbers;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public long getHighestPrimeNumber() {
        return this.primeNumbers.isEmpty() ? 0 : this.primeNumbers.get(this.primeNumbers.size() - 1);
    }

    public PrimeState getPrimeState() {
        return this.primeState;
    }

    public final static Set<Long> combine(Prime... primes) {
        Set<Long> primeNumbers = new TreeSet<>();

        for (Prime prime : primes) {
            primeNumbers.addAll(prime.primeNumbers);
        }

        return Collections.unmodifiableSet(primeNumbers);
    }

}
