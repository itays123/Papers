#include "../../assembler/symbols.h"
#include "../../assembler/charsequence.h"

static void print_external(table);

int main()
{
    table externals_table = NULL, current;
    char *label1_end, *label2_end, *label3_end;
    char label1[] = "Hello";
    char label2[] = "Another";
    char label3[] = "Extern";
    label1_end = next_white(label1);
    label2_end = next_white(label2);
    label3_end = next_white(label3);

    add_extern_reference_word(&externals_table, label1, label1_end, 165);
    add_extern_reference_word(&externals_table, label1, label1_end, 134);
    add_extern_reference_word(&externals_table, label2, label2_end, 123);
    add_extern_reference_word(&externals_table, label2, label2_end, 234);
    add_extern_reference_word(&externals_table, label1, label1_end, 22);
    add_extern_reference_word(&externals_table, label1, label1_end, 167);
    add_extern_reference_word(&externals_table, label2, label2_end, 168);
    add_extern_reference_word(&externals_table, label1, label1_end, 169);
    add_extern_reference_word(&externals_table, label3, label3_end, 170);
    add_extern_reference_word(&externals_table, label3, label3_end, 171);
    add_extern_reference_word(&externals_table, label3, label3_end, 172);

    printf("\n\nExternals table:");
    current = externals_table;
    while (current != NULL)
    {
        print_external(current);
        current = current->next;
    }

    free_table(&externals_table);
    return 0;
}

static void print_external(table row)
{
    int base_location = (row->data).symbol.data;
    printf("\nExternal reference (%s, base=%d, offset=%d)", row->key, base_location, base_location + 1);
}