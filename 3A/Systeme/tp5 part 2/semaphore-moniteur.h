/*
 *	Implementation de semaphore de comptage par moniteur
 *	biblioth�que pthread
 *	Moniteur = semaphores binaires(mutex) + conditions
 *	
 */

#ifndef _SEM_ 
#define _SEM_

#include <pthread.h>

/* Notions Globales */

typedef struct {
	pthread_mutex_t mutex;
	pthread_cond_t attenteFifo;
	int nbAttente;
	int value;
} type_semaphore;

void init_semaphore(type_semaphore * sem,int val);
void P_semaphore(type_semaphore * sem);
void V_semaphore(type_semaphore * sem);
void destroy_semaphore(type_semaphore * sem);
int value_semaphore(type_semaphore * sem);
int estVideFifo_semaphore(type_semaphore * sem);

#endif




