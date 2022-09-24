/* Symbols.c - dealing with the symbols table and the external words table */
#include "symbols.h"
#include <string.h> /* For strcmp */

static void make_symbol_attr(row_data *, symbol_purpose, int);

input_status add_symbol(table *tab, char *start, char *end, symbol_purpose purpose, int addr)
{
    input_status status;
    boolean key_exists;
    row_data symb_data;
    if ((status = validate_symbol_name(start, end)) != PASS)
        return status;
    
    make_symbol_attr(&symb_data, purpose, addr);

    key_exists = !add_item(tab, start, end, symb_data);

    if (key_exists)
        return SYMBOL_ALREADY_DEFINED;
    return PASS;
}

input_status add_extern_symbol(table *tab, char *start, char *end)
{
    input_status status;
    table prev_row, next_row;
    row_data symb_data;
    if ((status = validate_symbol_name(start, end)) != PASS)
        return status;
    
    make_symbol_attr(&symb_data, EXTERN, ADDR_EXTERN);

    prev_row = find_last_row_before(tab, start, end);
    if (!prev_row) 
        /* table is null, or symbol comes first, therefore doesn't exist already */
        add_item(tab, start, end, symb_data);
    else /* There is a symbol beofre. Chect next symbol */
    {
        next_row = prev_row->next;
        if (KEYOF_NODE_EQUALS(next_row, start, end))
        {
            /* Symbol is defined, check extern or not */
            if (!IS_EXTERN(next_row))
                return SYMBOL_ALREADY_DEFINED;
            /* Symbol already defined as extern. Do nothing. */
        }
        else {
            /* Symbol not defined already */
            prev_row->next = new_row(start, end, symb_data, next_row);   
        }
    }

    return PASS;
}

int find_symbol_addr(table *tab, char *start, char *end)
{
    table target_row;
    
    target_row = find_item(tab, start, end);

    if (!target_row)
        return ADDR_NOT_FOUND;
    return ROW_DATA(target_row);
}

input_status mark_entry_symbol(table *tab, char *start, char *end)
{
    table target_row = find_item(tab, start, end);

    if (!target_row)
        return UNREC_SYMBOL_FOR_ENTRY;
    
    if (IS_EXTERN(target_row))
        return EXTERNAL_ENTRY_SYMBOL;
    
    IS_ENTRY(target_row) = TRUE;
    return PASS;
}

void relocate_data_symbols(table *tab, int ic)
{
    table current_row = *tab;
    int prev_value;
    while (current_row != NULL)
    {
        if (IS_DATA(current_row))
        {
            prev_value = ROW_DATA(current_row);
            ROW_DATA(current_row) = prev_value + ic;
        }
        current_row = current_row->next;
    }
}

void add_extern_reference_word(table *tab, char *start, char *end, int base_addr)
{
    table prev_row;
    row_data word_data;

    make_symbol_attr(&word_data, EXTERN, base_addr);
    
    prev_row = find_last_row_before(tab, start, end);

    if (!prev_row) /* Symbol comes first or table is empty */
        add_item(tab, start, end, word_data);
    else /* Add word */
        prev_row->next = new_row(start, end, word_data, prev_row->next);
}

static void make_symbol_attr(row_data *row_out, symbol_purpose purpose, int addr)
{
    (*row_out).symbol.data = addr;
    (*row_out).symbol.is_extern = purpose == EXTERN;
    (*row_out).symbol.is_code = purpose == CODE;
    (*row_out).symbol.is_data = purpose == DATA;
    (*row_out).symbol.is_entry = FALSE;
}