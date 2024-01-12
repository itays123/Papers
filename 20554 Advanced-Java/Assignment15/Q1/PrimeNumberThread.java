public class PrimeNumberThread extends Thread {

    // actual heavy calculation. Check if `num` provided is a prime number
    private static boolean isPrime(int num) {
        for (int i = PrimeNumberMonitor.START_VALUE; i < num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    private PrimeNumberMonitor monitor;

    // construct a new thread
    public PrimeNumberThread(PrimeNumberMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        super.run();

        // for every available value, make calculation and return result
        for (int value = monitor.getNext(); value != PrimeNumberMonitor.END_VALUE; value = monitor.getNext()) {
            if (isPrime(value))
                monitor.addPrime(value);
        }
    }

}
