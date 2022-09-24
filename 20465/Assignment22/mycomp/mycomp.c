/* Mycomp.c - the main program responsible for combining complex computations with user input */
#include <stdio.h> /* for NULL */
#include <stdlib.h> /* for free() */
#include "interface.h"
#include "mycomp.h"

static complex *variables[NUMBER_OF_VARIABLES];

int main() {
    char line[MAX_LINE_LENGTH];
    char *args;
    cmdtype cmd;
    char chararg1, chararg2;
    double numarg1, numarg2;
    complex *result = NULL;

    init_variables();
    
    printf("Welcome to my complex calculator! Ready for calculations...");

    /* Handle user input */
    while((cmd = getcmd(line, &args)) != STOP)
    {
        switch (cmd)
        {
            case READ:
                if (arg(&args, VARIABLE, &chararg1)
                    && arg(&args, NUMBER, &numarg1)
                    && arg(&args, NUMBER, &numarg2)
                    && endofcmd(args))
                    read_comp(get_variable(chararg1), numarg1, numarg2);
                break;
            case PRINT:
                if (arg(&args, VARIABLE, &chararg1)
                    && endofcmd(args))
                    print_comp(get_variable(chararg1));
                break;
            case ADD_COMP:
                if (arg(&args, VARIABLE, &chararg1)
                    && arg(&args, VARIABLE, &chararg2)
                    && endofcmd(args))
                    result = add_comp(get_variable(chararg1), get_variable(chararg2));
                break;
            case SUB_COMP:
                if (arg(&args, VARIABLE, &chararg1)
                    && arg(&args, VARIABLE, &chararg2)
                    && endofcmd(args))
                    result = subtract_comp(get_variable(chararg1), get_variable(chararg2));
                break;
            case MULT_COMP_REAL:
                if (arg(&args, VARIABLE, &chararg1)
                    && arg(&args, NUMBER, &numarg1)
                    && endofcmd(args))
                    result = mult_comp_real(get_variable(chararg1), numarg1);
                break;
            case MULT_COMP_IMG:
                if (arg(&args, VARIABLE, &chararg1)
                    && arg(&args, NUMBER, &numarg1)
                    && endofcmd(args))
                    result = mult_comp_img(get_variable(chararg1), numarg1);
                break;
            case MULT_COMP_COMP:
                if (arg(&args, VARIABLE, &chararg1)
                    && arg(&args, VARIABLE, &chararg2)
                    && endofcmd(args))
                    result = mult_comp_comp(get_variable(chararg1), get_variable(chararg2));
                break;
            case ABS_COMP:
                if (arg(&args, VARIABLE, &chararg1)
                    && endofcmd(args))
                    printf("%.2f", abs_comp(get_variable(chararg1)));
                break;
            default: /* Undefined command name or error accured */
                break;
        }
        /* If recieved a result, print it, free and reset to null*/
        if (result != NULL)
        {
            print_comp(result);
            free(result);
            result = NULL;
        }
    }

    printf("\n\nGoodbye!");

    return 0;
}

/* Returns a pointer to a complex variable. This function's job is not to validate the name. 
If the name is invalid (which shouldn't happen with correct validation), will return NULL */
complex *get_variable(char name)
{
    int pos = name - 'A';
    if (pos >= 0 && pos < NUMBER_OF_VARIABLES)
        return variables[pos];
    
    return NULL;
}

/* Goes through the global array of pointers to complex variables,
* allocates a memory for each and sets their value to 0+0i */
void init_variables(void)
{
    int i;
    for (i=0; i < NUMBER_OF_VARIABLES; i++)
        variables[i] = new_comp();
}
