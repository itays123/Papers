#include "../../assembler/table.h"
#include "../../assembler/charsequence.h"

void print_row(table row);

int main()
{
    table head = NULL;
    row_data default_row_data;
    char *label1_end, *label2_end, *label3_end, *label4_end;
    char label1[] = "hello";
    char label2[] = "a slightly key before";
    char label3[] = "third row";
    char label4[] = "not found";
    label1_end = next_white(label1);
    label2_end = next('[', label2);
    label3_end = next('[', label3);
    label4_end = next('[', label4);
    default_row_data.symbol.is_code = 0; 
    default_row_data.symbol.is_data = 0; 
    default_row_data.symbol.is_entry = 0; 
    default_row_data.symbol.is_extern = 0; 
    default_row_data.symbol.data = 0; 


    printf("Adding row - ('%s', {0,0,0,0,0}): %d\n", label1, add_item(&head, label1, label1_end, default_row_data));
    printf("Adding row - ('%s', {0,0,0,0,0}): %d\n", label2, add_item(&head, label2, label2_end, default_row_data));
    printf("Adding row - ('%s', {0,0,0,0,0}): %d\n", label3, add_item(&head, label3, label3_end, default_row_data));
    printf("Adding row - ('%s', {0,0,0,0,0}): %d\n", label1, add_item(&head, label1, label1_end, default_row_data));
    
    printf("Last row before '%s': \n", label1);
    print_row(find_last_row_before(&head, label1, label1_end));
    printf("Last row before '%s': \n", label2);
    print_row(find_last_row_before(&head, label2, label2_end));
    printf("Last row before '%s': \n", label3);
    print_row(find_last_row_before(&head, label3, label3_end));
    printf("Last row before '%s': \n", label4);
    print_row(find_last_row_before(&head, label4, label4_end));
    
    printf("Searching row: '%s'\n", label1);
    print_row(find_item(&head, label1, label1_end));
    printf("Searching row: '%s'\n", label2);
    print_row(find_item(&head, label2, label2_end));
    printf("Searching row: '%s'\n", label4);
    print_row(find_item(&head, label4, label4_end));

    free_table(&head);

    return 0;
}

void print_row(table row)
{
    if (row)
        printf("Row (%s)\n", row->key);
    else
        printf("Nullish Row\n");
}