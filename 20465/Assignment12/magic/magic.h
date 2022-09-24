#include <stdio.h>

#define N 5

/* Booleans */
#define TRUE 1
#define FALSE 0

/* Magic square type definition */
typedef int square[N][N];

/* Scans the standard input and builds the square from N^2 given numbers 
* without any limitations on the format of them. Returns TRUE if square is finished, false otherwise
* If there's an error in the input, the function will output it */
int buildSquare(square );

/* Prints the square in a nice format */
void printSquare(square );

/* Checks if every value in the square is between 1 and N*N and unique (only appears once)
Will return TRUE if condition holds, FALSE otherwise */
int checkRangeUniqueValues(square );

/* Sums every row, column and diagon. 
* Will return the sum if it's the same across all rows, columns and diagons, NULL otherwise */
int checkSquareSum(square );