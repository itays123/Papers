#ifndef _OUTPUT
#define _OUTPUT
#include "globals.h"
#include "word.h"
#include "table.h"
#define ENTRY_POSTFIX ".ent"
#define EXTERN_POSTFIX ".ext"
#define OBJECT_POSTFIX ".ob"

/* Write the following files:
- the object file (filename.ob), using code_image, icf, data_image, dcf
- the externals file (filename.ext), using the externals table, if there are entries in it
- the entry file (filename.ent), using the symbols table, if there are any symbols marked as entry 
Return TRUE if everything was successful, FALSE otherwise */
boolean write_files(char *, word **, int, int *, int, table, table);

#endif