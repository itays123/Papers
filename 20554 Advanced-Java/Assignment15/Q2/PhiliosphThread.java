import java.util.Random;

/**
 * Represents a single philosoph thread capable of eating and thinking
 */
public abstract class PhiliosphThread extends Thread {

    public static final int NUM_PHILOSOPHS = 5;
    public static final int MAX_WAIT = 5 * 1000; // 5 seconds

    private static Random rand = new Random();

    // wait for threads to run
    private static void waitRandomTime() {
        try {
            Thread.sleep((int) (rand.nextDouble() * MAX_WAIT));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int philoId; // id is a number between 0 and 4
    private ChopsticksMonitor chopsticks; // the chopstick collection
    private boolean stop;

    public PhiliosphThread(int id, ChopsticksMonitor chopsticks) {
        this.philoId = id;
        this.chopsticks = chopsticks;
        this.stop = false;
    }

    public int getPhiloId() {
        return philoId;
    }

    /*
     * Each philisoph should aquire two sticks with numbers:
     * 0 => 0 and 4
     * 1 => 0 and 1
     * 2 => 1 and 2
     * 3 => 2 and 3
     * 4 => 3 and 4
     * and in general:
     * i => i and (i-1) % 5
     * According to the instructions, the stick with the lower number should be
     * acquired before.
     */

    // represents the first of the sticks the philosoph should acquire
    public int getFirstChopstickNum() {
        return Math.min((philoId + NUM_PHILOSOPHS - 1) % NUM_PHILOSOPHS, philoId);
    }

    // represents the second of the sticks the philosoph should acquire
    public int getSecondChopstickNum() {
        return Math.max((philoId + NUM_PHILOSOPHS - 1) % NUM_PHILOSOPHS, philoId);
    }

    /**
     * Methods to run in subclasses handling UI
     * Possible implementations:
     * 1. Printing state using System.out.println
     * 2. Updating a graphical user interface (implemented here)
     */

    // run before stick acquire
    protected abstract void beforeStickScquire(int stickNum);

    // run after atick acquire
    protected abstract void afterStickAquire(int stickNum);

    // run when eating
    protected abstract void onEating();

    // run after stick release
    protected abstract void afterStickRelease(int stickNum);

    // ru when thinking
    protected abstract void onThinking();

    @Override
    public void run() {
        super.run();
        while (!this.stop) {

            // acquire sticks to eat
            beforeStickScquire(getFirstChopstickNum());
            chopsticks.acquireStick(getFirstChopstickNum());
            afterStickAquire(getFirstChopstickNum());

            beforeStickScquire(getSecondChopstickNum());
            chopsticks.acquireStick(getSecondChopstickNum());
            afterStickAquire(getSecondChopstickNum());

            // eating
            onEating();
            waitRandomTime();

            // release sticks
            chopsticks.releaseStick(getFirstChopstickNum());
            afterStickRelease(getFirstChopstickNum());

            chopsticks.releaseStick(getSecondChopstickNum());
            afterStickRelease(getSecondChopstickNum());

            // thinking
            onThinking();
            waitRandomTime();

        }
    }

    public void stopAfterRelease() {
        this.stop = true;
    }

}
