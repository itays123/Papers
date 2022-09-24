/* Macro.c - processing macros
Assumptions:
- There are no multiple macro definitions
- There are no nested macro definitions
- Macro definition must end
- Macro is not named after a register, instruction or operation */
#include "macro.h"
#include "table.h"
#include "charsequence.h"
#include "utils.h"

/* For increased readability */
#define MACRO_ATTR(node) ((node)->data).macro
#define POSITION(node) MACRO_ATTR(node).position
#define LINES(node) MACRO_ATTR(node).lines

/* Read a single line from the file, and break from the function if too long.
According to the instructions, if a line is too long the program must notify about that,
and the macro pass is the first time we read from the file */
#define BREAK_IF_TOO_LONG(line, file) \
    if (!(*next('\n', line)) && (*line != '\n') && !feof(file)) \
    { \
        printf("\nFatal: cannot process line with more than %d characters", MAX_LINE_LENGTH); \
        return FALSE; \
    }
#define RUN_BREAK_IF_ERROR(func) \
    if (!(func)) \
        return FALSE;

/* Process a single line of the macro file. Write result to out file.
Return EOF if end of file, FALSE if line too long, TRUE otherwise,
Assuming all macro definitions have an end */
static int mpass_process_line(FILE *, char *, table *, FILE *);

boolean macro_pass(FILE *source, FILE *out)
{
    table macros_table = NULL;
    char line[MAX_LINE_LENGTH];
    int result = TRUE;
    while (result != EOF && result)
        result = mpass_process_line(source, line, &macros_table, out);
    /* Found a line too long, or end of file */
    return result == EOF;       
}

/* Count lines until encountered endm not including endm. 
Save the position of the first line, as well as the line count and the key, in the macros table 
Assumptions:
- There are no multiple macro definitions
- There are no nested macro definitions
- Macro definition must end
Return FALSE if encountered a too long line, TRUE otherwise */
static int mpass_process_definition(FILE *, table *, char *, char *);

/* Use the saved definition of the macro to write it in the output file 
Assumptions:
- Macro is not named after a register, instruction or operation */
static int mpass_process_reference(FILE *, char *, table , FILE *);

static int mpass_process_line(FILE *source, char *line, table *macros, FILE *out)
{
    char *opstart, *opend, *keystart, *keyend;
    table existing_definition;
    if (fgets(line, MAX_LINE_LENGTH, source) == NULL)
        return EOF;
    BREAK_IF_TOO_LONG(line, source)
    
    opstart = next_nonwhite(line);
    opend = next_white(opstart);

    if (str_equal(opstart, opend, "macro"))
    {
        keystart = next_nonwhite(opend);
        keyend = next_white(keystart);
        RUN_BREAK_IF_ERROR(
            mpass_process_definition(source, macros, keystart, keyend)
        ) /* Assumption: Macro definition WILL end */
    }

    else if ((existing_definition = find_item(macros, opstart, opend)) != NULL) 
        mpass_process_reference(source, line, existing_definition, out);
    
    else /* Not a macro definition nor a macro reference */
        fputs(line, out);
    return TRUE;
}

static int mpass_process_definition(FILE *source, table *macros, 
    char *keystart, char *keyend)
{
    /* We would like to preserve the memory of the line before (with the macro name) to prevent memory leaks */
    static char line[MAX_LINE_LENGTH];
    fpos_t current_position;
    row_data macro_data;
    unsigned int linespan = 0;
    char *opstart, *opend;

    /* When calling, the line of the definition has been read from the source. 
    Its position now points to the first line of the macro */
    fgetpos(source, &current_position);

    /* Go through every line and count lines until endm */
    do
    {
        fgets(line, MAX_LINE_LENGTH, source); /* Macro definition must end, so no need to check eof */
        BREAK_IF_TOO_LONG(line, source)
        linespan++;
        opstart = next_nonwhite(line);
        opend = next_white(opstart);
    } while (!str_equal(opstart, opend, "endm"));

    /* Counted the line with endm. Decrease one from linespan */
    linespan--;

    /* Add macro definition. We assume there are no two macros with the same name */
    macro_data.macro.position = current_position;
    macro_data.macro.lines = linespan;
    add_item(macros, keystart, keyend, macro_data);

    return TRUE;
}

static int mpass_process_reference(FILE *source, char *line, table macro, FILE *out)
{
    fpos_t temp;
    int i;
    fgetpos(source, &temp);
    fsetpos(source, &POSITION(macro));

    /* Read specified lines from source, and put in out file */
    for(i = 0; i < LINES(macro); i++)
    {
        fgets(line, MAX_LINE_LENGTH, source); /* The line must be there since we've already read it when defining the macro */
        fputs(line, out);
    }
    
    fsetpos(source, &temp); /* Restore position for next read */
    return 0;
}
