CCOPTIONS=-g 
CCPPOPTIONS=$(CCOPTIONS)
CC=gcc $(CCOPTIONS)
CCPP=g++ $(CCPPOPTIONS)

all: testsem-thread ping-pong-sem-thread

clean:	
	rm -f *.o testsem-thread ping-pong-sem-thread


# semaphores de comptage en moniteurs pthreads
semaphore-moniteur.o: semaphore-moniteur.c semaphore-moniteur.h
	$(CC) -c semaphore-moniteur.c


# test des semaphores de comptage pour l'exclusion mutuelle
testsem-thread: testsem-thread.c semaphore-moniteur.o semaphore-moniteur.h tprintf.o tprintf.h
	$(CC) -o testsem-thread semaphore-moniteur.o tprintf.o testsem-thread.c -lpthread
	

# test de synchronisation par alternance (ping pong)
ping-pong-sem-thread: ping-pong-sem-thread.c semaphore-moniteur.o semaphore-moniteur.h tprintf.o tprintf.h
	$(CC) -o ping-pong-sem-thread semaphore-moniteur.o tprintf.o ping-pong-sem-thread.c -lpthread


# Autres
tprintf.o: tprintf.c tprintf.h
	$(CC) -c tprintf.c

