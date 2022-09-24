#ifndef _TABLE
#define _TABLE
#include "globals.h"
#include "charsequence.h"
#include <stdio.h>

#define KEYOF_NODE_EQUALS(node, keystart, keyend) ((node) != NULL && str_equal((keystart), (keyend), (node)->key))
#define KEYOF_NODE_BEFORE(node, keyname) ((node) != NULL && strcmp((node)->key, keyname) < 0)
#define KEYOF_NODE_AFTER(node, keyname) ((node) != NULL && strcmp((node)->key, keyname) > 0)

/***** Table type definition ********/

typedef struct symbol_attributes {
    unsigned int is_code: 1;
    unsigned int is_data: 1;
    unsigned int is_extern: 1;
    unsigned int is_entry: 1;
    unsigned int data: 16;
} symbol_attr;

typedef struct macro_attributes {
    unsigned int lines;
    fpos_t position;
} macro_attr;

typedef union row_data_union {
    symbol_attr symbol;
    macro_attr macro;
} row_data;

typedef struct row *table;
typedef struct row {
    char *key;
    table next;
    row_data data;
} table_row;

/******** Function definitions ********/

/* Return pointer to a row with the last key that comes before the given key alphabetically,
or NULL if table is NULL or if given key is alphabetically before the key of the head.
The given key includes two pointers to the start and end of it, end exclusive */
table find_last_row_before(table *, char *, char *);

/* Adds a new item to the table, or create a new table with the item if needed.
Add it in the corresponding spot alphabetically (sort by key) to save time when searching for the element.
The given key includes two pointers to the start and end of it, end exclusive
Return TRUE if a row with the key specified didn't exist already, FALSE otherwise */
boolean add_item(table *, char *, char *, row_data);

/* Return a pointer to a row with a given key in a given table,
NULL if not found.
The given key includes two pointers to the start and end of it, end exclusive */
table find_item(table *, char *, char *);

/* Free every row in the table. */
void free_table(table *);

/* Create a new row in the table. Give it (a copy of) the key given (given pointers to the start and end of it, end exclusive),
the row data given and a pointer to the next row given 
Return pointer to new row */
table new_row(char *, char *, row_data , table );

#endif