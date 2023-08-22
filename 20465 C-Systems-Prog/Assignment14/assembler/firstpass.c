/* First pass - Generate data image, symbols table and partial code image */
#include "firstpass.h"
#include "command.h" /* For every method in the module */
#include "charsequence.h" /* For every method in the module */
#include "symbols.h" /* For add_symbol, add_extern_symbol */

#define CREATE_DATA_WORD_IF_POSSIBLE(code_image, i, length, data) \
    if (length == 1) \
        code_image[i++] = new_data_word(data); \
    else \
        i+=length;

/* Process a single line. Return an input status */
static input_status fpass_process_line(char *, word **, int *, int *, int *, table *);

boolean first_pass(FILE *src, char *filename, 
    word **code_image, int *ic, int *data_image, int *dc, table *symbols_table)
{
    boolean is_success = TRUE, first_output = TRUE;
    int line_num;
    char line[MAX_LINE_LENGTH];
    input_status status;
    for (line_num = 1; fgets(line, MAX_LINE_LENGTH, src) != NULL; line_num++)
    {
        status = fpass_process_line(line, code_image, ic, data_image, dc, symbols_table);
        if (!passed(status)) /* Not PASS or a warning */
            is_success = FALSE;
        if (status != PASS) /* Error or warning. Notify user */
        {
            /* Add new line if needed */
            if (first_output)
                first_output = FALSE;
            else
                puts("");
            printf(ERROR_FORMAT, filename, ASSEMBLY_POST_MACRO_POSTFIX, line_num, get_error(status));
            status = PASS; /* Restore state */
        }
    }
    if (!first_output && is_success)
        puts(""); /* Put a new line after warnings */
    return is_success;
}

/*************** Method declarations for processing a line **********/

/* Get two pointers to the start and end of the opword,if it starts with a dot and not entry or extern,
as well as the data image and the current data counter.
process a .string or .data instruction, return any input status stumbed upon */
static input_status process_instruction(char *, char *, int *, int *);

/* Get two pointers to the start and end of the opword, if not starts with a dot.,
as well as the code image and the current instruction counter.
process it as much as possible (add any words except for symbol references), return any input status stumbed upon */
static input_status process_operation(char *, char *, word **, int *);

/* Check the grammar of a line containing .entry or .extern instruction
redirect the last two pointers to point to the start and end of the symbol.
return any input status stumbed upon */
static input_status check_entry_extern_inst(char *, char *, char **, char **);

static input_status fpass_process_line(char *line, word **code_image, int *ic, 
    int *data_image, int *dc, table *symbols_table)
{
    input_status status;
    char *label_start_maybe, *label_end_maybe, *opstart, *opend, *symb_start, *symb_end;
    boolean label_exists;
    int label_points_to;
    symbol_purpose label_purpose;

    /* Find label & opword */
    label_start_maybe = next_nonwhite(line);
    PASS_IF_EMPTY_OR_COMMENT(label_start_maybe)
    label_end_maybe = next_white_or_colon(label_start_maybe);
    label_exists = find_opword(label_start_maybe, label_end_maybe, &opstart, &opend);

    /* Find line purpose and redirect */
    if (str_equal(opstart, opend, ".entry"))
    {
        EXECUTE(status, check_entry_extern_inst(opstart, opend, &symb_start, &symb_end))
        /* Do nothing. Entry instructions will be handles in the second pass */
        return label_exists ? WARN_LABEL_TO_ENTRY : PASS;
    }

    if (str_equal(opstart, opend, ".extern"))
    {
        EXECUTE(status, check_entry_extern_inst(opstart, opend, &symb_start, &symb_end))
        /* Valid extern instruction. Add it */
        EXECUTE(status, add_extern_symbol(symbols_table, symb_start, symb_end))
        return label_exists ? WARN_LABEL_TO_EXTERN : PASS;
    }

    if (*opstart == '.') /* Another instruction */
    {
        label_points_to = *dc;
        label_purpose = DATA;
        EXECUTE(status, process_instruction(opstart, opend, data_image, dc))
    }
    else /* Operation */
    {
        label_points_to = *ic;
        label_purpose = CODE;
        EXECUTE(status, process_operation(opstart, opend, code_image, ic))
    }

    /* Line was handles. Add label to the symbol table if needed */
    if (label_exists)
        EXECUTE(status, add_symbol(symbols_table, label_start_maybe, label_end_maybe, label_purpose, label_points_to))
    return PASS;
}

