#include <stdlib.h>
#include <stdio.h>
#include "Notes.h"


// ligne de compilation : gcc -o Notes Notes.c

int main(){
  SEleve tab[50];
  FILE *f;
  unsigned int size;
  if ((f = fopen("liste_eleves.txt", "r"))!=NULL){
      size = ChargementMemoire(f, tab, 50);
      AffichageSupMoyenne(tab, size, 12.0);
      MoyenneDe("Dupont", tab, size);
  }
  return 0;
}
