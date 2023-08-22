/* Nextchar.c - 
* responsible for working with pointers to characters and searching charcters within strings
*/
#include <ctype.h> /* for isdigit, isspace, etc. */
#include "nextchar.h"

static char *next(int (*)(char), char *);

static int nonwhite(char);
static int white_or_comma(char);
static int white(char);
static int non_number(char);

/* Returns a pointer to the next non-white character in a given string
* or to the end of the string if not found */
char *next_nonwhite(char *str)
{
    return next(nonwhite, str);
}

/* Returns a pointer to the next white character in a given string
* or to the end of string if not found */
char *next_white(char *str)
{
    return next(white, str);
}

/* Returns a pointer to the next white character or comma in a given string
* or to the end of string if not found */
char *next_white_or_comma(char *str)
{
    return next(white_or_comma, str);
}

/* Returns a pointer to the next character that is not a digit, a decimal point or a minus sign, or to the end of string if not found */
char *next_non_number(char *str)
{
    return next(non_number, str);
}

/* Returns a pointer to the next character in a given string 
* that holds a certain condition given in a function returning a boolean value 
* or a pointer to the end of the string if not found*/
char *next(int (*filter)(char), char *str)
{
    char *p;
    /* For each charcter that doesn't hold the condition, skip to next */
    for (p = str; *p && !filter(*p); p++);
    return p;
}

/**************************** FILTERS **********************/

/* Returns 0 value if char is white, non-zero otherwise */
static int nonwhite(char c)
{
    return !isspace(c);
}

/* Returns non-zero value if char is white, 0 otherwise */
static int white(char c)
{
    return isspace(c);
}

/* Returns 0 value if char is non-white and not a comma, non-zero otherwise */
static int white_or_comma(char c)
{
    return isspace(c) || c == ',';
}

/* Returns 0 value if char is a digit, a decimal point or a minus sign, non-zero otherwise */
static int non_number(char c)
{
    return !isdigit(c) && c != '.' && c != '-';
}

