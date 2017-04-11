/* Jeux de ping pong entre 2 types de threads (ping et pong) */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#define _GNU_SOURCE        /* or _BSD_SOURCE or _SVID_SOURCE */
#include <unistd.h>
#include <sys/syscall.h>
#include <string.h>
#include "tprintf.h"
#include "macros-thread.h"
#include "semaphore-moniteur.h"

#define NBTHREADS 8
#define NBCOUPS 2

/* Ressources critiques */
char chaine_ping_pong[500]="";

/* Declaration et creation des semaphore de comptage globaux */
/* -- A COMPLETER -- */


/* Fonction principales des threads */

/* Threads de type Ping */
void * thread_ping_main(void * arg) {
	char * nom= *(char **)arg;
	char texte_message[30];
	int i;

	sprintf(texte_message, "thread %s (pid:%d tid:%ld)", nom, getpid(), syscall(SYS_gettid));
	tprintf("Debut du %s\n", texte_message);

	for (  i=0; i<NBCOUPS; i++) {

		/* prise d'un jeton dans le semaphore */
		/* -- A COMPLETER -- */

			/* Debut de section critique */
			tprintf("(%i) Debut section critique du %s\n", i, texte_message);
			sleep(rand()%3);
			strcat(chaine_ping_pong, nom);
			strcat(chaine_ping_pong, " ");
			tprintf( "(%i) %s\n", i, chaine_ping_pong);
			sleep(rand()%3);
			tprintf("(%i) Fin section critique du %s\n", i, texte_message);
			/* fin de section critique */

		/* liberation d'un jeton dans le semaphore */
		/* -- A COMPLETER -- */
	}
	tprintf("Fin du %s\n", texte_message);
	pthread_exit(0);

}


/* Threads de type Pong */
void * thread_pong_main(void * arg) {
	char * nom= *(char **)arg;
	char texte_message[30];
	int i;

	sprintf(texte_message, "thread %s (pid:%d tid:%ld)\0", nom, getpid(), syscall(SYS_gettid));
	tprintf("Debut du %s\n", texte_message);

	for (  i=0; i<NBCOUPS; i++) {

		/* prise d'un jeton dans le semaphore */
		/* -- A COMPLETER -- */

			/* Debut de section critique */
			tprintf("(%i) Debut section critique du %s\n", i, texte_message);
			sleep(rand()%3);
			strcat(chaine_ping_pong, nom);
			strcat(chaine_ping_pong, " ");
			tprintf( "(%i) %s\n", i, chaine_ping_pong);
			sleep(rand()%3);
			tprintf("(%i) Fin section critique du %s\n", i, texte_message);
			/* fin de section critique */

		/* liberation d'un jeton dans le semaphore */
		/* -- A COMPLETER -- */	
	}
	tprintf("Fin du %s\n", texte_message);
	pthread_exit(0);
}


/* Fonction principale (main du processus): demarrage et de creation des threads  */
int main () {

	int i, errcode;
	void * (* thread_main)(void *);

	/* Nommage des threads */
	char * nomsThreads[NBTHREADS]={"PONG","PING","PING","PONG","PING","PONG","PONG","PING"};

	/* identifiant des threads */
	pthread_t threads[NBTHREADS];


	/* Initialisation des semaphores de comptage */
	/* -- A COMPLETER -- */

	/* Creation puis lancement des threads */
	for ( i=0; i<NBTHREADS; i++ ){
		if( nomsThreads[i][1]=='I' ) 
			thread_main = thread_ping_main;
		else if ( nomsThreads[i][1]=='O' ) 
			thread_main = thread_pong_main;
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

	/* Destruction des semaphores de comptage */
	/* -- A COMPLETER -- */

	exit(EXIT_SUCCESS);
}

