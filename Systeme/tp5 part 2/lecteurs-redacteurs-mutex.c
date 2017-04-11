/*
 *	Probl�me des Lecteurs r�dacteurs
 *	biblioth�que pthread
 *	Solution avec semaphores mutex uniquement
 *	
 */

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "macros-thread.h"
#include "tprintf.h"
#include "semaphore-moniteur.h"


/* Notions Globales */

#define NBTHREADS 6

int nbLecteurs = 0;

/* -- A COMPLETER -- */
/* declarer les semaphores mutex */
type_semaphore *writer;
type_semaphore *mutex;
type_semaphore *r;

/* Fonction principales des threads "redacteur" */
void * redacteur(void * arg) {
	char * nom = *(char **)arg;

	tprintf("debut thread redacteur %s\n", nom);

	/* -- A COMPLETER -- */
	/* gerer la synchronisation lecteurs/redacteurs */

	P_semaphore(r);
		P_semaphore(writer);
			tprintf("%s ecrit...\n", nom);
			sleep(5+rand()%6);
			tprintf("%s : nbLecteurs=%i\n", nom, nbLecteurs);
			tprintf("%s a fini d ecrire...\n", nom);

		/* -- A COMPLETER -- */
		/* gerer la synchronisation lecteurs/redacteurs */
		V_semaphore(writer);
	V_semaphore(r);

	tprintf("fin thread redacteur %s\n", nom);
	pthread_exit(0);
}

/* Fonction principales des threads "lecteur" */
void * lecteur(void * arg) {
	char * nom = *(char **)arg;

	tprintf("debut thread lecteur %s\n", nom);

	/* -- A COMPLETER -- */
	/* gerer la synchronisation lecteurs/redacteurs */
	P_semaphore(r);
		P_semaphore(mutex);
			nbLecteurs++;
			printf("Nb lecteurs : %i\n", nbLecteurs);
			if (nbLecteurs == 1){
				P_semaphore(writer);
			}
		V_semaphore(mutex);
	V_semaphore(r);


	tprintf("%s lit...\n", nom);
		sleep(1 + (unsigned int)rand() % 3);
		tprintf("%s a fini de lire...\n", nom);

	/* -- A COMPLETER -- */
	/* gerer la synchronisation lecteurs/redacteurs */
	P_semaphore(mutex);
		nbLecteurs--;
		printf("Nb lecteurs : %i\n", nbLecteurs);
		if (nbLecteurs == 0){
			V_semaphore(writer);
		}
	V_semaphore(mutex);


	tprintf("fin thread lecteur %s\n", nom);
	pthread_exit(0);
}

/* Fonction principales de demarrage et de creation des threads  */
int main ()
{
	pthread_t threads[NBTHREADS];
	char * nomsThreads[NBTHREADS]={"r1", "w1","r2", "r3", "w2", "r4"};
	void * (* thread_main) (void *);
	int i, errcode;

	/* -- A COMPLETER -- */
	/* initialiser les semaphores mutex */
	writer = malloc(sizeof(type_semaphore));
	mutex = malloc(sizeof(type_semaphore));
	r = malloc(sizeof(type_semaphore));
	init_semaphore(writer, 1);
	init_semaphore(mutex, 1);
	init_semaphore(r, 1);


	/* Creation des threads lecteurs et redacteurs */
	for ( i=0; i<NBTHREADS; i++ ){
		if ( nomsThreads[i][0]=='w' ) {
			thread_main = redacteur;
		} else if ( nomsThreads[i][0]=='r' ) {
			thread_main = lecteur;
		}
		errcode=pthread_create (&threads[i], NULL, thread_main, &nomsThreads[i]);
		if ( errcode != 0 ) {
			fprintf(stderr, "Erreur de creation du thread %s\n", nomsThreads[i]);
		}
	}

	/* Attente de terminaison de tous les threads */
	for (i=0; i<NBTHREADS; i++) {
		errcode=pthread_join (threads[i], NULL);
		if (errcode) { 
			fprintf(stderr, "erreur pthread_join pour le thread %s\n", nomsThreads[i]);
			exit(EXIT_FAILURE);
		}
	}

	/* -- A COMPLETER -- */
	/* Detruire les semaphores mutex */
	destroy_semaphore(writer);
	destroy_semaphore(mutex);
	free(writer);
	free(mutex);


	exit(EXIT_SUCCESS);
}

