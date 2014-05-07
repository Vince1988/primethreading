package ch.vincent_genecand.bfh.primethreading.version1;

import java.util.Random;
import java.util.Stack;

public class Prime extends Thread {

    private final Stack<Long> primeNumbers;
    private final long limit;

    private PrimeState primeState;

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
        this.primeNumbers = new Stack<>();
        this.limit = limit;
        this.primeState = PrimeState.STOPPED;
    }

    public long getHighestPrimeNumber() {
        return this.primeNumbers.isEmpty() ? 0 : this.primeNumbers.lastElement();
    }

    private void calculatePrimeNumbers() {
        for (long i = 2; i < this.limit && this.primeState != PrimeState.STOPPED; i++) {
            if (this.isNextPrimeNumber(i)) {
                this.primeNumbers.add(i);
            }
        }

        this.primeState = PrimeState.STOPPED;
    }

    private void sleep() {
        try {
            this.toggle();
            Thread.sleep(new Random().nextInt(1001));
            this.toggle();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void toggle() throws InterruptedException {
        switch (this.primeState) {
        case RUNNING:
            this.primeState = PrimeState.SLEEPING;
            break;
        case SLEEPING:
            this.primeState = PrimeState.RUNNING;
            Thread.sleep(100);
            break;
        default:
            break;
        }
    }

    private boolean isNextPrimeNumber(long number) {
        for (long primeNumber : this.primeNumbers) {
            this.sleep();
            if (number % primeNumber == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        this.primeState = PrimeState.RUNNING;
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
        return this.getName();
    }

    @Override
    public void interrupt() {
        this.primeState = PrimeState.STOPPED;
    }

    public PrimeState getPrimeState() {
        return this.primeState;
    }

}
