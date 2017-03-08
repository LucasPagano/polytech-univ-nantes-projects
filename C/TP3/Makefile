output: main.o AncienListe.o SBlock.o
	gcc -g -std=c99 -Wall main.o SBlock.o AncienListe.o -o tp3;

main.o: main.c
	gcc -g -std=c99 -Wall -c main.c

SBlock.o: SBlock.c SBlock.h
	gcc -g -std=c99 -Wall -c SBlock.c -o SBlock.o

AncienListe.o: AncienListe.c AncienListe.h
	gcc -g -std=c99 -Wall -c AncienListe.c -o AncienListe.o

clean:
	rm *.o tp3
