#include <stdio.h>
#include <stdlib.h>
#include "fonctions_tri.h"

void tri_bulles(int* tableau,int longueur, int (*comp)(int, int))
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


// Sert simplement à avoir la même signature pour qsort_tab que les autres
void tri_rapide(int* tableau,int longueur, int (*comp)(int, int))
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
