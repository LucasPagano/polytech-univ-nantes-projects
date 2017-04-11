/* 	Utilitaire printf estampille : a reutiliser ... 
	print et fprintf prefixes par "seconds:microsecondes:pid: "  ou "seconds:microsecondes:pid:tid: "
*/

#define _GNU_SOURCE
#include <unistd.h>
#include <sys/syscall.h>
#include <stdarg.h>
#include <sys/time.h>
#include <stdio.h>
#include <stdlib.h>

#include "tprintf.h"



/* printf estampille : a reutiliser ... */

int tprintf(const char * message, ... ) {
	va_list liste;
	char chaine [1000];
	struct timeval t;
	int pid = getpid();
	int tid = (int) syscall(SYS_gettid);
	va_start(liste, message);
	gettimeofday( &t, NULL);
	vsprintf(chaine, message, liste);
	if ( pid == tid ) 
		return printf("%ld:%ld:%i: %s", t.tv_sec, t.tv_usec, pid, chaine );
	else
		return printf("%ld:%ld:%i:%i %s", t.tv_sec, t.tv_usec, pid, tid, chaine );
}

int tfprintf(FILE * stream, const char * message, ... ) {
	va_list liste;
	char chaine [1000];
	struct timeval t;
	int pid = getpid();
	int tid = (int) syscall(SYS_gettid);
	va_start(liste, message);
	gettimeofday( &t, NULL);
	vsprintf(chaine, message, liste);
	if ( pid == tid ) 
		return fprintf(stream, "%ld:%ld:%i: %s", t.tv_sec, t.tv_usec, pid, chaine );
	else
		return fprintf(stream, "%ld:%ld:%i:%i %s", t.tv_sec, t.tv_usec, pid, tid, chaine );
}

