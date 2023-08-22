/* Interface.c - 
* Responsible for handling and processing input from the user 
*/
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h> /* for atof, malloc */
#include "interface.h"
#include "nextchar.h"

static char *splitcmd(char *, char **);

static cmdtype strtocmd(char []);

/* Requests the user for a new input line, and collects the command from the line.
uses the set of charcaters the first argument is pointing to, assuming it had no less than MAX_LINE_LENGTH chacaters.
points the second argument to the function to the first non-white, non-comma character after the command.
handles the validation for the STOP command, so no invalid stop lines will be executed */
cmdtype getcmd(char *line, char **rest)
{
    char *command;
    char *scanRes;
    cmdtype result;
    int linelen;

    printf("\n\n > ");

    scanRes = fgets(line, MAX_LINE_LENGTH, stdin);

    /* handle scanRes */
    if (scanRes == NULL) /* End of file occured */
    {
        printf("\nError: not exited using the stop command");
        return STOP;
    }

    printf("\n < %s", line);
    linelen = strlen(line);

    /* find command */
    command = splitcmd(line, rest);

    if (command == NULL)
        return NONE;
    
    result = strtocmd(command);

    /* Handle special case of STOP command */
    if (result == STOP)
    {
        if (strlen(command) == linelen) /* Edge case of stop + EOF */
            return STOP;
        return endofcmd(*rest) ? STOP : ERROR;

    }
    
    /* point the rest pointer to the first argument */
    *rest = next_nonwhite(*rest);
    if (*rest != NULL && **rest != ',')
        return result;
    
    /* Illegal comma or no args found */
    printf("\n Error: %s", ((*rest)? "Illegal comma" : "missing argument"));
    return ERROR;
}


static int var_name_validate(char *);

/* Gets a pointer to somewhere along a string, assuming it's after the command.
in the characters between the pointer and the next comma char,
it ignores white chars and finds an argument in a desired type
Moves the pointer to beyond the comma, or to a \0 character, whichever comes first
returns TRUE if the argument is valid, FALSE otherwise */
int arg(char **args, argtype type, void *to_assign)
{
    /* Start and end will mark the start and end of the argument string without white chars and commas */
    char *start, *end;
    char temp_end; /* To temporarly replace the char after the argument with \0 */
    start = next_nonwhite(*args);
    if (!(*start) || *start == ',')
    {
        /* We assume illegal commas before the arguments have already been searched */
        printf("\nError: %s", ((*start)? "Multiple consecutive commas" : "missing argument"));
        return FALSE;
    }

    if (type == NUMBER)
    {
        end = next_non_number(start);
        if (!isspace(*end) && *end != ',')
        {
            /* Argument should be a number but found a non-numeric char before argument string ended */
            printf("\nError: invalid parameter - not a number");
            return FALSE;
        }
    }
    else
        end = next_white_or_comma(start);

    /* Assign the result, as the desired type, in the given pointer.
    Note: Curr is now pointing to the first character in the sequence which isn't a part of the arg */
    temp_end = *end;
    *end = '\0';

    if (type == NUMBER)
        *(double *)to_assign = atof(start);

    if (type == VARIABLE) {
        if (var_name_validate(start))
            *(char *)to_assign = *start;
        else
        {
            printf("\nError: undefined complex variable");
            return FALSE;
        }
    }
    
    *end = temp_end;

    /* Before we return true, move args the pointer to beyond the next comma or to the end of the line */
    end = next_nonwhite(end);

    if (!(*end))
    {
        *args = end;
        return TRUE;
    }
    else if (*end == ',')
    {
        *args = end + 1;
        return TRUE;
    }

    /* Found another argument without a separating comma */
    printf("\nError: Missing comma");
    return FALSE;
}

/* Checks if a pointer points to the end of a command (only white chars are left),
assuming the command has more than one character before the pointer.
Edge case: if the character before the pointer is a comma, 
like in the command "read_comp A,3.5,-3," it will mark the end of command as false */
int endofcmd(char *rest)
{
    char *c;

    c = next_nonwhite(rest); /* c should be pointing to end of string */
    
    /* Handle comma edge case */
    if (*c || *(rest-1) == ',')
    {
        printf("\nError: Extraneous text after end of command");
        return FALSE;
    }
    
    return TRUE;
}

/* Returns a pointer to the first non-white character in the line given, NULL if not found
Terminates the first word in the line with '\0' if other charcters after it were found
points the second pointer given in the function to the charcter after the '\0' if placed, NULL if not*/
static char *splitcmd(char *line, char **rest)
{
    char *start, *end;
    start = next_nonwhite(line);
    
    /* If line is empty, return NULL */
    if (!(*start))
        return NULL;
    
    end = next_white(start);
    
    /* Found a space charcater, or line ended */
    if (!(*end))
    {
        *rest = (char *) NULL;
        return start;
    }
    
    /* Split line */
    *end = '\0';
    *rest = end + 1;
    return start;
}


static cmdtype strtocmd(char command[])
{
    if (!strcmp(command, "read_comp"))
        return READ;
    else if (!strcmp(command, "print_comp"))
        return PRINT;
    else if (!strcmp(command, "add_comp"))
        return ADD_COMP;
    else if (!strcmp(command, "sub_comp"))
        return SUB_COMP;
    else if (!strcmp(command, "mult_comp_real"))
        return MULT_COMP_REAL;
    else if (!strcmp(command, "mult_comp_img"))
        return MULT_COMP_IMG;
    else if (!strcmp(command, "mult_comp_comp"))
        return MULT_COMP_COMP;
    else if (!strcmp(command, "abs_comp"))
        return ABS_COMP;
    else if (!strcmp(command, "stop"))
        return STOP;
    else
    {
        printf("\nError: undefined command name");
        return ERROR;
    }
}

/* Validates the string name of the variable. Returns a boolean value.
If name is invalid, it prints an error message to the user */
static int var_name_validate(char *start)
{
    return strlen(start) == 1 && (*start >= 'A') && (*start < 'A' + NUMBER_OF_VARIABLES);
}
