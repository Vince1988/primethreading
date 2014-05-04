# Prime Number Threading
Projectwork for "concepts and methods in programming" class

## Task: Thread Execution

### Version 1
Implement an application containing n threads, that calculate a list of prime numbers (2, 3, 5, 7, 11, …).

Proceed as follows:

* In the main method, start these n threads and let them calculate prime numbers as long as they are not interrupted.
* Also start a calculation in the main method, which calculates the list of prime numbers until the maximum number m.
* When the calculation of prime numbers (2, 3, …, m) is completed in the main method, interrupt all the other running threads.
* When all threads (except the main one) have been terminated, display for each thread the highest prime number that was calculated.

Please note:

* Each thread should sleep randomly between 0 and 1 s between each calculation step.
* Name every thread and distribute the output to the console conveniently in order to observer which thread is running or waiting (thread state).
* To check, if a number is a prime number, use the following method:

```java
public static boolean isPrime(int no) {
    if (no < 2) return false;
    for (int i = 2; i < no; i++)
        if (no % i == 0) return false;
    return true;
}
```

### Version 2
Implement a second version of an application that calculates prime numbers.

Proceed as follows:

* In the main thread, calculate a list of prime numbers until the maximum value m and store these numbers in an array list.
* Recalculate the same prime numbers, but divide the calculation equally between m parallel threads. E.g., when you decide to calculate prime numbers until 1000 with 5 threads, the first thread should calculate prime numbers in the interval 1..200, the second in the interval 201..400, etc.
* Each thread must store the prime number it calculates in a local array list and when it terminates, it should return this list to the caller (use Callable object).
* At program end, all partial lists (one for each thread) must be stored together in an array list which must have the same content as the array list built in the main thread. Check this!

Compare the time needed to execute the calculations with one thread and with n parallel
threads (Hint: Introduce a very short delay (1 ms) between each call of the isPrime method).