#include "../../assembler/firstpass.h"
#include "../../assembler/word.h"
#include "../../assembler/symbols.h"
#include "../../assembler/output.h"
#include <stdio.h>

#define REQ_FILLING(adrs) ((adrs) == DIRECT || (adrs) == INDEX)

static void mock_spass_fill_words(char *, word **, int *);

int main()
{
    word *code_image[MAX_CODE_IMAGE_SIZE];
    int data_image[MAX_CODE_IMAGE_SIZE];
    int ic = IC_INIT_VALUE, dc = 0, icf;
    table symbols;
    char line[MAX_LINE_LENGTH];
    boolean is_success;
    is_success = first_pass(stdin, "input", code_image, &ic, data_image, &dc, &symbols);

    if (!is_success)
    {
        free_word_arr(code_image, ic);
        free_table(&symbols);
        return 0;
    }

    icf = ic;
    ic = IC_INIT_VALUE;
    rewind(stdin);
    relocate_data_symbols(&symbols, icf);

    while (fgets(line, MAX_LINE_LENGTH, stdin) != NULL)
        mock_spass_fill_words(line, code_image, &ic);
    
    write_files("output", code_image, icf, data_image, dc, symbols, NULL);
    free_word_arr(code_image, icf);
    free_table(&symbols);
    return 0;
}

static void mock_spass_fill_words(char *line, word **code_image, int *ic)
{
    word *opcode, *opdata, *base, *offset;
    char *label_start_maybe, *label_end_maybe, *opstart, *opend;
    int operand_count, i = *ic - IC_INIT_VALUE, length;
    addressing_method src_adrs, dest_adrs;

    /* Find label & opword */
    label_start_maybe = next_nonwhite(line);
    if (!(*label_start_maybe) || *label_start_maybe == ';')
        return;
    label_end_maybe = next_white_or_colon(label_start_maybe);
    find_opword(label_start_maybe, label_end_maybe, &opstart, &opend);

    if (*opstart == '.') /* Instruction, nothing to fill. Return */
        return;
    
    opcode = code_image[i++];
    operand_count = num_of_operands(INTEGER_DATA(opcode));
    length = LENGTH(opcode);

    if (length <= 2) /* Word is filled, nothing to do */
    {
        *ic = *ic + length;
        return;
    }
    
    /* Length is greater than two. Opdata word exists */
    opdata = code_image[i++];
    src_adrs = SRC_ADRS(opdata);
    dest_adrs = DEST_ADRS(opdata);
    
    /* Create src words if needed */
    if (REQ_FILLING(src_adrs))
    {
        alloc_address_words(&base, &offset, 0, Extern);
        code_image[i++] = base;
        code_image[i++] = offset;
    }
    else /* Figure out how much i should be moved */
    {
        if (operand_count > 1) /* Source words might exist */
            i += words_by_addr(src_adrs);
    }

    /* Create dest words if needed */
    if (REQ_FILLING(dest_adrs))
    {
        alloc_address_words(&base, &offset, 0, Extern);
        code_image[i++] = base;
        code_image[i++] = offset;
    }

    *ic = *ic + length;
}