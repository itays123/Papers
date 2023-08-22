#include "output.h"
#include "utils.h"
#include "symbols.h" /* For ROW_DATA */
#include <stdio.h>

#define LINE_FORMAT "%.4d A%x-B%x-C%x-D%x-E%x"
#define BITSOF(bits) bits[0], bits[1], bits[2], bits[3], bits[4]
#define NEWLINE_IF_NEEDED(not_needed, file, newline) \
    if (not_needed) \
        not_needed = FALSE; \
    else \
        fputs(newline, file);

/* Write the object file */
static boolean write_ob(char *, word **, int, int *, int);

/* Write the extrnals file if table not empty */
static boolean write_ext(char *, table);

/* Write the entry file if table is not empty */
static boolean write_ent(char *, table);

boolean write_files(char *filename, word **code_image, int icf,
    int *data_image, int dcf, table symbols, table externals)
{
    return write_ob(filename, code_image, icf, data_image, dcf)
        && write_ext(filename, externals)
        && write_ent(filename, symbols);
}

static boolean write_ob(char *filename, word **code_image, int icf, int *data_image, int dcf)
{
    hexbits bits;
    int i, code_image_length = icf - IC_INIT_VALUE;
    FILE *out = fopen_safe(filename, OBJECT_POSTFIX, "w");
    
    /* Print header line */
    fprintf(out, "%d %d", code_image_length, dcf);

    /* Print code image */
    for (i = 0; i < code_image_length; i++)
    {
        fputs("\n", out); /* New line */
        get_bits(bits, code_image[i]);
        fprintf(out, LINE_FORMAT, i + IC_INIT_VALUE, BITSOF(bits));
    }

    /* Print data image */
    bits[0] = Absolute; /* Data words are always absolute */
    for (i = 0; i < dcf; i++)
    {
        fputs("\n", out); /* New line */
        get_integer_hexbits(bits, data_image[i]);
        fprintf(out, LINE_FORMAT, i + icf, BITSOF(bits));
    }

    fclose(out);
    return TRUE;
}

static boolean write_ext(char *filename, table externals)
{
    /* The externals table is independent, therefore if not empty there are external symbols */
    FILE *out;
    table current;
    int base_loc;
    boolean is_first = TRUE;
    if (externals == NULL)
        return TRUE; /* Nothing to write */
    
    out = fopen_safe(filename, EXTERN_POSTFIX, "w");
    current = externals;

    while (current != NULL)
    {
        NEWLINE_IF_NEEDED(is_first, out, "\n\n")
        base_loc = ROW_DATA(current);
        fprintf(out, "%s BASE %d", current->key, base_loc);
        fprintf(out, "\n%s OFFSET %d", current->key, base_loc + 1);
        current = current->next;
    }

    fclose(out);
    return TRUE;
}

static boolean write_ent(char *filename, table symbols)
{
    FILE *out;
    table current;
    int base, offset;
    boolean is_first = TRUE;
    current = symbols;
    while (current != NULL && !IS_ENTRY(current))
        current = current->next;
    
    if (current == NULL) /* Did not find entry symbols */
        return TRUE;
    
    /* Found at least one entry symbol. Create file */
    out = fopen_safe(filename, ENTRY_POSTFIX, "w");

    while(current != NULL)
    {
        if (IS_ENTRY(current))
        {
            NEWLINE_IF_NEEDED(is_first, out, "\n")
            GET_BASE_OFFSET(ROW_DATA(current), base, offset);
            fprintf(out, "%s, %.4d, %.4d", current->key, base, offset);
        }
        current = current->next;
    }

    return TRUE;
}