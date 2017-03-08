#include <stdio.h>
#include <fcntl.h>
#include <zconf.h>
#include <sys/stat.h>
#include <stdlib.h>

int main() {

    // On lit une première fois
    printf("Premiere lecture\n");
    int desc= open("../a.txt", O_RDONLY | O_RDWR);
    char buf[20];
    int taille;
    struct stat *bufstat = malloc(sizeof(struct stat));

    printf("Nombre de caractères lus %i\n", taille = (int)read(desc, &buf, 20));
    buf[taille] = '\0';
    printf("%s\n", (char*)&buf);
    stat("../a.txt", bufstat);
    printf("Numéro d'i_noeud %i, FileDescriptor %i\n", (int)bufstat->st_ino, desc);

    // On écrit
    char buf_ecrit[3] = {'\n', 'm','2'};
    write(desc, buf_ecrit, 3);

    // On lit une deuxième fois
    printf("Deuxième lecture\n");
    int desc2 = open("../a.txt", O_RDONLY | O_RDWR);
    char buf2[20];
    int taille2;
    struct stat *bufstat2 = malloc(sizeof(struct stat));

    printf("Nombre de caractères lus %i\n", taille2 = (int)read(desc2, &buf2, 20));
    buf2[taille2] = '\0';
    printf("%s\n", (char*)&buf2);
    stat("../a.txt", bufstat2);
    printf("Numéro d'i_noeud %i, FileDescriptor %i\n", (int)bufstat2->st_ino, desc2);



    return 0;
}