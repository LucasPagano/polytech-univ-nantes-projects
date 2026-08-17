	/*
*	Implementation d'un s�maphore de comptage par moniteurs 
*	biblioth�que pthread
*	moniteurs = semaphores binaires (mutex) + conditions	
*/

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include "tprintf.h"
#include "macros-thread.h"
#include "semaphore-moniteur.h"


/* D�finition du moniteur */
/* ---------------------- */


/* Points d'entr�e */
/* Fonctions du moniteur */
/* seulement 2 fonctions en exclusion mutuelle : P_semaphore() et V_semaphore() */
/* Attention, les autres fonction sne sont pas protegees */

void init_semaphore(type_semaphore * sem, int val){

	/* implementer l'initialisation de  tous les champs 
	   de l'enregistrement type-semaphore: mutex, attenteFifo, nbAttente, value 
	   utiliser INIT_MUTEX, et INIT_COND 
	*/

	/* -- A COMPLETER -- */
		INIT_MUTEX(sem->mutex);
		INIT_COND(sem->attenteFifo);
		sem->nbAttente = 0;
		sem->value = val;
}


void P_semaphore(type_semaphore * sem){

	/* implementer l'operateur semaphore P(sem) en exclusion mutuelle
	   modifie les champs de l'enregistrement type-semaphore: nbAttente, value 
	   utiliser WAIT_MUTEX, SIGNAL_MUTEX et WAIT_COND
	*/

	/* -- A COMPLETER -- */
	WAIT_MUTEX(sem->mutex);
	while(sem->value<=0){
		sem->nbAttente++;
		WAIT_COND(sem->attenteFifo, sem->mutex);
		sem->nbAttente--;
	}

	sem->value--;
	SIGNAL_MUTEX(sem->mutex);

}


void V_semaphore(type_semaphore * sem){

	/* implementer l'operateur semaphore V(sem) en exclusion mutuelle
	   modifie les champs de l'enregistrement type-semaphore: value 
	   utiliser WAIT_MUTEX, SIGNAL_MUTEX et SIGNAL_COND
	*/

	/* -- A COMPLETER -- */
	WAIT_MUTEX(sem->mutex);
	SIGNAL_COND(sem->attenteFifo);
	sem->value++;
	sem->nbAttente--;
	SIGNAL_MUTEX(sem->mutex);


}


void destroy_semaphore(type_semaphore * sem){

	/* implementer la destruction du semaphore 

	/* -- A COMPLETER -- */
	DESTROY_COND(sem->attenteFifo);
	DESTROY_MUTEX(sem->mutex);

}



int value_semaphore(type_semaphore * sem){

	/* implementer la lecture non protegee de la valeur du semaphore: champs value */

	/* -- A COMPLETER -- */
	return sem->value;

}


int estVideFifo_semaphore(type_semaphore * sem){

	/* implementer l'acces au nombre de processus en attente en liste Fifo semaphore: champs nbAttente */

	/* -- A COMPLETER -- */
	return (sem->nbAttente);

}


