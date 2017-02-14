#include <stdio.h>
#include <stdlib.h>
#include "parseur.h"
#include <string.h>

int main()
{
    parseur();
    return 0;
}


int parseur()
{
    enum Etats curEtat = SEtatDebut;
    FILE *pFile;
    char c;
    int sortie = 1; // nous permet de sortir du while
    char **pile = malloc(sizeof(char*)*100);
    int indice = 0; // indice de la pile
    // Ouverture du fichier
    if ((pFile = fopen("exemple_SGML", "rt")))
        {

        // Test en soi
        while(sortie != 0){
            switch(curEtat){
            case SEtatDebut:
                // On passe les éventuels espaces et sauts de ligne
                do{
                        c = fgetc(pFile);
                } while ((c == '\n') && (c == ' '));
                if (c == '<')
                    curEtat = SEtat1;
                else
                    curEtat = SEtatErreur;
                break;

            case SEtat1:
                // On récupère le premier mot
                pile[indice] = malloc(sizeof(char) * 20);
                c = recup(pFile, pile[indice]);
                printf('%c',c);
                afficherStr(pile[indice]);
                indice++;
                break;

            // Etat de reussite
            case SEtatReussite:
                printf("Le fichier est conforme ");
                sortie = 0;
                break;

            // Etat d'erreur
            case SEtatErreur:
                printf("Le fichier n'est pas conforme");
                sortie = 0;
                break;
            }
        }
    }
}

// Recupere le premier mot de la balise
char recup(FILE* pFile, char* str){
    char c;
    int cpt = 0;
    do{
        c=fgetc(pFile);
        str[cpt] = c;
        cpt++;
    }while(c != ' ' && c != '>' && c != '/');
    str[cpt] = '\0';
    return c;
}

void afficherStr(char* str){
    char c;
    for(int i=0; i<20 && c!='\0'; i++){
        printf("%c",c);
    }
    printf("\n");
}
