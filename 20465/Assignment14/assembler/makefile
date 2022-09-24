FLAGS = -ansi -Wall -pedantic -std=c90
EXE_DEPS = obj/assembler.o obj/charsequence.o obj/command.o obj/firstpass.o \
	obj/macro.o obj/output.o obj/secpass.o obj/symbols.o obj/table.o obj/utils.o obj/word.o

# Executable
assembler: obj_folder $(EXE_DEPS)
	gcc -g $(EXE_DEPS) $(FLAGS) -o $@

# Objects folder
obj_folder:
	mkdir -p obj

# Main
obj/assembler.o: assembler.c globals.h macro.h firstpass.h secpass.h symbols.h table.h word.h utils.h output.h
	gcc -c assembler.c $(FLAGS) -o $@

# String Helper Functions
obj/charsequence.o: charsequence.c charsequence.h globals.h
	gcc -c charsequence.c $(FLAGS) -o $@

# Dealing With Assembly Lines
obj/command.o: command.c command.h charsequence.h globals.h
	gcc -c command.c $(FLAGS) -o $@

# First pass
obj/firstpass.o: firstpass.c firstpass.h word.h table.h command.h charsequence.h symbols.h globals.h
	gcc -c firstpass.c $(FLAGS) -o $@

# Macro pass
obj/macro.o: macro.c macro.h table.h charsequence.c utils.h globals.h
	gcc -c macro.c $(FLAGS) -o $@

# Output files
obj/output.o: output.c output.h word.h table.h utils.h symbols.h globals.h
	gcc -c output.c $(FLAGS) -o $@

# Second pass
obj/secpass.o: secpass.c secpass.h word.h table.h command.h charsequence.h symbols.h utils.h globals.h
	gcc -c secpass.c $(FLAGS) -o $@

# Symbols table
obj/symbols.o: symbols.c symbols.h command.h table.h globals.h
	gcc -c symbols.c $(FLAGS) -o $@

# Table module
obj/table.o: table.c table.h charsequence.h utils.h globals.h
	gcc -c table.c $(FLAGS) -o $@

# Utility functions
obj/utils.o: utils.c utils.h globals.h
	gcc -c utils.c $(FLAGS) -o $@

# Word-related functions
obj/word.o: word.c word.h utils.h globals.h
	gcc -c word.c $(FLAGS) -o $@