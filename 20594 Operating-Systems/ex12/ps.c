/**
 * Main ps file made by Itay Schechner 328197462
 */
#include "types.h"
#include "stat.h"
#include "user.h"

int main(int argc, char *argv[])
{

    if (cps162() != 0)
    {
        printf(2, "ps: Failed to execute command");
    }

    exit(0);
}