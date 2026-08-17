#include "fonctions_tri.h"
#include <stdio.h>
#include <stdlib.h>

void tri_bulles(int* tableau,int longueur, Comp comp)
{
     int i, inversion;

     do
       {
       inversion=0;

       for(i=0;i<longueur-1;i++)
          {
          if (tableau[i]>tableau[i+1])
             {
             swap_tab(&tableau[i],&tableau[i+1]);
             inversion=1;
             }
          }
       }
     while(inversion);
}

void tri_rapide(int* tableau,int longueur, Comp comp)
{
    qsort_tab(tableau, 0, longueur-1, comp);
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

int partitionner(int* tab, int premier, int dernier, int pivot, Comp comp)
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

void tri_insertion(int tab[], int taille, Comp comp)
{
   int i, j;
   for (i = 1; i < taille; ++i) {
       int elem = tab[i];
       for (j = i; j > 0 && comp(elem,tab[j-1]); j--)
           tab[j] = tab[j-1];
       tab[j] = elem;
   }
}

void tri_mr_anonyme(int *tab, int taille, Comp comp) {

  for (int i = 0; i < taille; i++) {
    int index = i;
    for(int j = i+1; j < taille; j++) {
      if (comp(tab[j],tab[index])) {
        index = j;
      }
    }
    swap_tab(&tab[i], &tab[index]);
  }

}

void swap_tab(int* i, int* j)
{
    int temp = *i;
    *i = *j;
    *j = temp;

}
