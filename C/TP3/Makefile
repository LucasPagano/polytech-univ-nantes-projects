output: main.o Liste.o
	gcc main.o Liste.o -o tp3; \

main.o: main.c
	gcc -c main.c

Liste.o: AncienListe.c Liste.h
	gcc -c AncienListe.c -o Liste.o

clean:
	rm *.o tp3
