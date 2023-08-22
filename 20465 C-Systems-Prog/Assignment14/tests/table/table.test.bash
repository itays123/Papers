#!/bin/bash

echo "$(gcc -g ../../assembler/table.c ../../assembler/utils.c ../../assembler/charsequence.c table.c -Wall -pedantic -std=c90 -o table)"
echo "$(./table >table.out.txt)"
echo "$(cat ./table.out.txt)"