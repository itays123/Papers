
typedef struct complex {
    double real, imaginary;
} complex;

/* Creates and allocates a memory to a new complex variable */
complex* new_comp();

/* Assign real and imaginary parts to a complex variable */
void read_comp(complex*, double, double);

/* Print a complex variable */
void print_comp(complex* );

/* Returns the sum of two complex variables, using the following formula:
* (a + bi) + (c + di) = (a+c) + i(b+d) */
complex *add_comp(complex*, complex*);

/* Returns the difference of two complex variables, using the following formula:
* (a + bi) - (c + di) = (a-c) + i(b-d) */
complex *subtract_comp(complex*, complex*);

/* Returns the result of multiplying a complex number with a real number, using the following formula:
* (a + bi) * c = ac + bci */
complex *mult_comp_real(complex*, double);

/* Returns the result of multiplying a complex number with an imaginary number, using the following formula:
* (a + bi) * ci = aci + bci^2 = -bc + aci */
complex *mult_comp_img(complex*, double);

/* Returns the result of multiplying two complex numbers, using the following formula:
* (a+bi)(c+di) = ac + adi + bci + bdi^2 = (ac-bd) + (ad + bc)i */
complex *mult_comp_comp(complex*, complex*);

/* Returns the absolute value of a complex number, using the following formula:
* |a+bi| = sqrt(a^2 + b^2) */
double abs_comp(complex*);