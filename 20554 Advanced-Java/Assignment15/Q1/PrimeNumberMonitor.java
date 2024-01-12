import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * A monitor to split jobs of finding prime numbers
 */
public class PrimeNumberMonitor {

    public static final int END_VALUE = -1; // value to return from getNext() when there are no more values left
    public static final int START_VALUE = 2; // start calculating from 2

    private Collection<Integer> primeNumberList;
    private int nextInt;
    private int processesDone;
    private int m; // number to find primes up to
    private int n; // number of processes allowed

    /**
     * I chose implementing the lock using multiple locks instead of the
     * `syncronized` keyword.
     * The reason for that is that a thread getting a next number to calculate
     * should not affect other threads adding to the prime number list.
     */
    private Lock primeNumberListLock, nextIntLock, processesDoneLock;
    private Condition processesDoneCondition;

    // construct a new prime number monitor
    public PrimeNumberMonitor(int m, int n) {
        this.m = m;
        this.n = n;
        this.primeNumberList = new TreeSet<Integer>();
        this.nextInt = START_VALUE; // first integer to calculate from
        this.processesDone = 0;

        this.primeNumberListLock = new ReentrantLock();
        this.nextIntLock = new ReentrantLock();
        this.processesDoneLock = new ReentrantLock();
        this.processesDoneCondition = this.processesDoneLock.newCondition();
    }

    public int getNext() {
        // get next int and update it
        nextIntLock.lock();
        try {
            if (nextInt <= m)
                return nextInt++;
        } finally {
            nextIntLock.unlock();
        }

        // no more job for current process
        processesDoneLock.lock();
        try {
            processesDone++;
            if (processesDone == n)
                processesDoneCondition.signalAll();

            return END_VALUE;
        } finally {
            processesDoneLock.unlock();
        }
    }

    public void addPrime(int prime) {
        primeNumberListLock.lock();
        try {
            primeNumberList.add(prime);
        } finally {
            primeNumberListLock.unlock();
        }
    }

    public void printPrimeList() {
        // Wait until all processes are done
        processesDoneLock.lock();
        try {
            while (processesDone < n)
                processesDoneCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            processesDoneLock.unlock();
        }

        // All processes done. Print list.
        System.out.println("Prime numbers until " + m + ":");
        primeNumberListLock.lock();
        try {
            for (Integer prime : primeNumberList) {
                System.out.println(prime);
            }
        } finally {
            primeNumberListLock.unlock();
        }

    }

}
