#!/bin/bash

echo "$(gcc -g ../../assembler/secpass.c ../../assembler/firstpass.c ../../assembler/word.c ../../assembler/output.c ../../assembler/symbols.c ../../assembler/table.c ../../assembler/utils.c ../../assembler/charsequence.c ../../assembler/command.c spass.c -Wall -pedantic -std=c90 -o spass)"
echo "$(./spass <spass_pass.input.txt >spass_pass.out.txt)"
echo "$(cat ./spass_pass.out.txt)"
echo "$(./spass <spass_fail.input.txt >spass_fail.out.txt)"
echo "$(cat ./spass_fail.out.txt)"