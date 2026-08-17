/* 
 * Prise et liberation d'un semaphore de comptage 
 * par 2 processus thread T1 et T2 = exclusion mutelle.
 *
 * pour visualiser les threads pendant l'execution, 
 * utilisez la commande shell "ps -aLm" par exemple 
 */

#include <pthread.h>
#define _GNU_SOURCE
#include <unistd.h>
#include <sys/syscall.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include "tprintf.h"
#include "macros-thread.h"
#include "semaphore-moniteur.h"

/* nombre de threads crees: 2 */
#define NBTHREADS 2
/* NB: vous pourrez ensuite essayer de creer plus de 2 threads */

#define NBCOUPS 3

/* Declaration et creation du semaphore de comptage global */
type_semaphore static *sem;
/* -- A COMPLETER -- */

/* ressource critique */
int compteur = 0;



/* Fonction principales des threads */
void * thread_main(void * arg) {
	char *nom= *(char **)arg;
	char texte_message[30];
	int i;

	sprintf(texte_message, "thread %s (pid:%d tid:%ld)", nom, getpid(), syscall(SYS_gettid));
	tprintf("Debut du %s\n", texte_message);


	/* prise et liberation du semaphore */
	for ( i=0; i<NBCOUPS; i++) {

		/* prise d'un jeton dans le semaphore */
		/* -- A COMPLETER -- */
		P_semaphore(sem);

			/* Debut de section critique */
			tprintf("(%i) Debut section critique du %s\n", i, texte_message);

			compteur ++;
			tprintf("(%i) compteur=%i pour le %s\n", i, compteur,texte_message);
			sleep(rand()%3);

			tprintf("(%i) Fin section critique du  %s\n", i, texte_message);
			/* fin de section critique */

		/* liberation d'un jeton dans le semaphore */
		/* -- A COMPLETER -- */
		V_semaphore(sem);

		sleep(1);
		
	}
	tprintf("Fin du %s\n", texte_message);
	pthread_exit(NULL);
}




/* Fonction principale (main du processus): demarrage et de creation des threads  */
int main () {
	int i, errcode;

	/* Nommage des threads */
	char * nomsThreads[NBTHREADS]={"T1","T2"};

	/* identifiant des threads */
	pthread_t threads[NBTHREADS];


	/* Initialisation du semaphore de comptage � 1 */
	/* NB: Essayez ensuite avec les valeurs initiales 0, 2, 3 */
	/* -- A COMPLETER -- */
	sem = malloc(sizeof(type_semaphore));
	init_semaphore(sem, 1);


	/* Creation puis lancement des threads */
	for ( i=0; i<NBTHREADS; i++ ){
		errcode = pthread_create (&threads[i], NULL, thread_main, &nomsThreads[i]);
		if ( errcode != 0 ) {
			fprintf(stderr, "Erreur de creation du thread %s\n", nomsThreads[i]);
		}
	}


	/* Attente de terminaison de tous les threads */
	for ( i=0; i<NBTHREADS; i++) {
		errcode = pthread_join (threads[i], NULL);
		if (errcode) { 
			fprintf(stderr, "erreur pthread_join pour le thread %s\n", nomsThreads[i]);
			exit(EXIT_FAILURE);
		}
	}

	/* Destruction du semaphore de comptage */
	/* -- A COMPLETER -- */
	destroy_semaphore(sem);
	free(sem);
	exit(EXIT_SUCCESS);
}

