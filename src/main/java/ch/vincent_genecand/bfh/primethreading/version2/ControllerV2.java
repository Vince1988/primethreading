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

    private final List<Prime> singlePrime;
    private final List<Prime> splitPrimes;

    private double singleTime;
    private double splitTime;

    public ControllerV2(int threads, long packetSize) {
        this.threads = threads;
        this.packetSize = packetSize;
        this.limit = this.threads * this.packetSize;

        this.singlePrime = new ArrayList<>();
        this.splitPrimes = new ArrayList<>();

        this.initThreads();

        try {
            this.singleTime = this.executePrimes(this.singlePrime);
            this.splitTime = this.executePrimes(this.splitPrimes);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        this.compare();
    }

    private void compare() {
        Set<Long> split = Prime.combine(this.splitPrimes.toArray(new Prime[this.splitPrimes.size()]));
        Set<Long> single = Prime.combine(this.singlePrime.toArray(new Prime[this.singlePrime.size()]));

        System.out.println("==========");
        System.out.println("Compare:");
        System.out.println("==========");
        System.out.println("Single: " + single);
        System.out.println("Split: " + split);
        System.out.println("Equal?: " + single.equals(split));
        System.out.println("Time difference:");
        System.out.println("Single: " + this.singleTime + " s");
        System.out.println("Split: " + (this.splitTime - this.singleTime) + " s");
    }

    private double executePrimes(List<Prime> primes) throws InterruptedException, ExecutionException {
        System.out.println("==========");
        System.out.println("# " + primes.size() + " Threads:");
        System.out.println("==========");

        long start = System.nanoTime();

        ExecutorService executorService = Executors.newFixedThreadPool(primes.size());
        List<Future<List<Long>>> futurePrimes = executorService.invokeAll(primes);

        executorService.shutdown();
        while (executorService.isTerminated()) {
            // wait until all is done
        }

        long end = System.nanoTime();

        double time = (end - start) / 1000000000.0;

        for (Future<List<Long>> futurePrime : futurePrimes) {
            System.out.println(futurePrime.get());
        }

        System.out.println("Time: " + time + " s");
        System.out.println();

        return time;
    }

    private void initThreads() {
        this.singlePrime.add(new Prime("Single Prime", 0, this.limit));

        for (int i = 0; i < this.threads; i++) {
            this.splitPrimes.add(new Prime("Split Prime #" + (i + 1), i * this.packetSize, i * this.packetSize + this.packetSize));
        }

    }
}
