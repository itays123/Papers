#ifndef _FPASS
#define _FPASS
#include "globals.h"
#include "word.h"
#include "table.h"
#include <stdio.h>

/* Main first pass functionality.
Process every single line of the file, create words as possible (any words except symbol references)
Create the data image, create the symbols table.
Move ic and dc accordingly.
Return TRUE if did not find any first-pass errors, FALSE otherwise.
If false, program should NOT continue for second pass. */
boolean first_pass(FILE *, char *, word **, int *, int *, int *, table *);

#endif