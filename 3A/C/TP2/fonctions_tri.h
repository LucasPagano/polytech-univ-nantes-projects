#ifndef _FONCTIONS_TRI_H
  #define _FONCTIONS_TRI_H

  typedef int (*Comp)(int, int); // Definition de la fonction de comparaison
  typedef void (*algo_tri)(int*, int, Comp); // Definition des algorithmes de tri

  void swap_tab(int *i, int *j); // Echange deux elements d'un tableau

  int partitionner(int* tab, int premier, int dernier, int pivot, Comp comp);
  void qsort_tab(int* tab, int premier, int dernier, int (*comp)(int, int));
  void tri_rapide(int* tableau,int longueur, Comp comp); // Sert simplement a avoir la meme signature pour qsort_tab que les autres


  void tri_bulles(int* tableau,int longueur, Comp comp);

  void tri_insertion(int *tab, int taille, Comp comp);

  void tri_mr_anonyme(int *tab, int taille, Comp comp);

#endif
