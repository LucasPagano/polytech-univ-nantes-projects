

/* Ensemble de macros bien pratiques pour les moniteurs pthreads */

#ifndef _MACROS_THREADS_H_ 
#define _MACROS_THREADS_H_


/* Mutex */

#define INIT_MUTEX(unMutex)	if (pthread_mutex_init(&(unMutex), NULL)) { perror("pthread_mutex_init (&(unMutex), NULL)"); pthread_exit(NULL);}

#define WAIT_MUTEX(unMutex)		if (pthread_mutex_lock(&(unMutex))) { perror("pthread_mutex_lock(&(unMutex))"); pthread_exit(NULL);}

#define SIGNAL_MUTEX(unMutex)	if (pthread_mutex_unlock(&(unMutex))) { perror("pthread_mutex_unlock(&(unMutex))"); pthread_exit(NULL);}

#define DESTROY_MUTEX(unMutex)	if (pthread_mutex_destroy(&(unMutex))) { perror("pthread_mutex_destroy(&(unMutex))"); pthread_exit(NULL);}


/* Conditions */

#define INIT_COND(uneCondition)	if (pthread_cond_init(&(uneCondition), NULL)) { perror("pthread_cond_init(&(uneCondition), NULL)"); pthread_exit(NULL);}

#define WAIT_COND(uneCondition,unMutex)	if (pthread_cond_wait(&(uneCondition), &(unMutex))) { perror("pthread_cond_wait(&(uneCondition), &(unMutex))"); pthread_exit(NULL);}

#define SIGNAL_COND(uneCondition)	if (pthread_cond_signal(&(uneCondition))) { perror("pthread_cond_signal(&(uneCondition))"); pthread_exit(NULL);}

#define DESTROY_COND(uneCondition)	if (pthread_cond_destroy(&(uneCondition))) { perror("pthread_cond_destroy(&(uneCondition))"); pthread_exit(NULL);}

#endif

