#include "../../assembler/command.h"
#include "../../assembler/charsequence.h"
#include <stdio.h>

void test_line(char *);

int main()
{
    char line[MAX_LINE_LENGTH] = "   label: add #14  , symb[r10]";
    printf("starting test...");
    do {
        test_line(line);
        printf("\n\n>");
    } while(fgets(line, MAX_LINE_LENGTH, stdin) != NULL);
    return 0;
}

void test_line(char *line)
{
    char *label_start, *label_end, *opword_start, *opword_end;
    char *operands, *op1_start, *op1_end, *op2_start, *op2_end;
    char *symb_start, *symb_end, *idx_start, *idx_end;
    int operands_to_search;
    boolean label_found;
    opcode op;
    funct ft;
    input_status status, notfound_status;
    addressing_method addr1 = NONE, addr2 = NONE;
    reg reg1 = NON_REG, reg2 = NON_REG;
    int immediate_data1 = 0, immediate_data2 = 0;

    label_start = next_nonwhite(line);
    label_end = next_white_or_colon(label_start);
    puts(line);
    label_found = find_opword(label_start, label_end, &opword_start, &opword_end);
    printf("\nFound label ? %d, opword is %s", label_found, opword_start);
    status = str_to_opcode_funct(opword_start, opword_end, &op, &ft);
    printf("\nRecognized command ? status = %d, opcode = %d, funct = %d", status, op, ft);
    operands_to_search = num_of_operands(op);
    notfound_status = operands_to_search > 1 ? MISSING_OPERAND_REQUIRED_TWO : MISSING_OPERAND_REQUIRED_ONE;
    if (operands_to_search)
    {
        operands = next_nonwhite(opword_end);
        printf("\nApproached operands - %s", operands);
        status = find_operand(&operands, &op1_start, &op1_end, notfound_status);
        printf("\nOperand searched. status = %d, op1 start = `%s`, end = `%s`, operands = `%s`", status, op1_start, op1_end, operands);
        status = get_operand_data(op1_start, op1_end, &addr1, &reg1, &immediate_data1);
        printf("\nData collected. Status = %d, addressing method = %d, register = %d, data = %d", status, addr1, reg1, immediate_data1);
    }
    if (operands_to_search == 1)
    {
        status = end_of_command(op1_end);
        printf("\nChecked end of command. status = %d", status);
    }
    
    if (addr1 == INDEX)
    {
        split_symbol_index(op1_start, &symb_start, &symb_end, &idx_start, &idx_end);
        status = validate_symbol_name(symb_start, symb_end);
        printf("\nSymbol validated. status = %d", status);
    }
    if (addr1 == DIRECT)
    {
        status = validate_symbol_name(op1_start, op1_end);
        printf("\nSymbol validated. status = %d", status);
    }

    if (operands_to_search > 1)
    {
        status = find_operand(&operands, &op2_start, &op2_end, notfound_status);
        printf("\nOperand searched. status = %d, op2 start = `%s`, end = `%s`, operands = `%s`", status, op2_start, op2_end, operands);
        status = get_operand_data(op2_start, op2_end, &addr2, &reg2, &immediate_data2);
        printf("\nData collected. Status = %d, addressing method = %d, register = %d, data = %d", status, addr2, reg2, immediate_data2);
        status = end_of_command(op2_end);
        printf("\nChecked end of command. status = %d", status);
    } 
    if (addr2 == INDEX)
    {
        split_symbol_index(op2_start, &symb_start, &symb_end, &idx_start, &idx_end);
        status = validate_symbol_name(symb_start, symb_end);
        printf("\nSymbol validated. status = %d", status);
    }
    if (addr2 == DIRECT)
    {
        status = validate_symbol_name(op2_start, op2_end);
        printf("\nSymbol validated. status = %d", status);
    }

    if (label_found)
    {
        status = validate_symbol_name(label_start, label_end);
        printf("\nLabel validated, status = %d", status);
    }

    status = check_addressing_methods(op, addr1, addr2);
    printf("\nValidated addressing methods. status = %d", status);
    printf("\nWord line will occupy: 2 + %d + %d", words_by_addr(addr1), words_by_addr(addr2));
    printf("\nPassed - %d", passed(status));
    
}