#!/bin/bash

echo "$(gcc -g ../../assembler/output.c ../../assembler/symbols.c ../../assembler/table.c ../../assembler/utils.c ../../assembler/word.c ../../assembler/charsequence.c ../../assembler/command.c output.c -Wall -pedantic -std=c90 -o output)"
echo "$(./output)"