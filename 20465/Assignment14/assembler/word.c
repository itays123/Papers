/* Word.h - dealing with the word structure */
#include "word.h"
#include "utils.h" /* For malloc_safe, get_integer_hexbits */
#include <stdlib.h> /* For free */

/* Allocates a word according to the type given.
ARE will be set to the memory_status given */
static word *new_word(word_type, memory_status);

word *new_opcode_word(opcode op, int length)
{
    word *result = new_word(OPCODE, Absolute);
    INTEGER_DATA(result) = op;
    LENGTH(result) = length;
    return result;
}

word *new_opdata_word(funct ft, reg src_reg, addressing_method src_adrs,
                    reg dest_reg, addressing_method dest_adrs)
{
    word *result = new_word(OPDATA, Absolute);
    FUNCT_OF(result) = ft;
    SRC_REG(result) = src_reg == NON_REG ? r0 : src_reg;
    SRC_ADRS(result) = src_adrs;
    DEST_REG(result) = dest_reg == NON_REG ? r0 : dest_reg;
    DEST_ADRS(result) = dest_adrs;
    return result;
}

word *new_data_word(int data)
{
    word *result = new_word(INTDATA, Absolute);
    INTEGER_DATA(result) = data;
    return result;
}

void alloc_address_words(word **base_word, word **offset_word, int address, memory_status ARE)
{
    int base, offset;
    GET_BASE_OFFSET(address, base, offset)
    *base_word = new_word(INTDATA, ARE);
    INTEGER_DATA(*base_word) = base;
    *offset_word = new_word(INTDATA, ARE);
    INTEGER_DATA(*offset_word) = offset;
}

void get_bits(hexbits bits, word *source)
{
    opcode op;
    addressing_method src_adrs, dest_adrs;
    reg dest_reg;
    bits[0] = ARE_OF(source);

    switch (TYPEOF(source))
    {
        case OPCODE:
            op = INTEGER_DATA(source);
            get_integer_hexbits(bits, 1 << op);
            break;
        
        case INTDATA:
            get_integer_hexbits(bits, INTEGER_DATA(source));
            break;
        
        case OPDATA:
            bits[1] = FUNCT_OF(source);
            bits[2] = SRC_REG(source);
            /* The next hexbit is the two bits of src_adrs and the first two of dest_reg */
            src_adrs = SRC_ADRS(source);
            dest_reg = DEST_REG(source);
            bits[3] = (src_adrs << 4 | dest_reg) >> 2;
            /* The next hexbit is the last two bits of dest_reg and the two of dest_adrs */
            dest_adrs = DEST_ADRS(source);
            bits[4] = (dest_reg << 2 | dest_adrs) & HEXBIT_MASK;
            break;
    }
    
}

void free_word_arr(word **words, int count)
{
    int i;
    for (i = 0; i < count; i++)
    {
        free(words[i]);
    }
}

static word* new_word(word_type type, memory_status ARE)
{
    word *result = malloc_safe(sizeof(word));
    /* In both structs, type and ARE are in the same location. */
    TYPEOF(result) = type;
    ARE_OF(result) = ARE;
    return result;
}