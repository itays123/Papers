/**
 * Q3 - implementation of a semaphore in C
 */
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>

#define ITERATIONS 5 /* number of iterations */

typedef struct
{
    int status;
    int waiting; /* represents the "semaphore queue". 0 for an empty queue, 1 if the queue is not empty. */
} semaphore;

semaphore s;
sigset_t waitset;

/**
 * Syncronously wait for signal SIGUSR1
 */
void wait_for_signal()
{
    int sig;

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

    /* Signal waitset config */
    sigemptyset(&waitset);
    sigaddset(&waitset, SIGUSR1);           /* init waiting list */
    sigprocmask(SIG_BLOCK, &waitset, NULL); /* Line copied from SignaalsOUSSyncrony example in order to make things work. */
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
        kill(getpid(), SIGUSR1); /* send signal to wake up waiting thread */
        s.waiting = 0;
    }
    else
        s.status++;
}

void *thread_critical_section_test(void *thread_name)
{
    int i;
    char *str = (char *)thread_name;
    for (i = 0; i < ITERATIONS; i++)
    {
        printf("thread %s wants to enter critical section\n", str);
        sem_down();
        printf("thread %s in critical section\n", str);
        sleep(1);
        sem_up();
        printf("thread %s exited critical section\n", str);
        sleep(1);
    }
}

/**
 * Test the semaphore
 */
int main()
{
    pthread_t thread1, thread2;
    int *exit;

    sem_init(1);

    pthread_create(&thread1, NULL, thread_critical_section_test, (void *)"thread1");
    pthread_create(&thread2, NULL, thread_critical_section_test, (void *)"thread2");

    pthread_join(thread1, (void **)&exit);
    pthread_join(thread2, (void **)&exit);
    return 0;
}
