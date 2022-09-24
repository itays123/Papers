/*
* Program will recieve a string from the user and contract it
* Assertations - 
* - The user will enter a line no bigger than 80 characters.
*/
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#define MAX_STR_LENGTH 81 /* 80 characters + '\0' */
#define MIN_SEQUENCE_LENGTH 3
#define DASH '-'
#define TRUE 1
#define FALSE 0

/* a function that contracts the string in the first parameter and places the contracted string in the second,
* Asserting the second parameter has at least the amount of cells in the first one */
void contract(char [], char []);

/* a function that adds a given sequence to a string */
void addSequenceToString(char [] , int , char , char );

/* Checks if two given chars are next to each other in the Ascii table, 
* and are both the same type (number, big letter, small letter etc.) */
int isInSameSequence(char , char );

/* Replaces the '\n' character of a string with a '\0' character */
void removeEnterCharAtEndOfLine(char []);

int main() 
{
    char inputStr[MAX_STR_LENGTH];
    char contractedStr[MAX_STR_LENGTH];

    printf("\nPlease enter a string to contract: \n");
    fgets(inputStr, MAX_STR_LENGTH, stdin);
    removeEnterCharAtEndOfLine(inputStr);
    contract(inputStr, contractedStr);

    printf("\nContracted the string: \"%s\"", inputStr);
    printf("\nTo: \"%s\" \n", contractedStr);

    return 0;
}

/* contracts the string in the first parameter and places it in the second one.
* Does it by running through every character in the first string and comparing its code to the pervious one */
void contract(char s1[], char s2[]) 
{
    int sequenceStartIdx = 0; /* saves the position of the start index */
    int i;
    char prev, curr;
    curr = s1[0]; /* First character will definately be the first in the contracted string */

    s2[0] = '\0'; /* Add this in order to make the strcat() function work */

    for (i=1; s1[i]; i++) {
        prev = curr;
        curr = s1[i];
        if (!isInSameSequence(prev,curr)) {
            /* current char is NOT in the same sequence as the char before. Meaning, a sequence has ended */
            addSequenceToString(s2, i-sequenceStartIdx, s1[sequenceStartIdx], prev);
            sequenceStartIdx = i;
        }
    }

    /* Add last sequence */
    addSequenceToString(s2, i-sequenceStartIdx, s1[sequenceStartIdx], curr);

}

/* a function that adds a given sequence to a string and returns the new string length.
* Diffrenciuates between various cases: 1 char, 2 char and 3+ char long sequences. */
void addSequenceToString(char str[], int sequenceLength, char start, char end) 
{
    int i = strlen(str); /* i = index of first available cell of str */
    str[i++] = start;

    if (sequenceLength >= MIN_SEQUENCE_LENGTH) /* sequence is long enough to add a dash */
        str[i++] = DASH;
    if (sequenceLength > 1) /* a sequence of 2 (i.e: ab) should be ended, and a longer sequence (i.e: a-c) as well */
        str[i++] = end;

    str[i++] = '\0';

}

/* Checks if two given chars are next to each other in the Ascii table, 
* and are both alphanumeric (checked using ctype.h's isalnum function)
* Returns a boolean value (0 or 1) */
int isInSameSequence(char prev, char curr) 
{
    return isalnum(prev) && isalnum(curr) && curr == prev + 1;
}

/* Replaces the '\n' character of a string with a '\0' character */
void removeEnterCharAtEndOfLine(char str[]) 
{
    int i = 0;
    while (str[i] && str[i] != '\n')
        i++;
    str[i] = '\0';
}