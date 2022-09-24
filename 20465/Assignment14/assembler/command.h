#ifndef _COMMAND
#define _COMMAND
#include "globals.h"
#define MAX_SYMBOL_LENGTH 31
#define REGISTER_NAME_MIN_LENGTH 2
#define REGISTER_NAME_MAX_LENGTH 3
#define MIN_OPERATION_LENGTH 3
#define MAX_OPERATION_LENGTH 4

/* Useful macros, to share between the passes */
#define EXECUTE(status, func) \
    if ((status = func) != PASS) \
        return status;
#define PASS_IF_EMPTY_OR_COMMENT(first_nonwhite) \
    if (!(*(first_nonwhite)) || *(first_nonwhite) == ';') \
        return PASS;
#define ERROR_FORMAT "%s%s:%d - %s"

/***************** Type definitions *******************/

typedef enum input_statuses {
    PASS,
    /* Warnings */
    WARN_LABEL_TO_ENTRY,
    WARN_LABEL_TO_EXTERN,

    /* Label name errors */
    TOO_LONG_SYMBOL_NAME,
    ILLEGAL_CHAR_IN_BEGINNING_OF_SYMBOL,
    ILLEGAL_CHARS_IN_SYMBOL_NAME,
    SYMBOL_NAMED_LIKE_OPERATION,
    SYMBOL_NAMED_LIKE_REGISTER,
    SYMBOL_ALREADY_DEFINED,

    /* First pass - structure of line errors */
    LABEL_TO_EMPTY_LINE,
    MULTIPLE_CONSECUTIVE_COMMAS,
    MISSING_COMMA_BETWEEN_OPERANDS,
    EXTRANEOUS_TEXT,

    /* First pass - instruction structure */
    UNREC_INSTRUCTION,
    ILLEGAL_COMMA_AFTER_INST,
    INST_REQ_OPERAND,
    STRING_INST_EXPECTS_OPERAND,
    STRING_MUST_BEGIN_WITH_QUOT,
    STRING_MUST_END_WITH_QUOT,
    NO_NUMBERS_FOR_DATA_INST,
    INVALID_OPERAND_NOT_NUMBER,
    EXPECTED_NUMBER_AFTER_COMMA,

    /* First pass - operation structure */
    UNREC_OPERATION,
    ILLEGAL_COMMA_AFTER_OPERATION,
    MISSING_OPERAND_REQUIRED_ONE,
    MISSING_OPERAND_REQUIRED_TWO,
    INVALID_ADRS_METHOD_FIRST_OP,
    INVALID_ADRS_METHOD_SECOND_OP,
    INVALID_NUMBER_FOR_IMMD_ADDRESSING,
    INVALID_REGISTER_FOR_INDEX_ADDRESSING,

    /* Second pass - error in symbol references */
    UNREC_SYMBOL_FOR_ENTRY,
    SYMBOL_NOT_FOUND_FIRST_OP,
    SYMBOL_NOT_FOUND_SECOND_OP,
    EXTERNAL_ENTRY_SYMBOL

} input_status;

/*********************** Module Methods ***********************/

/* Gets a pointer to the start and end of a subtring (end exclusive), and two pointers to pointers of characters.
If the end of the substring is a colon (:), searches the next word after the substring end 
and assigns it to the pointers given.
If not a colon, assigns the start and end of the substring to the two other pointers.
Return TRUE if there was a colon, FALSE otherwise */
boolean find_opword(char *, char *, char **, char **);

/* Gets a pointer to the start and end of a substring,
compares it to given strings and assigns their opcode and funct values to two pointers given.
returns 
- UNREC_OPERATION if operation not found
- PASS otherwise */
input_status str_to_opcode_funct(char *, char *, opcode *, funct *);

/* Gets a pointer to a pointer to somewhere along a string, 
assuming it's after a comma if there should have been one.
Assigns the pointer to the next non-whitespace character (if found) to the second argument,
and the next whitespace or comma after it to the third argument.
Repoints the pointer in the first argument to beyond the next comma if found, or to the end of string if not.
Returns:
- MULTIPLE_CONSECUTIVE_COMMAS, if found a comma before any non-whitespace characters
- MISSING_COMMA_BETWEEN_OPERANDS, if found a non-whitespace characer before a comma in the repointing sequence,
- not found status, the fourth parameter, if not found a non-whitespace parameter 
- PASS, in any other case */
input_status find_operand(char **, char **, char **, input_status );

/* Gets an opcode integer and returns the number of operands this operation requires */
int num_of_operands(opcode);

/* Gets two pointers to the start and end of a substring (end exclusive),
assuming it had no whitespace characters in its start and end.
Find the corresponding addressing method of the operand substring and assigns it to the corresponding pointer given.
If addressing method contains a register (register-direct or index), assign in to the corresponding pointer given.
If addressing method is immediate, assign the string result to the corresponsing pointer given.
Returns:
- INVALID_NUMBER_FOR_IMMD_ADDRESSING if the number given for immediate addressing is invalid.
- INVALID_REGISTER_FOR_INDEX_ADDRESSING if the index given in an index adddressing operand is not r10 to r15.
- PASS otherwise */
input_status get_operand_data(char *, char *, addressing_method *, reg *, int *);

/* Gets two pointers to the start and end of a substring (end exclusive), 
and checks for a valid symbol name:
- 31 characters at most, else return TOO_LONG_SYMBOL_NAME
- Starts with a letter, else return ILLEGAL_CHAR_IN_BEGINNING_OF_SYMBOL
- Contains only digits and letters, else return ILLEGAL_CHARS_IN_SYMBOL_NAME
- Is not a name of a command or a register, else return corresponding input statuses
If all checks pass, return PASS. */
input_status validate_symbol_name(char *, char *);

/* Gets a pointer to a string in the form of "symbol[index]"
and four pointer to pointers to characters:
- symbol_start
- symbol_end
- index_start,
- index_end
points symbol_start and symbol_end to the start and end of the symbol char sequence (end exclusive), 
and index_start and index_end to the start and end of the index char sequence (end exclusive).
returns TRUE if everything went fine, FALSE if couldn't find characters from the pattern mentioned above */
boolean split_symbol_index(char *, char **, char **, char **, char **);

/* Gets two pointers to the start and end of a substring (end exclusive)
and returns the register code matching to it "r0" will return 0, etc.
if not found, will return NONE_REG */
reg str_to_reg(char *, char *);

/* Gets an opcode and two addressing methods, 
and validates them against each other via the table presented in the intructions
returns
- INVALID_ADRS_METHOD_FIRST_OP if the first operand has an invalid addressing method
- INVALID_ADRS_METHOD_SECOND_OP if the second operand has an invalid addressing method
- PASS otherwise */
input_status check_addressing_methods(opcode, addressing_method, addressing_method);

/* Gets an addressing method, 
and calculates the length of it - how many words it will occupy:
- addressing mode REGISTER_DIRECT occupies 0 words
- addressing mode IMMEDIATE occupies 1 word
- addressing modes DIRECT and INDEX occupy two words  */
int words_by_addr(addressing_method);

/* Gets a pointer to the first desired whitespace character in a sequence of white chars ending a string, 
and searches for non-whitespace characters after it.
returns:
- EXTRANEOUS_TEXT if found a non-white char or if the pointer given points to a non-whitespace character
- PASS otherwise */
input_status end_of_command(char *);

/* Gets an input status enumerable and returns its corresponding error message */
char *get_error(input_status);

/*Checks if an input status is either PASS or a warning */
boolean passed(input_status);

#endif