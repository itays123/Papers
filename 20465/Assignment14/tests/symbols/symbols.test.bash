#!/bin/bash

echo "$(gcc -g ../../assembler/symbols.c ../../assembler/table.c ../../assembler/utils.c ../../assembler/charsequence.c ../../assembler/command.c symbols.c -Wall -pedantic -std=c90 -o symbols)"
echo "$(./symbols >symbols.out.txt)"
echo "$(cat ./symbols.out.txt)"