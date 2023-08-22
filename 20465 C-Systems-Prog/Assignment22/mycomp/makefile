mycomp: complex.o nextchar.o mycomp.o interface.o
	gcc -g complex.o nextchar.o mycomp.o interface.o -ansi -Wall -pedantic -std=c90 -lm -o mycomp

complex.o: complex.c complex.h
	gcc -c complex.c -ansi -Wall -pedantic -std=c90 -lm -o complex.o

mycomp.o: mycomp.c complex.h interface.h mycomp.h
	gcc -c mycomp.c -ansi -Wall -pedantic -std=c90 -o mycomp.o

interface.o: interface.c interface.h nextchar.h
	gcc -c interface.c -ansi -Wall -pedantic -std=c90 -o interface.o

nextchar.o: nextchar.c nextchar.h
	gcc -c nextchar.c -ansi -Wall -pedantic -std=c90 -o nextchar.o