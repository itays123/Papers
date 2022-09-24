#include "../../assembler/symbols.h"
#include "../../assembler/charsequence.h"

static void print_symbol_row(table);

int main()
{
    input_status status;
    table symbols_table = NULL, current;
    char *label1_end, *label2_end, *label3_end, *label4_end, *label5_end;
    char label1[] = "Hello";
    char label2[] = "Another";
    char label3[] = "Extern";
    char label4[] = "notfound";
    char label5[] = "AAAA";
    label1_end = next_white(label1);
    label2_end = next_white(label2);
    label3_end = next_white(label3);
    label4_end = next_white(label4);
    label5_end = next_white(label5);

    status = add_symbol(&symbols_table, label1, label1_end, CODE, 102);
    printf("\nAdded symbol, status = %d", status);
    status = add_symbol(&symbols_table, label2, label2_end, DATA, 37);
    printf("\nAdded symbol, status = %d", status);
    status = add_symbol(&symbols_table, label2, label2_end, DATA, 38);
    printf("\nAttempted adding duplicate symbol, status = %d", status);
    status = add_extern_symbol(&symbols_table, label3, label3_end);
    printf("\nAdded extern symbol, status = %d", status);
    status = add_extern_symbol(&symbols_table, label3, label3_end);
    printf("\nAdded extern symbol again, status = %d", status);
    status = add_symbol(&symbols_table, label3, label3_end, DATA, 38);
    printf("\nAttempted adding duplicate extern symbol, status = %d", status);

    printf("\n\nSearching addresses...");
    printf("\nSearching the address of '%s': %d", label1, find_symbol_addr(&symbols_table, label1, label1_end));
    printf("\nSearching the address of '%s': %d", label2, find_symbol_addr(&symbols_table, label2, label2_end));
    printf("\nSearching the address of '%s': %d", label3, find_symbol_addr(&symbols_table, label3, label3_end));
    printf("\nSearching the address of '%s': %d", label4, find_symbol_addr(&symbols_table, label4, label4_end));
    printf("\nSearching the address of '%s': %d", label5, find_symbol_addr(&symbols_table, label5, label5_end));

    relocate_data_symbols(&symbols_table, 102);
    printf("\n\nSearching addresses after relocation:");
    printf("\nSearching the address of '%s': %d", label1, find_symbol_addr(&symbols_table, label1, label1_end));
    printf("\nSearching the address of '%s': %d", label2, find_symbol_addr(&symbols_table, label2, label2_end));
    printf("\nSearching the address of '%s': %d", label3, find_symbol_addr(&symbols_table, label3, label3_end));

    printf("\n\nMarking entry symbols:");
    status = mark_entry_symbol(&symbols_table, label1, label1_end);
    printf("\nMarked %s as entry. Result = %d", label1, status);
    status = mark_entry_symbol(&symbols_table, label2, label2_end);
    printf("\nMarked %s as entry. Result = %d", label2, status);
    status = mark_entry_symbol(&symbols_table, label3, label3_end);
    printf("\nMarked %s as entry. Result = %d", label3, status);
    status = mark_entry_symbol(&symbols_table, label4, label4_end);
    printf("\nMarked %s as entry. Result = %d", label4, status);
    status = mark_entry_symbol(&symbols_table, label5, label5_end);
    printf("\nMarked %s as entry. Result = %d", label5, status);

    printf("\n\nSymbols table:");
    current = symbols_table;
    while (current != NULL)
    {
        print_symbol_row(current);
        current = current->next;
    }

    free_table(&symbols_table);
    return 0;
}

static void print_symbol_row(table row)
{
    printf("\nSymbol: {");
    printf("\n\tkey: %s,", row->key);
    printf("\n\tis_code: %d,", (row->data).symbol.is_code);
    printf("\n\tis_data: %d,", (row->data).symbol.is_data);
    printf("\n\tis_extern: %d,", (row->data).symbol.is_extern);
    printf("\n\tis_entry: %d,", (row->data).symbol.is_entry);
    printf("\n\tdata: %d,", (row->data).symbol.data);
    printf("\n}");
}
