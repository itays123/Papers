#!/bin/bash

echo "$(gcc -g ../../assembler/firstpass.c ../../assembler/word.c ../../assembler/output.c ../../assembler/symbols.c ../../assembler/table.c ../../assembler/utils.c ../../assembler/charsequence.c ../../assembler/command.c fpass.c -Wall -pedantic -std=c90 -o fpass)"
echo "$(./fpass <fpass_pass.input.txt >fpass_pass.out.txt)"
echo "$(cat ./fpass_pass.out.txt)"
echo "$(./fpass <fpass_fail.input.txt >fpass_fail.out.txt)"
echo "$(cat ./fpass_fail.out.txt)"