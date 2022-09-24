
#define MAX_LINE_LENGTH 100
#define NUMBER_OF_VARIABLES 6

enum { FALSE = 0, TRUE = 1 };

typedef enum { EMPTY, VARIABLE, NUMBER } argtype;

typedef enum { NONE, ERROR, READ, PRINT, ADD_COMP, SUB_COMP, MULT_COMP_REAL, MULT_COMP_IMG, MULT_COMP_COMP, ABS_COMP, STOP } cmdtype;

/* Requests the user for a new input line, and collects the command from the line.
uses the set of charcaters the first argument is pointing to, assuming it had no less than MAX_LINE_LENGTH chacaters.
points the second argument to the function to the first non-white, non-comma character after the command.
handles the validation for the STOP command, so no invalid stop lines will be executed */
cmdtype getcmd(char*, char**);

/* Gets a pointer to somewhere along a string, assuming it's after the command.
in the characters between the pointer and the next comma char,
it ignores white chars and finds an argument in a desired type
Moves the pointer to beyond the comma, or to a \0 character, whichever comes first
returns TRUE if the argument is valid, FALSE otherwise */
int arg(char**, argtype, void*);

/* Checks if a pointer points to the end of a command (only white chars are left),
assuming the command has more than one character before the pointer.
Edge case: if the character before the pointer is a comma, 
like in the command "read_comp A,3.5,-3," it will mark the end of command as false */
int endofcmd(char*);