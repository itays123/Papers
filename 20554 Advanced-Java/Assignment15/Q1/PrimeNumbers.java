// main class
public class PrimeNumbers {
    public static void main(String[] args) throws Exception {
        printPrimes(1000, 10);
    }

    // Print all prime numbers up to m with n parallel working threads
    public static void printPrimes(int m, int n) {
        PrimeNumberMonitor monitor = new PrimeNumberMonitor(m, n);
        PrimeNumberThread[] threads = new PrimeNumberThread[n];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new PrimeNumberThread(monitor);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        monitor.printPrimeList();
    }

}
