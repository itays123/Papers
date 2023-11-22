/**
 * Q3 - implementation of a semaphore in C
 */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>

typedef struct
{
    int status;
    int waiting; /* represents the "semaphore queue". 0 for an empty queue, 1 if the queue is not empty. */
} semaphore;

semaphore s;

/**
 * Syncronously wait for signal SIGUSR1
 */
void wait_for_signal()
{
    int sig;
    sigset_t waitset;
    sigemptyset(&waitset);
    sigaddset(&waitset, SIGUSR1);           /* init waiting list */
    sigprocmask(SIG_BLOCK, &waitset, NULL); /* Line copied from SignaalsOUSSyncrony example in order to make things work. */

    /* waiting happens here */
    if (sigwait(&waitset, &sig) != 0)
    {
        printf("Error when waiting for signal");
        exit(1);
    }
}

/**
 * Initializes the semaphore with a given
 * @param status (0 or 1) represents the number of threads that are allowed to be running at the same time
 */
void sem_init(int status)
{
    if (!status && status != 1)
    {
        printf("Error: status must equal 0 or 1, current status: %d", status);
        exit(1);
    }

    s.status = status;
    s.waiting = 0;
}

/**
 * The down method, as stated in the study guide page 57.
 * This is a syncrounous method that wait for the signal SIGUSER1 if the status of the semaphore is blocked
 */
void sem_down()
{
    if (s.status == 0) /* if semaphore is blocked, mark as waiting and */
    {
        s.waiting = 1;
        wait_for_signal();
    }
    else if (s.status > 0) /* if semaphore is not blocked, block it for future threads. */
        s.status--;
}

/**
 * The up method. Frees the semaphore, or signals another thread to wake up if waiting
 */
void sem_up()
{
    if (s.waiting == 1)
    {
        s.waiting = 0;
        kill(getpid(), SIGUSR1); /* send signal to wake up waiting thread */
    }
    else
        s.status++;
}
