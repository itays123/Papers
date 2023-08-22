#include <stdio.h>
#include "../../assembler/utils.h"
#include "../../assembler/charsequence.h"

int main()
{
    char *filename = "myassembly", *filename_end, *filename_cpy;
    FILE *myassembly_file;
    hexbits bits;
    int base, offset;
    printf("\nConcating two strings. Result: %s, expected myassembly.as", strcat_safe(filename, ".as"));
    myassembly_file = fopen_safe(filename, ".as", "w");
    fprintf(myassembly_file, "c is awesome!");
    printf("\nChecking STORABLE(-6). Result: %X, expected 0xFFFA", STORABLE(-6));
    bits[0] = Absolute;
    get_integer_hexbits(bits, STORABLE(-6));
    printf("\nIn hexbits - %X, %X, %X, %X, %X", bits[0], bits[1], bits[2], bits[3], bits[4]);
    GET_BASE_OFFSET(33, base, offset);
    printf("\nTesting GET_BASE_OFFSET(33). Result: (%d, %d), expected (32, 1)", base, offset);

    filename_end = next_white(filename);
    filename_cpy = strcpy_safe(filename, filename_end);
    printf("\nCopied filename, result: %s", filename_cpy);
    return 0;
}