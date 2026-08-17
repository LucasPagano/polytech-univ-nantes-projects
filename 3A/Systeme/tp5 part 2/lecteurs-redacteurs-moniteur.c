/*
*	Problème des Lecteurs rédacteurs
 *	bibliothèque pthread
 *	Solution avec moniteurs (mutex et conditions) 
 *	
 */

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "macros-thread.h"
#include "tprintf.h"

/* Notions Globales */

#define NBTHREADS 6




/* Définition du moniteur: variables d'état, mutex, conditions */
/* ----------------------------------------------------------- */

int nbLecteurs=0; /* Ressource Critique */
int nbRedacteurs=0; /* Ressource Critique */

/* -- A COMPLETER -- */
/* declarer mutex et conditions pour le moniteur */


/* Points d'entrée du moniteur proteges par E.M.*/
void m_prologue_lecteur(char *);
void m_epilogue_lecteur(char *);
void m_prologue_redacteur(char *);
void m_epilogue_redacteur(char *);


/* Fonctions du moniteur */

void m_prologue_lecteur(char * nom) {

	/* -- A COMPLETER -- */
	/* Section à mettre en Exclusion Mutuelle */
	/* par ex:
	tprintf("producteur %s demande P(mutex)...\n", nom);
	WAIT_MUTEX(mutex);
	tprintf("producteur %s obtient P(mutex)...\n", nom);
	*/

		/* code en E.M. sur mutex */

		/* -- A COMPLETER -- */
		/* gerer la synchronisation lecteurs/redacteurs */

		nbLecteurs ++;

		/* fin d'E.M. */

	/* -- A COMPLETER -- */
	/* Mettre fin à la section en Exclusion Mutuelle */
}


void m_epilogue_lecteur(char * nom) {

	/* -- A COMPLETER -- */
	/* Section à mettre en Exclusion Mutuelle */

		/* code en E.M. sur mutex */

		/* -- A COMPLETER -- */
		/* gerer la synchronisation lecteurs/redacteurs */

		nbLecteurs --;

		/* fin d'E.M. */

	/* -- A COMPLETER -- */
	/* Mettre fin à la section en Exclusion Mutuelle */
}



void m_prologue_redacteur(char * nom) {

	/* -- A COMPLETER -- */
	/* Section à mettre en Exclusion Mutuelle */

		/* code en E.M. sur mutex */

		/* -- A COMPLETER -- */
		/* gerer la synchronisation lecteurs/redacteurs */

		nbRedacteurs=1;

		/* fin d'E.M. */

	/* -- A COMPLETER -- */
	/* Mettre fin à la section en Exclusion Mutuelle */
}



void m_epilogue_redacteur(char * nom) {

	/* -- A COMPLETER -- */
	/* Section à mettre en Exclusion Mutuelle */

		/* code en E.M. sur mutex */

		/* -- A COMPLETER -- */
		/* gerer la synchronisation lecteurs/redacteurs */

		nbRedacteurs=0;

		/* fin d'E.M. */

	/* -- A COMPLETER -- */
	/* Mettre fin à la section en Exclusion Mutuelle */
}


/* fin de definition du moniteur */
/* ----------------------------- */



/* Fonction principales des threads "redacteur" */
void * redacteur(void * arg) {
	char * nom = *(char **)arg;

	tprintf("debut thread redacteur %s\n", nom);

	m_prologue_redacteur(nom);

	tprintf("%s ecrit...\n", nom);
	sleep(5+rand()%6);

	tprintf("%s etat du tampon partage: nbLecteurs=%i\n", nom, nbLecteurs);
	tprintf("%s a fini d ecrire...\n", nom);
	
	m_epilogue_redacteur(nom);

	tprintf("fin thread redacteur %s\n", nom);
	pthread_exit(0);
}

/* Fonction principales des threads "lecteur" */
void * lecteur(void * arg) {
	char * nom = *(char **)arg;

	tprintf("debut thread lecteur %s\n", nom);

	m_prologue_lecteur(nom);

	tprintf("%s lit...\n", nom);
	sleep(1+rand()%3);
	tprintf("%s a fini de lire...\n", nom);

	m_epilogue_lecteur(nom);

	tprintf("fin thread lecteur %s\n", nom);
	pthread_exit(0);
}


/* Fonction principales de demarrage et de creation des threads  */
int main () {

	pthread_t threads[NBTHREADS];
	char * nomsThreads[NBTHREADS]={"w1","r1", "r2", "r3", "w2", "r4"};
	void * (* thread_main) (void *);
	int i, errcode;

	/* -- A COMPLETER -- */
	/* initialiser les mutex et conditions du moniteur */

	nbLecteurs=0;
	nbRedacteurs=0;

	for ( i=0; i<NBTHREADS; i++ ){
		if ( nomsThreads[i][0]=='r' ) {
			thread_main = lecteur;
		} else if ( nomsThreads[i][0]=='w' ) {
			thread_main = redacteur;
		}
		errcode=pthread_create (&threads[i], NULL, thread_main, &nomsThreads[i]);
		if ( errcode != 0 ) {
			fprintf(stderr, "Erreur de creation du thread %s\n", nomsThreads[i]);
		}
	}

	for (i=0; i<NBTHREADS; i++) {
		errcode=pthread_join (threads[i], NULL);
		if (errcode) { 
		  fprintf(stderr, "erreur pthread_join pour le thread %s\n", nomsThreads[i]);
		exit(EXIT_FAILURE);
		}
	}

	/* -- A COMPLETER -- */
	/* Detruire les mutex et conditions du moniteur */

	exit(EXIT_SUCCESS);

}


