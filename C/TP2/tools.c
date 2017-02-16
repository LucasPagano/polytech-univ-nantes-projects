#include "fonctions_tri.h"
#include "tools.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Fonction qui teste les fonctions de tri
void testDesFonctions(algo_tri *tableau_de_fonctions, int taille_tableau, int nombre_de_fonctions, Comp comp){
    int *tab = initialisation(taille_tableau); // On initialise le tableau
    double* tableau_des_temps = malloc(sizeof(double)*nombre_de_fonctions); // Tableau des temps

    for (int j=0; j<nombre_de_fonctions; j++)
    {
        int *tab2 = malloc(sizeof(int)*taille_tableau);
        dupliquer(tab, tab2, taille_tableau);


        clock_t start_t, end_t;
        double total_t;
        start_t = clock();
        tableau_de_fonctions[j](tab2, taille_tableau, comp);

        end_t = clock();

        total_t = (double)(end_t - start_t) / CLOCKS_PER_SEC;
        tableau_des_temps[j] = total_t;

        free(tab2);
    }
    printf("Les temps d'execution :\n \
    Pour le tri rapide : %f\n \
    Pour le tri a bulles : %f\n \
    Pour le tri par insertion : %f\n \
    Pour le tri de mr_anonyme : %f\n",
    tableau_des_temps[0], tableau_des_temps[1], tableau_des_temps[2], tableau_des_temps[3]);
    free(tableau_des_temps);
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
void afficherTableau(int* tab, int taille_tableau){
    printf("[%i",tab[0]);
    for (int i=1;i<taille_tableau;i++)
    {
        printf(",%i", tab[i]);
    }
    printf("]\n");
}

// Teste si le tableau est trié
int triValide(int* tab, int taille_tableau, Comp comp){
    int tri = 1;
    int cpt = 0;

    while (tri ==0 && cpt < taille_tableau -1)
    {
        if (comp(tab[cpt], tab[cpt+1]))
            tri = 0;
        cpt++;
    }
    return tri;
}

void dupliquer(int *tab1, int *tab2, int taille){
  for(int i=0;i<taille;i++)
    tab2[i] = tab1[i];

}
