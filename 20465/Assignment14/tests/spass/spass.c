#include "../../assembler/secpass.h"
#include "../../assembler/firstpass.h"
#include "../../assembler/output.h"
#include "../../assembler/symbols.h"
#include <stdio.h>

#define EXECUTE_BREAK_IF_FAIL(func, code_image, ic, symbols, externals) \
    if (!func) \
    { \
        free_word_arr(code_image, ic); \
        free_table(&symbols); \
        free_table(&externals); \
        /* fclose(file); */  \
        return 0; \
    }
        

int main()
{
    word *code_image[MAX_CODE_IMAGE_SIZE];
    int data_image[MAX_CODE_IMAGE_SIZE];
    table symbols = NULL, externals = NULL;
    int ic = IC_INIT_VALUE, dc = 0;
    
    EXECUTE_BREAK_IF_FAIL(
        first_pass(stdin, "input", code_image, &ic, data_image, &dc, &symbols), 
        code_image, ic, symbols, externals)
    rewind(stdin);
    relocate_data_symbols(&symbols, ic);
    EXECUTE_BREAK_IF_FAIL(
        second_pass(stdin, "input", code_image, &symbols, &externals), 
        code_image, ic, symbols, externals)
    EXECUTE_BREAK_IF_FAIL(
        write_files("output", code_image, ic, data_image, dc, symbols, externals),
        code_image, ic, symbols, externals)
    
    free_word_arr(code_image, ic);
    free_table(&symbols);
    free_table(&externals);

    return 0;
}