package ch.vincent_genecand.bfh.primethreading.version2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import ch.vincent_genecand.bfh.primethreading.prime.Prime;

public class ControllerV2 {

    private final long limit;
    private final int threads;
    private final long packetSize;

    private final List<FutureTask<List<Long>>> futurePrimes;
    private final List<Prime> primes;
    private FutureTask<List<Long>> futurePrime;
    private Prime prime;

    private ThreadGroup splitPrimeGroup;

    public ControllerV2(int threads, long packetSize) {
        this.threads = threads;
        this.packetSize = packetSize;
        this.limit = this.threads * this.packetSize;

        this.futurePrimes = new ArrayList<>();
        this.primes = new ArrayList<>();

        this.initThreads();

        try {
            this.singlePrime();
            this.splitPrime();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.compare();
    }

    private void compare() {
        Set<Long> split = Prime.combine(this.primes.toArray(new Prime[this.primes.size()]));
        Set<Long> single = Prime.combine(this.prime);

        System.out.println("==========");
        System.out.println("Compare:");
        System.out.println("==========");
        System.out.println("Single: " + single);
        System.out.println("Split: " + split);
        System.out.println("Equal?: " + single.equals(split));
    }

    private void singlePrime() throws InterruptedException, ExecutionException {
        System.out.println("==========");
        System.out.println("Single:");
        System.out.println("==========");

        long start = System.nanoTime();

        this.futurePrime.run();
        System.out.println(this.futurePrime.get());

        long end = System.nanoTime();

        double time = (end - start) / 1000000000.0;
        System.out.println("Time: " + time + " s");
        System.out.println();
    }

    private void splitPrime() throws InterruptedException, ExecutionException {
        System.out.println("==========");
        System.out.println("Split:");
        System.out.println("==========");

        long start = System.nanoTime();

        for (FutureTask<List<Long>> p : this.futurePrimes) {
            p.run();
        }
        for (FutureTask<List<Long>> p : this.futurePrimes) {
            System.out.println(p.get());
        }

        long end = System.nanoTime();

        double time = (end - start) / 1000000000.0;
        System.out.println("Time: " + time + " s");
        System.out.println();
    }

    private void initThreads() {
        this.prime = new Prime(null, "Single Prime", 0, this.limit);
        this.futurePrime = new FutureTask<>(this.prime);

        this.splitPrimeGroup = new ThreadGroup("SplitPrimeGenerator");
        for (int i = 0; i < this.threads; i++) {
            Prime p = new Prime(this.splitPrimeGroup, "SPG #" + (i + 1), i * this.packetSize, i * this.packetSize + this.packetSize);
            this.primes.add(p);
            this.futurePrimes.add(new FutureTask<>(p));
        }
    }
}
