package ch.vincent_genecand.bfh.primethreading.version1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Prime extends Thread {

    private final List<Long> primeNumbers;
    private final long limit;

    private PrimeState state;

    public Prime(String name) {
        this(name, Long.MAX_VALUE);
    }

    public Prime(String name, long limit) {
        this(null, name, limit);
    }

    public Prime(ThreadGroup threadGroup, String name) {
        this(threadGroup, name, Long.MAX_VALUE);
    }

    public Prime(ThreadGroup threadGroup, String name, long limit) {
        super(threadGroup, name);
        this.primeNumbers = new ArrayList<>();
        this.limit = limit;
        this.state = PrimeState.STOPPED;
    }

    public long getHighestPrimeNumber() {
        return this.primeNumbers.size() > 0 ? this.primeNumbers.get(this.primeNumbers.size() - 1) : 0;
    }

    private void calculatePrimeNumbers() {
        for (long i = 2; i < this.limit && this.state != PrimeState.STOPPED; i++) {
            if (this.isNextPrimeNumber(i)) {
                this.primeNumbers.add(i);
            }
            this.sleep();
        }

        this.state = PrimeState.STOPPED;
    }

    private void sleep() {
        if (this.state == PrimeState.RUNNING) {
            this.state = PrimeState.SLEEPING;
        }
        try {
            Thread.sleep(new Random().nextInt(1001));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (this.state == PrimeState.SLEEPING) {
            this.state = PrimeState.RUNNING;
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
    public void run() {
        this.state = PrimeState.RUNNING;
        this.calculatePrimeNumbers();
    }

    public final static boolean isPrimeNumber(long number) {

        if (number < 2) {
            return false;
        }

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;

    }

    @Override
    public String toString() {
        return " | " + this.getName() + ": " + this.getHighestPrimeNumber() + " (" + this.state + ") | ";
    }

    @Override
    public void interrupt() {
        this.state = PrimeState.STOPPED;
    }

    private enum PrimeState {
        RUNNING, SLEEPING, STOPPED;
    }

}
