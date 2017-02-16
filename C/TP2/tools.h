#ifndef _TOOLS_H
  #define _TOOLS_H

  int* initialisation(int taille_tableau); // Retourne un tableau de nombres aléatoires de taille taille_tableau
  void afficherTableau(int* tab, int taille_tableau);
  int triValide(int* tab, int taille_tableau, Comp comp); // Retourne 1 si tableau trié, 0 si non trié
  // Mesure le temps de tri des fonctions du tableau sur un même tableau
  void testDesFonctions(algo_tri *tableau_de_fonctions, int taille_tableau, int nombre_de_fonctions, Comp comp);
  void dupliquer(int *tab1, int *tab2, int taille); // Copie le tableau 1 dans le tableau 2

#endif
