output: main.o Liste.o
	gcc -g -std=c99 -Wall main.o Liste.o -o tp3;

main.o: main.c
	gcc -g -std=c99 -Wall -c main.c

Liste.o: SBlock.c Liste.h
	gcc -g -std=c99 -Wall -c SBlock.c -o Liste.o

clean:
	rm *.o tp3
