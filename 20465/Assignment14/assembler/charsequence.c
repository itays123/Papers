/* Charsequence.c - working with strings and pointers */
#include <ctype.h> /* For isalnum, isdigit */
#include <string.h> /* For strcmp */
#include <stdlib.h> /* For atoi */
#include "charsequence.h"

/******** NEXT FUNCTIONS DEFINITIONS */
#define WHITE(c) ((c) == ' ' || (c) == '\t' || (c) == '\n')
#define NON_WHITE(c) (!(WHITE(c)))
#define WHITE_OR_COMMA(c) (WHITE(c) || (c) == ',')
#define WHITE_OR_COLON(c) (WHITE(c) || (c) == ':')
#define NON_DIGITAL(c) (!isdigit(c))
#define NON_ALNUM(c) (!isalnum(c))

#define DEFINE_NEXT_FUNCTION(func_name, filter) \
char *func_name(char *str)\
{\
    char *p;\
    for (p = str; *p && !filter(*p); p++);\
    return p;\
}

DEFINE_NEXT_FUNCTION(next_white, WHITE)
DEFINE_NEXT_FUNCTION(next_nonwhite, NON_WHITE)
DEFINE_NEXT_FUNCTION(next_white_or_comma, WHITE_OR_COMMA)
DEFINE_NEXT_FUNCTION(next_white_or_colon, WHITE_OR_COLON)
DEFINE_NEXT_FUNCTION(next_non_digital, NON_DIGITAL)
DEFINE_NEXT_FUNCTION(next_non_alnum, NON_ALNUM)

char *next(char target, char *str)
{
    char *p;
    for (p = str; *p && *p != target; p++);
    return p;
}

/********* STRING UTILITIES FUNCTIONS ****************/

boolean str_equal(char *start, char *end, char *compare_to)
{
    int result;
    char temp = *end;
    *end = '\0';
    result = !strcmp(start, compare_to);
    *end = temp;
    return result ? TRUE : FALSE;
}

boolean str_before(char *start, char *end, char *compare_to)
{
    int result;
    char temp = *end;
    *end = '\0';
    result = strcmp(start, compare_to);
    *end = temp;
    return result < 0 ? TRUE : FALSE;
}

boolean str_to_int(char *start, char *end, int *to_assign)
{
    char temp = *end;
    
    /* Validate numeric string */
    if ((!isdigit(*start) && *start != '-' && *start != '+') /* start can point to a sign */
        || next_non_digital(start + 1) != end) /* rest of chars in substring must be digits */
        return FALSE;
    
    *end = '\0';
    *to_assign = atoi(start);
    *end = temp;
    return TRUE;
}
