#ifndef _MACRO
#define _MACRO
#include "globals.h"
#include <stdio.h>

/* Goes through the source file. 
If encountered a macro definition, save its position and use it when encountered macro references */
boolean macro_pass(FILE *, FILE *);

#endif