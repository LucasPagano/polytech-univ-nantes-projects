#include <stdio.h>
#include <zconf.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <signal.h>
#include <fcntl.h>
#include <string.h>

static int desc;

void usr1_handler(int sig){
	static int nb=1;
	static int nbTerm=1;

	switch (sig){
		case SIGUSR1:
            write(desc, "Je ne suis pas mort.\n", 21);
			break;
	}
}
int main(int argc, char *argv[]){

	desc = open("log", O_RDWR | O_CREAT, 0777);
	//Gestion des signaux
	struct sigaction act, oact; // struct création et récup de l'ancien gestionnaire de signaux

	sigaction(SIGUSR1, NULL, &oact);
	act.sa_handler=usr1_handler; // install new handler
	act.sa_mask=oact.sa_mask; // keep former mask
	act.sa_flags=SA_RESTART; // appels systèmes redémarrés après avoir interrompus par le signal

	sigaction(SIGUSR1, &act, NULL);

	if (argc>=2 && !(strcmp(argv[1], "-d"))) {

		// Création de daemon
		// fork
		int pid = fork();
		if (pid < 0) {
			exit(EXIT_FAILURE);
		}
		if (pid > 0) {
			exit(EXIT_SUCCESS);
		}
		umask(0);
		// detach from session
		int sid = setsid();
		if (sid < 0) {
			exit(EXIT_FAILURE);
		}
		// change dir
		if (chdir("/") < 0) {
			exit(EXIT_FAILURE);
		}

		printf("Je suis un daemon et mon pid est : %i\n", getpid());
		// close fd
		close(STDIN_FILENO);
		close(STDOUT_FILENO);
		close(STDERR_FILENO);
	}

	printf("Mon pid est : %i\n", getpid());

	for(;;);

	return 0;
}