/* Second pass - generate the externals table and finish generating the code image */
#include "secpass.h"
#include "command.h" /* For split_symbol_index, input_status, ... */
#include "charsequence.h" /* For all methods in the module */
#include "symbols.h" /* For add_extern_reference, find_symbol_addr */
#include "utils.h" /* For GET_BASE_OFFSET */

static input_status spass_process_line(char *, word **, int *, table *, table *);

#define REQUIRES_SYMBOL(addr) addr == DIRECT || addr == INDEX
#define POINT_SYMBOL_START_END(addr, op_start, op_end, symb_start, symb_end, index_start, index_end) \
    if (addr == DIRECT) \
    { \
        symb_start = op_start; \
        symb_end = op_end; \
    } \
    else \
    { \
        split_symbol_index(op_start, &symb_start, &symb_end, &index_start, &index_end); \
    }

boolean second_pass(FILE *source, char *filename, word **code_image, 
    table *symbols_table, table *externals_table)
{
    int ic = IC_INIT_VALUE, line_num;
    input_status status;
    boolean is_success = TRUE; /* No need for print flag in this pass - no warnings. If not successful, printed before */
    char line[MAX_LINE_LENGTH];
    for (line_num = 1; fgets(line, MAX_LINE_LENGTH, source) != NULL; line_num++)
    {
        status = spass_process_line(line, code_image, &ic, symbols_table, externals_table);
        if (status != PASS) /* No second pass warnings */
        {
            /* New line if needed  */
            if (!is_success)
                puts("");
            is_success = FALSE;
            printf(ERROR_FORMAT, filename, ASSEMBLY_POST_MACRO_POSTFIX, line_num, get_error(status));
            status = PASS;
        }
    }
    return is_success;
}

/* Get a operands string, the code image, and the current instruction counter, as well as the symbols and externals tables.
Process it using the arguments. Complete missing code words and the external table
Return any input status stumbed upon */
static input_status spass_process_operation(char *, word **, int *, table *, table *);

static input_status spass_process_line(char *line, word **code_image, int *ic, table *symbols, table *externals)
{
    input_status status;
    char *label_start_maybe, *label_end_maybe, *opstart, *opend, *operands, *symb_start, *symb_end;

    /* Find opword. We don't care if the label exists or not */
    label_start_maybe = next_nonwhite(line);
    PASS_IF_EMPTY_OR_COMMENT(label_start_maybe)
    label_end_maybe = next_white_or_colon(label_start_maybe);
    find_opword(label_start_maybe, label_end_maybe, &opstart, &opend);
    operands = next_nonwhite(opend);

    if (str_equal(opstart, opend, ".entry"))
    {
        find_operand(&operands, &symb_start, &symb_end, INST_REQ_OPERAND); /* Valid, checked in first pass */
        EXECUTE(status, mark_entry_symbol(symbols, symb_start, symb_end))
        return PASS;
    }

    if (*opstart == '.') /* First pass took care of all data instructions except .entry */
        return PASS;
    
    return spass_process_operation(operands, code_image, ic, symbols, externals);
}

/* Core functionality - enter a symbol start and end (end exclusive) and its addressing method, 
as well as the base word index in the code image.
get its refrence and allocate the address words for it.
If external reference, add to the externals table */
static input_status process_operand_symbol_reference(char *, char *, word **, int *, table *, table *, input_status);

static input_status spass_process_operation(char *operands, word **code_image, int *ic, table *symbols, table *externals)
{
    input_status status;
    char *symb_start, *symb_end, *index_start, *index_end,
        *op1_start, *op1_end, *op2_start, *op2_end;
    word *opcode, *opdata;
    int length, opcount, i;
    addressing_method addr1, addr2;

    i = *ic - IC_INIT_VALUE;
    opcode = code_image[i++];
    length = LENGTH(opcode);
    opcount = num_of_operands(INTEGER_DATA(opcode));
    *ic = *ic + length; /* Move ic to next spot. Won't need it anymore since we have index */
    
    if (length <= BASE_OP_LENGTH) /* If length <= 2, no operands contain symbols. */
        return PASS;

    /* Collect operands and addressing methods */
    opdata = code_image[i++]; /* There must be an opdata word */
    find_operand(&operands, &op1_start, &op1_end, PASS);
    if (opcount > 1)
    {
        find_operand(&operands, &op2_start, &op2_end, PASS);
        addr1 = SRC_ADRS(opdata);
        addr2 = DEST_ADRS(opdata);
    }
    else /* One operand */
    {
        addr1 = DEST_ADRS(opdata); /* When there is one operand, it is referenced in the destination addressing method */
        addr2 = NONE;
    }

    /* Process symbols if needed */
    if (REQUIRES_SYMBOL(addr1))
    {
        POINT_SYMBOL_START_END(addr1, op1_start, op1_end, symb_start, symb_end, index_start, index_end);
        EXECUTE(status, process_operand_symbol_reference(symb_start, symb_end, code_image, &i, 
            symbols, externals, SYMBOL_NOT_FOUND_FIRST_OP));
    }
    else
        i += words_by_addr(addr1);

    if (REQUIRES_SYMBOL(addr2))
    {
        POINT_SYMBOL_START_END(addr2, op2_start, op2_end, symb_start, symb_end, index_start, index_end);
        EXECUTE(status, process_operand_symbol_reference(symb_start, symb_end, code_image, &i, 
            symbols, externals, SYMBOL_NOT_FOUND_SECOND_OP));
    }
    /* No need to move i, won't use it anymore */

    return PASS;
}

static input_status process_operand_symbol_reference(char *symb_start, char *symb_end, word **code_image, int *i, 
    table *symbols, table *externals, input_status notfound_status)
{
    word *base, *offset;
    int symb_addr, base_word_location;
    symb_addr = find_symbol_addr(symbols, symb_start, symb_end);

    if (symb_addr == ADDR_NOT_FOUND)
        return notfound_status;

    /* Keep in mind that if any data symbol has pointed to 0, it is now pointing to icf */
    if (symb_addr == ADDR_EXTERN) 
    {
        base_word_location = *i + IC_INIT_VALUE;
        add_extern_reference_word(externals, symb_start, symb_end, base_word_location);
    }

    alloc_address_words(&base, &offset, symb_addr, symb_addr == ADDR_EXTERN ? Extern : Relocatable);
    code_image[(*i)++] = base;
    code_image[(*i)++] = offset;
    return PASS;
}