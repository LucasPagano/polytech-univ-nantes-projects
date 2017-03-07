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
    char* balise_fermante;
    int sortie = 1; // nous permet de sortir du while
    char **pile = malloc(sizeof(char*)*100);
    int indice = 0; // indice de la pile
    // Ouverture du fichier
    if ((pFile = fopen("fichier.html", "rt")))
        {

        // Test en soi
        while(sortie != 0){
            switch(curEtat){
            case SEtatDebut:
                // On passe les éventuels espaces et sauts de ligne
                do{
                        c = fgetc(pFile);
                } while ((c == '\n') || (c == ' '));
                if (c == '<'){
                    curEtat = SEtat1;}
                else
                    curEtat = SEtatErreur;
                break;

            // On récupère le premier mot et on le stocke dans la pile
            case SEtat1:
                pile[indice] = malloc(sizeof(char) * 20);
                c = recup(pFile, pile[indice]);
                indice++;
                curEtat = SEtat2;
                break;

            // On passe tous les mots inutiles
            // On teste si c'est une balise fermante ou une autre ouvrante
            case SEtat2:
                while (c != '<')
                    c = fgetc(pFile);

                c = fgetc(pFile);
                if (c == '/')
                    curEtat = SEtat3;
                else if (c >= 'A' && c <= 'z')
                {
                    curEtat = SEtat1;
                    fseek(pFile, -1, SEEK_CUR); // On revient un en arrière pour prendre le mot correctement
                }
                else
                    curEtat = SEtatErreur;

                break;

            case SEtat3:
                balise_fermante = malloc(sizeof(char) * 20);
                c = recup(pFile, balise_fermante);
                if (strcmp(pile[indice-1], balise_fermante) == 0)
                {
                    pile[indice] = "";
                    indice--;
                    if (indice == 0)
                        curEtat = SEtatReussite;
                    else
                        curEtat = SEtat2;

                } else
                {
                    curEtat = SEtatErreur;
                }
                free(balise_fermante);
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
    fclose(pFile);
    return 0;
}

// Recupere le premier mot de la balise et le stocke dans la pile
char recup(FILE* pFile, char* str){
    char c;
    int cpt = 0;
    do{
        c=fgetc(pFile);
        str[cpt] = c;
        cpt++;
    }while(c != ' ' && c != '>' && c != '/');
    str[cpt-1] = '\0';
    return c;
}

void afficherStr(char* str){
    for(int i=0; i<20 && str[i]!='\0'; i++){
        printf("%c",str[i]);
    }
    printf("\n");
}
