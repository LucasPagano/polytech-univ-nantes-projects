#include <signal.h>
#include <stdio.h>
#include <zconf.h>
#include <stdlib.h>
#include <wait.h>


void usr1_handler(int sig){
	static int nb=1;
	static int nbTerm=1;

	switch (sig){
		case SIGUSR1:
//            printf("signal %d recu %d fois.\n", sig, nb++);
			break;
		case SIGTERM:
			if (nbTerm==1) printf("Pour la première fois Non.\n");
			else printf("Pour la %ième fois Non.\n", nbTerm);
			nbTerm++;
			break;
//        case SIGALRM:
//            printf("Trop lent.\n");
//            exit(EXIT_FAILURE);
	}
}

int main(){
	struct sigaction act, oact; // struct création et récup de l'ancien gestionnaire de signaux

	sigaction(SIGUSR1, NULL, &oact);
	act.sa_handler=usr1_handler; // install new handler
	act.sa_mask=oact.sa_mask; // keep former mask
	act.sa_flags=SA_RESTART; // appels systèmes redémarrés après avoir interrompus par le signal

	sigaction(SIGUSR1, &act, NULL);
	sigaction(SIGTERM, &act, NULL);
	sigaction(SIGALRM, &act, NULL);

	int pid = getpid();
	kill(pid, SIGTERM);
	int status;
	pid = fork();
	if (pid == 0){
			kill(getpid(), SIGTERM);
	}else{
		waitpid(pid, &status, 0);
	}
}

