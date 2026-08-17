/* 	Utilitaire printf estampille : a reutiliser ... 
	print et fprintf prefixes par "seconds:microsecondes:pid: "  ou "seconds:microsecondes:pid:tid: "
*/

#include <stdarg.h>


/* printf estampille : a reutiliser ... */
int tprintf( const char *, ... );
int tfprintf(FILE * stream, const char * message, ... );

