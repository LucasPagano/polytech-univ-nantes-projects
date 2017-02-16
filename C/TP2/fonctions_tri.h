#ifndef _FONCTIONS_TRI_H
  #define _FONCTIONS_TRI_H

  typedef void (*algo_tri)(int* tableau,int longueur, int (*comp)(int, int)); // Definition des algorithmes de tri
  typedef int (*Comp)(int, int); // Definition de la fonction de comparaison

  void swap_tab(int *i, int *j); // Echange deux elements d'un tableau

  int partitionner(int* tab, int premier, int dernier, int pivot, int (*comp)(int, int));
  void qsort_tab(int* tab, int premier, int dernier, int (*comp)(int, int));
  void tri_rapide(int* tableau,int longueur, Comp comp); // Sert simplement a avoir la meme signature pour qsort_tab que les autres


  void tri_bulles(int* tableau,int longueur, Comp comp);

  void tri_insertion(int *tab, int taille, Comp comp);

  void tri_mr_anonyme(int *tab, int taille, Comp comp);

#endif
