/* Table.c - deal with dynamic tables */
#include "table.h"
#include "utils.h"
#include <string.h> /* For strcmp, strlen, strcpy */
#include <stdlib.h> /* For free */

table find_last_row_before(table *tab, char *keystart, char *keyend)
{
    char temp;
    table curr, prev = NULL;
    
    /* Edge case: empty table or key comes before the key of the head. 
    Return NULL */
    if ((*tab) == NULL || str_before(keystart, keyend, (*tab)->key))
        return NULL;


    /* Point curr to the first node with a key coming after the new key */
    curr = *tab;
    temp = *keyend;
    *keyend = '\0';
    while (KEYOF_NODE_BEFORE(curr, keystart))
    {
        prev = curr;
        curr = curr->next;
    }
    *keyend = temp;

    /* Prev is pointing to the desired node */
    return prev;
}

boolean add_item(table *tab, char *keystart, char *keyend, row_data data)
{
    table prev, next_node;
    
    /* Edge case: empty table or key comes before the key of the head. 
    Add the new row on the spot */
    if ((*tab) == NULL || str_before(keystart, keyend, (*tab)->key))
    {
        *tab = new_row(keystart, keyend, data, tab ? *tab : NULL);
        return TRUE;
    }

    /* Find position to add */
    prev = find_last_row_before(tab, keystart, keyend);
    /* Prev is only null if the key is equal to the key of the head of the table */
    if (!prev)
        return FALSE;
    
    next_node = prev->next;

    /* Check the key of the next node. If equals to this key, do not add and return FALSE */
    if (KEYOF_NODE_EQUALS(next_node, keystart, keyend))
        return FALSE;

    /* Add node */
    prev->next = new_row(keystart, keyend, data, next_node);
    return TRUE;
}

table find_item(table *tab, char *keystart, char *keyend)
{
    table prev, target;
    prev = find_last_row_before(tab, keystart, keyend);

    /* Check: Table empty, or key comes before head (or head itself) */
    if (prev == NULL) 
    {
        if (KEYOF_NODE_EQUALS(*tab, keystart, keyend))
            return *tab;
        return NULL;
    }

    /* Check: if prev is the last element, element not found */
    if (prev->next == NULL)
        return NULL;
    
    target = prev->next;

    /* Compare the key of target and the key given */
    if (str_equal(keystart, keyend, target->key))
        return target;
    
    return NULL;
}

void free_table(table *tab)
{
    table head, temp;
    if (tab == NULL)
        return;

    head = *tab;
    while (head != NULL)
    {
        temp = head;
        head = head->next;
        free(temp->key);
        free(temp);
    }
}

table new_row(char *keystart, char *keyend, row_data data, table next)
{
    char *key_copy;
    table result;

    key_copy = strcpy_safe(keystart, keyend);
    result = (table) malloc_safe(sizeof(table_row));

    result->key = key_copy;
    result->data = data;
    result->next = next;
    return result;
}