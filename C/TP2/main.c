#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "main.h"

int main()
{
    srand(time(NULL));

    int (*comp)(int, int) = inf; // définition de la fonction de comparaison

    int taille_tableau = 1000;
    int* tab = malloc(sizeof(int)*taille_tableau);

    initialisation(tab, taille_tableau); // On initialise le tableau
    //afficherTableau(tab, taille_tableau);

    clock_t start_t, end_t;
    double total_t;
    start_t = clock();
    tri_rapide(tab, taille_tableau, comp); // On le trie
    end_t = clock();
    total_t = (double)(end_t - start_t) / CLOCKS_PER_SEC;
    printf("Temps d'exécution: %f\n", total_t  );
    //afficherTableau(tab, taille_tableau);


    return 0;
}

// Initialise le tableau
void initialisation(int* tab, int taille_tableau){
    for (int i=0;i<taille_tableau;i++)
    {
        tab[i] = rand()%100;
    }
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

int testTri(int* tab, int taille_tableau, int (*comp)(int, int))
{
    int tri = 0;
    int cpt = 0;
    while (tri ==0 && cpt < taille_tableau -1)
    {
        if ((comp(tab[cpt], tab[cpt+1])))
            tri = 1;
        cpt++;
    }
    return tri;
}


void qsort_tab(int* tab, int premier, int dernier, int (*comp)(int, int))
{
    if (premier<dernier)
    {
        int pivot = premier;
        pivot = partitionner(tab, premier, dernier, pivot, comp);
        qsort_tab(tab, premier, pivot-1, comp);
        qsort_tab(tab, pivot+1, dernier, comp);
    }

}

int partitionner(int* tab, int premier, int dernier, int pivot, int (*comp)(int, int))
{
    swap_tab(&tab[pivot], &tab[dernier]);
    int j = premier;
    for (int i=premier; i<dernier; i++)
    {

        if (comp(tab[i],tab[dernier]))
        {
            swap_tab(&tab[i], &tab[j]);
            j++;
        }
    }
    swap_tab(&tab[j], &tab[dernier]);
    return j;
}

void swap_tab(int* i, int* j)
{
    int temp = *i;
    *i = *j;
    *j = temp;

}

int inf(int a, int b)
{
    return a <= b;
}

int sup(int a, int b)
{
    return a >= b;
}

int pair_croissant(int a, int b)
{
    if (a%2 == 0 && b%2 == 0)
        return a < b;
    else if (a%2 == 0 && b%2 != 0)
        return 1;
    else if (a%2 == 1 && b%2 == 0)
        return 0;
    else
        return a < b;
}

int impair_croissant(int a, int b)
{
    if (a%2 == 0 && b%2 == 0)
        return a < b;
    else if (a%2 == 0 && b%2 != 0)
        return 0;
    else if (a%2 == 1 && b%2 == 0)
        return 1;
    else
        return a < b;
}

void tri_shell(int* tableau,int longueur, int (*comp)(int, int))
{
    int n, i, j, valeur;
    n=0;
    while(n<longueur)
    {
        n=3*n+1;
    }
    while(n!=0)
    {
        n=n/3;
        for (i=n; i<longueur; i++)
        {
            valeur=tableau[i];
            j=i;

            while((j>(n-1)) && (comp(valeur,tableau[j-n])))
            {
                tableau[j]=tableau[j-n];
                j=j-n;
            }
            tableau[j]=valeur;
        }
    }
}

// Sert simplement à avoir la même signature pour qsort_tab que les autres
void tri_rapide(int* tableau,int longueur, int (*comp)(int, int))
{
    qsort_tab(tableau, 0, longueur-1, comp);
}

