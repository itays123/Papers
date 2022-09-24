
/* Returns a pointer to the next non-white character in a given string
* or to the end of the string if not found */
char *next_nonwhite(char *);

/* Returns a pointer to the next white character in a given string
* or to the end of string if not found */
char *next_white(char *);

/* Returns a pointer to the next white character or comma in a given string
* or to the end of string if not found */
char *next_white_or_comma(char *);

/* Returns a pointer to the next character that is not a digit, a decimal point or a minus sign, or to the end of string if not found */
char *next_non_number(char *);
