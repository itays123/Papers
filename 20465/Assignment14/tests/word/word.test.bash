#!/bin/bash

echo "$(gcc -g ../../assembler/word.c ../../assembler/utils.c word.c -Wall -pedantic -std=c90 -o word)"
echo "$(./word >word.out.txt)"
echo "$(cat ./word.out.txt)"