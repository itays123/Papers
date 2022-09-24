#ifndef _CHARSEQ
#define _CHARSEQ
#include "globals.h" /* For bool */

/* Returns a pointer to the next non-white character in a given string
* or to the end of the string if not found */
char *next_nonwhite(char *);

/* Returns a pointer to the next white character in a given string
* or to the end of string if not found */
char *next_white(char *);

/* Returns a pointer to the next white character or comma in a given string
* or to the end of string if not found */
char *next_white_or_comma(char *);

/* Returns a pointer to the next white character or colon in a given string
or to the end of string if not found */
char *next_white_or_colon(char *);

/* Returns a pointer to the next character that is not a digit
or to the end of string if not found */
char *next_non_digital(char *);

/* Returns a pointer to the next character that is not a digit or a letter
or to the end of string if not found */
char *next_non_alnum(char *);

/* Returns a pointer to the next character in a given string that is equal to a given character,
ot to the end of string if not found  */
char *next(char, char *);

/* Gets a pointer to the start of a substring, and a pointer to the end of it(end exclusive), 
and compares it with a given string.
Return true if equal, false otherwise */
boolean str_equal(char *, char *, char *);

/* Gets a pointer to the start of a substring, and a pointer to the end of it(end exclusive), 
and compares it with a given string.
Return true if before the compared string lexicographically, false otherwise */
boolean str_before(char *, char *, char *);

/* Gets a pointer to the start of a substring, and a pointer to the end of it (end exclusive),
and parses it to an integer if a valid integer (optional sign + decimal digits)
assigns the result to the pointer given to it.
Returns true if succesfully converted to number, false otherwise */
boolean str_to_int(char *, char *, int *);

#endif