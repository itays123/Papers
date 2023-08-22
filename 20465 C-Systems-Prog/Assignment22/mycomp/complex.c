/* complex.c: working with complex variables */
#include <stdio.h>
#include <stdlib.h> /* for malloc, free */
#include <math.h> /* for pow, sqrt, fabs */
#include "complex.h"

/* Creates and allocates a memory to a new complex variable */
complex *new_comp()
{
    complex *comp = (complex *) malloc(sizeof(complex));
    if (!comp)
    {
        printf("Error: memory allocation failed");
        exit(1);
    }
    comp->real = comp->imaginary = 0.0;
    return comp;
}

/* Assign real and imaginary parts to a complex variable */
void read_comp(complex *var, double real, double imaginary)
{
    var->real = real;
    var->imaginary = imaginary;
}

/* Print a complex variable */
void print_comp(complex *var)
{
    if (var->imaginary >= 0)
        printf("%.2f + (%.2f)i", var->real, var->imaginary);
    else
        printf("%.2f - (%.2f)i", var->real, fabs(var->imaginary));
}

/* Returns the sum of two complex variables, using the following formula:
* (a + bi) + (c + di) = (a+c) + i(b+d) */
complex *add_comp(complex *comp1, complex *comp2)
{
    complex *sum = new_comp();
    sum->real = comp1->real + comp2->real;
    sum->imaginary = comp1->imaginary + comp2->imaginary;
    return sum;
}

/* Returns the difference of two complex variables, using the following formula:
* (a + bi) - (c + di) = (a-c) + i(b-d) */
complex *subtract_comp(complex *comp1, complex *comp2)
{
    complex *dif = new_comp();
    dif->real = comp1->real - comp2->real;
    dif->imaginary = comp1->imaginary - comp2->imaginary;
    return dif;
}

/* Returns the result of multiplying a complex number with a real number, using the following formula:
* (a + bi) * c = ac + bci */
complex *mult_comp_real(complex *comp, double real)
{
    complex *res = new_comp();
    res->real = comp->real * real;
    res->imaginary = comp->imaginary * real;
    return res;
}

/* Returns the result of multiplying a complex number with an imaginary number, using the following formula:
* (a + bi) * ci = aci + bci^2 = -bc + aci */
complex *mult_comp_img(complex *comp, double img)
{
    complex *res = new_comp();
    res->real = -1 * (comp->imaginary) * img;
    res->imaginary = (comp->real) * img;
    return res;
}

/* Returns the result of multiplying two complex numbers, using the following formula:
* (a+bi)(c+di) = ac + adi + bci + bdi^2 = (ac-bd) + (ad + bc)i */
complex *mult_comp_comp(complex *comp1, complex *comp2)
{
    complex *res = new_comp();
    res->real = (comp1->real * comp2->real) - (comp1->imaginary * comp2->imaginary);
    res->imaginary = (comp1->real * comp2->imaginary) + (comp1->imaginary * comp2->real);
    return res;
}

/* Returns the absolute value of a complex number, using the following formula:
* |a+bi| = sqrt(a^2 + b^2) */
double abs_comp(complex *comp)
{
    return sqrt(pow(comp->real, 2) + pow(comp->imaginary, 2));
}