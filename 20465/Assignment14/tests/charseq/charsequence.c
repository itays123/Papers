#include <stdio.h>
#include "../../assembler/charsequence.h"

int main()
{
    char line[] = "label: operation 123, op2";
    char *colon, *non_alnum, *opstart, *opend, *digit, *num_end, *comma;
    int num;
    char line2[] = ".string \"hello\" ";
    char *instruction_end, *quote_open, *quote_close;
    
    puts(line);
    colon = next_white_or_colon(line); /* : */
    printf("\nnext white or colon: `%s`, extected :", colon);
    non_alnum = next_non_alnum(line); /* : */
    printf("\ncheck: next non alnum: `%s`, expected :", non_alnum);

    opstart = next_nonwhite(colon + 1); /* o */
    printf("\nnext non white: `%s`, expected o", opstart);
    opend = next_white(opstart); /* SPACE */
    printf("\nnext white or comma: `%s`, expected SPACE", opend);
    printf("\nop name == `operation`? %d", str_equal(opstart, opend, "operation"));

    digit = next_nonwhite(opend); /* 1 */
    printf("\nnext non white: `%s`, expected 1", digit);
    num_end = next_non_digital(digit); /* , */
    printf("\nnext non digital: `%s`, expected ,", num_end);
    if (str_to_int(digit, num_end, &num))
        printf("\ndigits converted to integer = %d", num);
    else
        printf("\ncould not convert number");
    comma = next_white_or_comma(digit); /* , */
    printf("\ncheck: next white or comma: `%s`, expected ,", comma);

    printf("\n\n");

    puts(line2);

    instruction_end = next_white(line2); /* SPACE */
    printf("\nnext white: `%s`, expected SPACE", instruction_end);
    quote_open = next('"', instruction_end);
    printf("\nnext quote: `%s`, expected quote", quote_open);
    quote_close = next('"', quote_open+1);
    printf("\nnext quote: `%s`, expected quote", quote_close);

    return 0;
}