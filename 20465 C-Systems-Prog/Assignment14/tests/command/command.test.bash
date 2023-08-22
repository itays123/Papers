#!/bin/bash

echo "$(gcc -g ../../assembler/command.c ../../assembler/charsequence.c command.c -Wall -pedantic -std=c90 -o command)"
echo "$(./command <command.input.txt >command.out.txt)"