static input_status process_instruction(char *opstart, char *opend, int *data_image, int *dc)
{
    char *str, *argstart, *argend, *curr_char;
    int curr_arg;
    input_status status;

    if (str_equal(opstart, opend, ".data"))
    {
        str = next_nonwhite(opend);
        if (!(*str))
            return NO_NUMBERS_FOR_DATA_INST;
        if (*str == ',')
            return ILLEGAL_COMMA_AFTER_INST;
        
        while (*str)
        {
            EXECUTE(status, find_operand(&str, &argstart, &argend, EXPECTED_NUMBER_AFTER_COMMA))
            if (!str_to_int(argstart, argend, &curr_arg))
                return INVALID_OPERAND_NOT_NUMBER;
            data_image[(*dc)++] = curr_arg;
        }
        if (*argend == ',')
            return EXPECTED_NUMBER_AFTER_COMMA;
        EXECUTE(status, end_of_command(argend))
        return PASS;
    }
    
    if (str_equal(opstart, opend, ".string"))
    {
        argstart = next_nonwhite(opend);
        if (!(*argstart))
            return STRING_INST_EXPECTS_OPERAND;
        if (*argstart != '"')
            return STRING_MUST_BEGIN_WITH_QUOT;

        argend = next('"', argstart + 1);
        if (!(*argend))
            return STRING_MUST_END_WITH_QUOT;
        
        for (curr_char = argstart + 1; curr_char < argend; curr_char++)
            data_image[(*dc)++] = *curr_char;
        /* Terminate with 0 */
        data_image[(*dc)++] = '\0';
        return PASS;
    }

    return UNREC_INSTRUCTION;
}

static input_status process_operation(char *opstart, char *opend, word **code_image, int *ic)
{
    input_status status, notfound_status;
    opcode oc;
    funct ft;
    reg reg1 = NON_REG, reg2 = NON_REG;
    addressing_method addr1 = NONE, addr2 = NONE;
    int data1, data2, i, length1 = 0, length2 = 0, opcount, length;
    char *str, *op1_start, *op1_end, *op2_start, *op2_end;

    EXECUTE(status, str_to_opcode_funct(opstart, opend, &oc, &ft))
    opcount = num_of_operands(oc);

    if (opcount == 0)
    {
        EXECUTE(status, end_of_command(opend))
        code_image[(*ic) - IC_INIT_VALUE] = new_opcode_word(oc, 1); /* Length is 1 - only the opcode word */
        (*ic)++;
        return PASS;
    }

    /* Check illegal commas */
    str = next_nonwhite(opend);
    if (*str == ',')
        return ILLEGAL_COMMA_AFTER_OPERATION;
    
    /* At least one operand must be found */
    notfound_status = opcount > 1 ? MISSING_OPERAND_REQUIRED_TWO : MISSING_OPERAND_REQUIRED_ONE;
    EXECUTE(status, find_operand(&str, &op1_start, &op1_end, notfound_status))
    EXECUTE(status, get_operand_data(op1_start, op1_end, &addr1, &reg1, &data1))
    length1 = words_by_addr(addr1);

    if (opcount > 1)
    {
        EXECUTE(status, find_operand(&str, &op2_start, &op2_end, notfound_status))
        EXECUTE(status, get_operand_data(op2_start, op2_end, &addr2, &reg2, &data2))
        EXECUTE(status, end_of_command(op2_end))
        length2 = words_by_addr(addr2);
    }
    else 
        EXECUTE(status, end_of_command(op1_end))
    
    EXECUTE(status, check_addressing_methods(oc, addr1, addr2))

    /* Finished checks. Create words */
    i = (*ic) - IC_INIT_VALUE; /* Next available index in code image */
    length = BASE_OP_LENGTH + length1 + length2; /* Opcode word + opdata word + length of args */
    code_image[i++] = new_opcode_word(oc, length);

    /* Create second word. If one operand, it will be referred as the dest operand */
    if (opcount > 1)
        code_image[i++] = new_opdata_word(ft, reg1, addr1, reg2, addr2);
    else
        code_image[i++] = new_opdata_word(ft, NON_REG, IMMEDIATE /* 0 */, reg1, addr1);
    
    /* Create immediate data word if possible */
    CREATE_DATA_WORD_IF_POSSIBLE(code_image, i, length1, data1);
    CREATE_DATA_WORD_IF_POSSIBLE(code_image, i, length2, data2);

    *ic = *ic + length;
    return PASS;
}

static input_status check_entry_extern_inst(char *opstart, char *opend, char **symb_start, char **symb_end)
{
    input_status status;
    char *str = next_nonwhite(opend);
    if (*str == ',')
        return ILLEGAL_COMMA_AFTER_INST;
    
    EXECUTE(status, find_operand(&str, symb_start, symb_end, INST_REQ_OPERAND))
    EXECUTE(status, end_of_command(*symb_end))

    return PASS;
}