#!/bin/bash

echo "$(gcc -g ../../assembler/utils.c ../../assembler/charsequence.c utils.c -Wall -pedantic -std=c90 -o utils)"
echo "$(./utils >utils.out.txt)"
echo "$(cat ./utils.out.txt)"