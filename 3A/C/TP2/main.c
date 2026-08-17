#include "fonctions_tri.h"
#include "fonctions_comparaison.h"
#include "tools.h"

#include <time.h>
#include <stdlib.h>

// LIGNE DE COMPILATION : gcc -Wall fonctions_comparaison.c fonctions_tri.c main.c tools.c -o tp3.exe

int main()
{
    srand(time(NULL));
    Comp comp = inf; // La fonction de comparaison
    int const nombre_de_fonctions = 4; // Le nombre de fonctions de tri

    algo_tri tableau_de_fonctions[nombre_de_fonctions];
    tableau_de_fonctions[0] =  &tri_rapide;
    tableau_de_fonctions[1] =  &tri_bulles;
    tableau_de_fonctions[2] = &tri_insertion;
    tableau_de_fonctions[3] = &tri_mr_anonyme;

    int taille_tableau = 20000; // Taille du tableau aléatoire

    testDesFonctions(tableau_de_fonctions, taille_tableau, nombre_de_fonctions, comp);
    return 0;
}
