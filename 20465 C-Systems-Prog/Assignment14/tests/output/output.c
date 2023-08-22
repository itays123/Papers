#include "../../assembler/output.h"
#include "../../assembler/word.h"
#include "../../assembler/command.h"
#include "../../assembler/table.h"
#include "../../assembler/symbols.h"
#include <string.h>

#define IC_TO_IDX(ic) ((*ic)++)-IC_INIT_VALUE
#define MOCK_PTRS(symb) symb, symb + strlen(symb)

void build_code_image(word **, int *);
void build_data_image(int *, int *);
void build_externals_table(table *);
void build_symbols_table(table *);

int main()
{
    word *code_image[MAX_CODE_IMAGE_SIZE];
    int data_image[MAX_CODE_IMAGE_SIZE];
    int icf = IC_INIT_VALUE, dcf = 0;
    table symbols_table, externals_table;

    build_code_image(code_image, &icf);
    build_data_image(data_image, &dcf);
    build_externals_table(&externals_table);
    build_symbols_table(&symbols_table);

    write_files("full", code_image, icf, data_image, dcf, symbols_table, externals_table);
    write_files("no_tables", code_image, icf, data_image, dcf, NULL, NULL);
    free_word_arr(code_image, icf);
    free_table(&symbols_table);
    free_table(&externals_table);
    return 0;
}

void build_words_from_command(word **, int *, char *, reg , reg , addressing_method, addressing_method, int, int);

void build_code_image(word **img, int *icf)
{
    char commands[][5] = {
        "add",
        "prn",
        "lea",
        "inc",
        "mov",
        "sub",
        "bne",
        "cmp",
        "bne",
        "dec",
        "sub",
        "stop"
    };
    build_words_from_command(img, icf, commands[0], r3, NON_REG, REGISTER_DIRECT, DIRECT, 0, 147);
    build_words_from_command(img, icf, commands[1], NON_REG, NON_REG, NONE, IMMEDIATE, 0, 48);
    build_words_from_command(img, icf, commands[2], NON_REG, r6, DIRECT, REGISTER_DIRECT, 148, 0);
    build_words_from_command(img, icf, commands[3], NON_REG, r6, NONE, REGISTER_DIRECT, 0, 0);
    build_words_from_command(img, icf, commands[4], r3, NON_REG, REGISTER_DIRECT, DIRECT, 0, 0);
    build_words_from_command(img, icf, commands[5], r1, r4, REGISTER_DIRECT, REGISTER_DIRECT, 0, 0);
    build_words_from_command(img, icf, commands[6], NON_REG, NON_REG, NONE, DIRECT, 0, 103);
    build_words_from_command(img, icf, commands[7], NON_REG, NON_REG, DIRECT, IMMEDIATE, 150, -6);
    build_words_from_command(img, icf, commands[8], NON_REG, r15, NONE, INDEX, 0, 151);
    build_words_from_command(img, icf, commands[9], NON_REG, NON_REG, NONE, DIRECT, 0, 152);
    build_words_from_command(img, icf, commands[10], r10, r14, INDEX, REGISTER_DIRECT, 153, 0);
    build_words_from_command(img, icf, commands[11], NON_REG, NON_REG, NONE, NONE, 0, 0);
}

void build_words_from_command(word **img, int *icf, char *opword, reg reg1, reg reg2, 
    addressing_method addr1, addressing_method addr2, int data1, int data2)
{
    opcode oc;
    funct ft;
    int operand_count, length, src_length, dest_length;
    word *base, *offset;
    str_to_opcode_funct(opword, opword + strlen(opword), &oc, &ft);
    src_length = words_by_addr(addr1);
    dest_length = words_by_addr(addr2);
    operand_count = num_of_operands(oc);
    length = 1;
    length += operand_count > 0 ? 1 : 0;
    length += src_length;
    length += dest_length;
    img[IC_TO_IDX(icf)] = new_opcode_word(oc, length);
    
    if (operand_count == 0)
        return;
    img[IC_TO_IDX(icf)] = new_opdata_word(ft, reg1, addr1, reg2, addr2);

    if (src_length == 1) /* New int data word */
        img[IC_TO_IDX(icf)] = new_data_word(data1);
    if(src_length == 2) /* New address words */
    {
        alloc_address_words(&base, &offset, data1, data1 == ADDR_EXTERN ? Extern : Relocatable);
        img[IC_TO_IDX(icf)] = base;
        img[IC_TO_IDX(icf)] = offset;
    }

    if (dest_length == 1) /* New int data word */
        img[IC_TO_IDX(icf)] = new_data_word(data2);
    if(dest_length == 2) /* New address words */
    {
        alloc_address_words(&base, &offset, data2, data2 == ADDR_EXTERN ? Extern : Absolute);
        img[IC_TO_IDX(icf)] = base;
        img[IC_TO_IDX(icf)] = offset;
    }
}

void alloc_string(char *, int *, int *);
void alloc_int_arr(int *, int *, int *);

void build_data_image(int *img, int *dcf)
{
    char *str1 = "hello", *str2 = "world";
    int arr1[] = {1, 33, 46, 165, 999, -1}, arr2[] = {-6, -1};
    alloc_string(str1, img, dcf);
    alloc_int_arr(arr1, img, dcf);
    alloc_string(str2, img, dcf);
    alloc_int_arr(arr2, img, dcf);
}

void alloc_string(char *str, int *img, int *dcf)
{
    int i;
    for (i = 0; i < strlen(str) + 1; i++)
    {
        img[*dcf] = str[i];
        (*dcf)++;
    }
}

void alloc_int_arr(int arr[], int *img, int *dcf)
{
    int i;
    for (i = 0; arr[i] != -1; i++)
    {
        img[*dcf] = arr[i];
        (*dcf)++;
    }
}

void build_externals_table(table *externals)
{
    char references[][4] = {
        "W",
        "K",
        "END"
    };
    char *ref1 = references[0], *ref2 = references[1], *ref3 = references[2];
    add_extern_reference_word(externals, MOCK_PTRS(ref1), 123);
    add_extern_reference_word(externals, MOCK_PTRS(ref2), 104);
    add_extern_reference_word(externals, MOCK_PTRS(ref3), 109);
    add_extern_reference_word(externals, MOCK_PTRS(ref1), 117);
    add_extern_reference_word(externals, MOCK_PTRS(ref2), 127);
}

void build_symbols_table(table *symbols)
{
    char symbol_names[][6] = {
        "Hello",
        "symb",
        "supp"
    };
    char *symb1 = symbol_names[0], *symb2 = symbol_names[1], *symb3 = symbol_names[2];
    add_symbol(symbols, MOCK_PTRS(symb1), CODE, 102);
    add_symbol(symbols, MOCK_PTRS(symb2), DATA, 150);
    add_symbol(symbols, MOCK_PTRS(symb3), CODE, 104);
    mark_entry_symbol(symbols, MOCK_PTRS(symb1));
    mark_entry_symbol(symbols, MOCK_PTRS(symb2));
}