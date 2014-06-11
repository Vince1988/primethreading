package ch.vincent_genecand.bfh.primethreading.version2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.vincent_genecand.bfh.primethreading.prime.Prime;

public class ControllerV2 {

    private final long limit;
    private final int threads;
    private final long packetSize;

    private final ExecutorService executorServiceSplit;
    private final ExecutorService executorServiceSingle;

    private final List<Future<List<Long>>> futurePrimes;
    private Future<List<Long>> futurePrime;

    private Prime singlePrime;
    private List<Prime> splitPrimes;

    private double singleTime;
    private double splitTime;

    public ControllerV2(int threads, long packetSize) {
        this.threads = threads;
        this.packetSize = packetSize;
        this.limit = this.threads * this.packetSize;

        this.futurePrimes = new ArrayList<>();
        this.splitPrimes = new ArrayList<>();

        executorServiceSplit = Executors.newFixedThreadPool(threads);
        executorServiceSingle = Executors.newFixedThreadPool(1);

        this.initThreads();

        try {
            singleTime = this.singlePrime();
            splitTime = this.splitPrime();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        this.compare();
    }

    private void compare() {
        Set<Long> split = Prime.combine(this.splitPrimes.toArray(new Prime[this.splitPrimes.size()]));
        Set<Long> single = Prime.combine(this.singlePrime);

        System.out.println("==========");
        System.out.println("Compare:");
        System.out.println("==========");
        System.out.println("Single: " + single);
        System.out.println("Split: " + split);
        System.out.println("Equal?: " + single.equals(split));
        System.out.println("Time difference:");
        System.out.println("Single: " + singleTime + " s");
        System.out.println("Split: " + (splitTime - singleTime) + " s");
    }

    private double singlePrime() throws InterruptedException, ExecutionException {
        System.out.println("==========");
        System.out.println("Single:");
        System.out.println("==========");

        long start = System.nanoTime();

        this.futurePrime = executorServiceSingle.submit(singlePrime);
        System.out.println(this.futurePrime.get());

        long end = System.nanoTime();

        executorServiceSingle.shutdownNow();

        double time = (end - start) / 1000000000.0;
        System.out.println("Time: " + time + " s");
        System.out.println();

        return time;
    }

    private double splitPrime() throws InterruptedException, ExecutionException {
        System.out.println("==========");
        System.out.println("Split:");
        System.out.println("==========");

        long start = System.nanoTime();

        for (Prime p : this.splitPrimes) {
            this.futurePrimes.add(executorServiceSplit.submit(p));
        }

        for (Future<List<Long>> p : this.futurePrimes) {
            System.out.println(p.get());
        }

        long end = System.nanoTime();

        executorServiceSplit.shutdownNow();

        double time = (end - start) / 1000000000.0;
        System.out.println("Time: " + time + " s");
        System.out.println();

        return time;
    }

    private void initThreads() {
        this.singlePrime = new Prime("Single Prime", 0, this.limit);

        for (int i = 0; i < this.threads; i++) {
            this.splitPrimes.add(new Prime("Split Prime #" + (i + 1), i * this.packetSize, i * this.packetSize + this.packetSize));
        }

    }
}
