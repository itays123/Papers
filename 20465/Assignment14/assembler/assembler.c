/* Assembler.c: Main program, process assembly files */
#include "globals.h"
#include "macro.h"
#include "firstpass.h"
#include "secpass.h"
#include "symbols.h"
#include "table.h"
#include "word.h"
#include "utils.h"
#include "output.h"
#include <stdio.h>

/* Process a single assembly file, given its name.
Return TRUE if processed successfully, FALSE otherwise */
static boolean process_file(char *);\

int main(int argc, char **argv)
{
    int i;
    boolean success = TRUE;

    /* Notify error if no file is specified */
    if (argc == 1)
    {
        printf("Usage: %s [...files]\n", argv[0]);
        return 0;
    }
    
    for (i = 1; i < argc; i++) /* No need to process the first argument */
    {
        printf("\n\nProcessing file: %s%s\n", argv[i], ASSEMBLY_POSTFIX);
        success = process_file(argv[i]);
        printf("\nFile processing %s\n\n", success ? "finished successfully." : "failed.");
    }

    return 0;
}

/* For increased code readability */
#define END_FILE_PROCESS(code_img, ic, symbols, externals, file, result) \
    free_word_arr(code_img, ic); \
    free_table(&symbols); \
    free_table(&externals); \
    fclose(file); \
    return result; 
#define RUN_TERMINATE_IF_FAIL(func, code_img, ic, symbols, externals, file) \
    if (!(func)) \
    {\
        END_FILE_PROCESS(code_img, ic, symbols, externals, file, FALSE) \
    }
#define OPEN_SOURCE_FILE(fp, filename, postfix) \
    if (!fopen_check(fp, filename, postfix, "r")) \
    { \
        printf("Error: Cannot open %s%s, file doesn't exist", filename, postfix); \
        return FALSE; \
    }

static boolean process_file(char *filename)
{
    int ic = IC_INIT_VALUE, dc = 0;
    table symbols = NULL, externals = NULL;
    int data_img[MAX_CODE_IMAGE_SIZE];
    word *code_img[MAX_CODE_IMAGE_SIZE];
    FILE *source, *macro_out;
    boolean success;

    /* Macro pass */
    OPEN_SOURCE_FILE(&source, filename, ASSEMBLY_POSTFIX);
    macro_out = fopen_safe(filename, ASSEMBLY_POST_MACRO_POSTFIX, "w");
    success = macro_pass(source, macro_out);
    fclose(source);
    fclose(macro_out);
    if (!success)
        return FALSE;
    
    /* First and second pass */
    OPEN_SOURCE_FILE(&source, filename, ASSEMBLY_POST_MACRO_POSTFIX);
    RUN_TERMINATE_IF_FAIL(
        first_pass(source, filename, code_img, &ic, data_img, &dc, &symbols),
        code_img, ic, symbols, externals, source
    )
    relocate_data_symbols(&symbols, ic);
    rewind(source);
    RUN_TERMINATE_IF_FAIL(
        second_pass(source, filename, code_img, &symbols, &externals),
        code_img, ic, symbols, externals, source
    )
    RUN_TERMINATE_IF_FAIL(
        write_files(filename, code_img, ic, data_img, dc, symbols, externals),
        code_img, ic, symbols, externals, source
    )
    END_FILE_PROCESS(code_img, ic, symbols, externals, source, TRUE);
}