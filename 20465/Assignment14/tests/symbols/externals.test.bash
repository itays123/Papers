#!/bin/bash

echo "$(gcc -g ../../assembler/symbols.c ../../assembler/table.c ../../assembler/utils.c ../../assembler/charsequence.c ../../assembler/command.c externals.c -Wall -pedantic -std=c90 -o externals)"
echo "$(./externals >externals.out.txt)"
echo "$(cat ./externals.out.txt)"