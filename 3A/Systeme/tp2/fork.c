#include <stdlib.h>
#include <zconf.h>
#include <stdio.h>
#include <wait.h>

int main(int argc, char *argv[], char *envp[]) {
    printf("%s\n", argv[0]);
    int pid = fork();

    if (pid == 0) {
        printf("Fils: mon pid est %i\n", getpid());
//        char *argv2[4] = {"/bin/bash", "-c", "echo coucou > test", NULL};
//        int retval = execv("/bin/bash", argv2); // Écrase ce qui suit
        int retval = system("/bin/ls -l");
        if (retval != 0){
            fprintf(stderr, "Error: %d\n", retval);
            perror("Exec did not work: ");
        }

        sleep(3); // Le père m'attend

    } else {
        printf("Père: le pid de mon fils est %i\n", pid);
        int status;
        waitpid(pid, &status, 0);
        printf("Père: mon fils est mort avec le status %i", status);
    }


}