import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents the chopstick collection, responsible for not letting two
 * philosophs use the same stick simultaniusly
 */
public class ChopsticksMonitor {
    private boolean[] taken; // for each stick number, false represents available while true represents not
                             // available.
    private Lock[] stickLocks;
    private Condition[] stickConditions;

    public ChopsticksMonitor() {
        taken = new boolean[PhiliosphThread.NUM_PHILOSOPHS];
        stickLocks = new Lock[PhiliosphThread.NUM_PHILOSOPHS];
        stickConditions = new Condition[PhiliosphThread.NUM_PHILOSOPHS];
        for (int i = 0; i < taken.length; i++) {
            taken[i] = false;
            stickLocks[i] = new ReentrantLock();
            stickConditions[i] = stickLocks[i].newCondition();
        }
    }

    // waits until stick is available for taking, and takes it
    public void acquireStick(int stickNum) {
        stickLocks[stickNum].lock();
        try {
            while (taken[stickNum]) {
                stickConditions[stickNum].await();
            }

            taken[stickNum] = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stickLocks[stickNum].unlock();
        }
    }

    // releases the sticks for other philosophs to use
    public void releaseStick(int stickNum) {
        stickLocks[stickNum].lock();
        try {
            taken[stickNum] = false;
            stickConditions[stickNum].signalAll();
        } finally {
            stickLocks[stickNum].unlock();
        }
    }
}
