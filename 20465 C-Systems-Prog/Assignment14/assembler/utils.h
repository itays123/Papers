#ifndef _UTILS
#define _UTILS
#include <stdio.h> /* For FILE */
#include "globals.h" /* For hexbits */

/*************************** Macro definitions **************************/
#define STORABLE(integer) ((integer) & 0xFFFF) /* Keep 16 LSBs */
#define HEXBIT_MASK 0xF
#define BASE_OFFSET_UNIT 16
#define GET_BASE_OFFSET(addr, base, offset)\
    offset = addr % BASE_OFFSET_UNIT; \
    base = addr - offset;

/***************************** Function headers *****************************/

/* Allocate the size of bytes needed in memory, and check if allocation was successful
If not successful, exit */
void *malloc_safe(int);

/* Gets two strings and allocates a new string with the contents of the two strings joined */
char *strcat_safe(char *, char *);

/* Gets two pointers to the start and end (end exclusive) of a string and copies it to an allocated 
location in memory. Return pointer to memory*/
char *strcpy_safe(char *, char *);

/* Opens a file with a name, postfix and permissions specified,
assigns it to the pointer to FILE * given,
and returns TRUE if opened sucessfully, false otherwise */
boolean fopen_check(FILE **, char *, char *, char *);

/* Opens a file with a name,postfix and permissions specified safely */
FILE *fopen_safe(char *, char *, char *);

/* Converts an integer given to a sequence of 4 hexbits, 
and stores it in the 4 LSBs of the hexbit array given */
void get_integer_hexbits(hexbits, int);


#endif