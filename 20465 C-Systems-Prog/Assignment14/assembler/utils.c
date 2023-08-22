/* Utils.c - small, useful utility functions */
#include "utils.h"
#include "stdlib.h" /* For malloc(), free(), .... */
#include "string.h" /* For strlen(), strcpy() */

#define SIZEOF_STR(length) (sizeof(char) * ((length) + 1)) /* length + '\0' */

void *malloc_safe(int bytes)
{
    void *result = malloc(bytes);
    if (result == NULL)
    {
        printf("Fatal: memory allocation failed");
        exit(1);
    }
    /* result is safe */
    return result;
}

char *strcat_safe(char *str1, char *str2)
{
    int length1, length2;
    char *result;
    /* malloc length1 + length2 + 1 (for \0) characters */
    length1 = strlen(str1);
    length2 = strlen(str2);
    result = malloc_safe(SIZEOF_STR(length1 + length2));
    memcpy(result, str1, length1);
    memcpy(result+length1, str2, length2);
    result[length1 + length2] = '\0';
    return result;
}

char *strcpy_safe(char *start, char *end)
{
    char *result;
    int length = end - start;
    result = malloc_safe(SIZEOF_STR(length));
    memcpy(result, start, length);
    result[length] = '\0';
    return result;
}

boolean fopen_check(FILE **out, char *filename, char *postfix, char *permissions)
{
    char *full_filename = strcat_safe(filename, postfix);
    *out = fopen(full_filename, permissions);
    free(full_filename);
    return *out != NULL;
}

FILE *fopen_safe(char *filename, char *postfix, char *permissions)
{
    FILE *target;
    boolean open_success = fopen_check(&target, filename, postfix, permissions);
    if (!open_success)
    {
        printf("Fatal: Opening file %s%s failed", filename, postfix);
        exit(1);
    }
    return target;
}

void get_integer_hexbits(hexbits bits, int data)
{
    int i, currentMask, bitsToMove;
    /* Skip most significat hexbit - will be kept for the ARE status */
    for (i = 1; i < HEXBITS_IN_WORD; i++)
    {
        /* Calculate mask:
        1 => 0xF000, 2 => 0x0F00, 3 => 0x00F0, 4 => 0x000F */
        bitsToMove = (BITS_IN_HEXBIT * (HEXBITS_IN_WORD - 1 - i));
        currentMask = HEXBIT_MASK << bitsToMove;
        bits[i] = (data & currentMask) >> bitsToMove;
    }
}
