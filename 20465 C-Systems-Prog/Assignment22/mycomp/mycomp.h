#include "complex.h" /* for complex type def */

/* Returns a pointer to a complex variable. This function's job is not to validate the name. 
If the name is invalid (which shouldn't happen with correct validation), will return NULL */
complex *get_variable(char c);

/* Goes through the global array of pointers to complex variables,
* allocates a memory for each and sets their value to 0+0i */
void init_variables(void);