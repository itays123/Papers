#ifndef _SPASS
#define _SPASS
#include "globals.h"
#include "word.h"
#include "table.h"
#include <stdio.h>

/* Run a the second pass of a file. Generate the externals table and complete the code image 
Return TRUE if everything went fine, FALSE otherwise*/
boolean second_pass(FILE *, char *, word **, table *, table *);

#endif