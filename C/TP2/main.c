#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "main.h"
#include "fonctions_tri.h"
#include "fonctions_comparaison.h"
#include <string.h>

int main()
{
    srand(time(NULL));

    int (*comp)(int, int) = inf; // définition de la fonction de comparaison
    void (*tableau_de_fonctions[2])(int*, int, int (*comp)(int, int)) = {tri_rapide, tri_bulles};
    int nombre_de_fonctions = 2;

    int taille_tableau = 20000; // Taille des tableaux aléatoires
    double* tableau_des_temps;
    tableau_des_temps = malloc(sizeof(double)*nombre_de_fonctions);

    testDesFonctions(tableau_de_fonctions, taille_tableau, nombre_de_fonctions, comp, tableau_des_temps);

    printf("Les temps d'execution :\n Pour le tri rapide : %f\n Pour le tri a bulles : %f\n", tableau_des_temps[0], tableau_des_temps[1]);

    return 0;
}

// Fonction qui teste les fonctions de tri
void testDesFonctions(void (*tableau_de_fonctions[2])(int*, int, int (*comp)(int, int)), int taille_tableau, int nombre_de_fonctions, int (*comp)(int, int), double* tableau_des_temps)
{

    for (int j=0; j<nombre_de_fonctions; j++)
    {
        int *tab = initialisation(taille_tableau); // On initialise le tableau
        clock_t start_t, end_t;
        double total_t;
        start_t = clock();
        tableau_de_fonctions[j](tab, taille_tableau, comp);

        end_t = clock();

        total_t = (double)(end_t - start_t) / CLOCKS_PER_SEC;
        tableau_des_temps[j] = total_t;
    }
}


// Initialise le tableau
int* initialisation(int taille_tableau){

    int* tab = malloc(sizeof(int)*taille_tableau);
    for (int i=0;i<taille_tableau;i++)
    {
        tab[i] = rand()%taille_tableau;
    }
    return tab;
}

// Affiche le tableau
void afficherTableau(int* tab, int taille_tableau)
{
    printf("[%i",tab[0]);
    for (int i=1;i<taille_tableau;i++)
    {
        printf(",%i", tab[i]);
    }
    printf("]\n");
}

// Teste si le tableau est trié
int triValide(int* tab, int taille_tableau, int (*comp)(int, int))
{
    int tri = 0;
    int cpt = 0;

    while (tri ==0 && cpt < taille_tableau -1)
    {
        if (comp(tab[cpt], tab[cpt+1]))
            tri = 1;
        cpt++;
    }
    return tri;
}

