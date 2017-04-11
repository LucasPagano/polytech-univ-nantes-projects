#include <signal.h>
#include <stdio.h>
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

//    printf("Entre ton nom.\n");
//    alarm(10);
//    for (int i=getchar(); i!='\n'; i=getchar()) printf("%c", i);

//    int pid = fork();
//    if (pid == 0) {
//        sleep(0.6);
//        int ppid = getppid();
//        for (int i = 1; i <= 100; i += 2) {
//            printf("%i\n", i);
//            kill(ppid, SIGUSR1);
//            pause();
//        }
//        kill(ppid, SIGUSR1);// ne pas oublier de réveiller le père à la fin de la boucle
//
//    } else {
//        pause();
//        for (int i = 2; i <= 100; i += 2) {
//            printf("%i\n", i);
//            kill(pid, SIGUSR1);
//            pause();
//        }
//        int status;
//        waitpid(pid, &status, 0);
//    }


    int pid = fork();
    if (pid == 0){
        static int nb = 0;
        while (nb<=5){
            alarm(2);
            pause();
            printf("*\n");
            nb++;
        }
        kill(getppid(), SIGKILL);
    } else {
        for (;;){
            alarm(1);
            pause();
            printf(".\n");
        }
    }

}