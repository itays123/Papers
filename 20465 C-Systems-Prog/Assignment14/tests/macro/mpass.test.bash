#!/bin/bash

echo "$(gcc -g ../../assembler/macro.c ../../assembler/table.c ../../assembler/utils.c ../../assembler/charsequence.c mpass.c -Wall -pedantic -std=c90 -o mpass)"
echo "$(./mpass)"
echo "$(cat ./out.am)"