output: main.o Liste.o
	gcc -Wall main.o Liste.o -o tp3;

main.o: main.c
	gcc -Wall -c main.c

Liste.o: PropreListe.c Liste.h
	gcc -Wall -c PropreListe.c -o Liste.o

clean:
	rm *.o tp3
