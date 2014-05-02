package ch.vincent_genecand.bfh.primethreading;

import java.util.ArrayList;
import java.util.List;

import ch.vincent_genecand.bfh.primethreading.version1.Prime;

final class Main {

    private Main() {

    }

    public static void main(String[] args) {
        List<Prime> primes = new ArrayList<>();
        ThreadGroup tg = new ThreadGroup("Unlimited Prime Generators");
        Prime prime = new Prime("Limited Prime Generator", 25);

        for (int i = 0; i < 5; i++) {
            primes.add(new Prime(tg, "#" + i));
        }

        prime.start();
        for (Prime p : primes) {
            p.start();
        }

        while (prime.isAlive()) {
            try {
                System.out.print(prime);
                for (Prime t : primes) {
                    System.out.print(t);
                }
                System.out.println();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        tg.interrupt();

        System.out.print(prime);
        for (Prime t : primes) {
            System.out.print(t);
        }
    }
}
