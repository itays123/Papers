#include "../../assembler/macro.h"
#include "../../assembler/table.h"
#include "../../assembler/utils.h"

int main()
{
    FILE *source = fopen_safe("source", ASSEMBLY_POSTFIX, "r"),
        *out = fopen_safe("out", ASSEMBLY_POST_MACRO_POSTFIX, "w");
    int result = macro_pass(source, out);
    return !result;
}
