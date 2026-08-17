#include <stdio.h>
#include <stdlib.h>
#include <string.h>


// ligne de compilation : gcc -o Notes Notes.c



struct SEleve{
  double moyenne;
  char *nom;
};

typedef struct SEleve SEleve;

//Prototypes
unsigned int ChargementMemoire(FILE *f, SEleve tab[], unsigned int size);
char* lireLigne(FILE *f);
void remplirCase(SEleve *eleve, char *ligne);
void AffichageSupMoyenne(SEleve tab[], unsigned int size, double moyenneMin);
double MoyenneDe(char *nom, SEleve tab[], unsigned int size);


int main()
{
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

unsigned int ChargementMemoire(FILE *f, SEleve tab[], unsigned int size){
  int rempli = 0;
  char *ligne;

  while(!(feof(f))){
      ligne = lireLigne(f);
      remplirCase(&tab[rempli], ligne);
      rempli +=1;

  }
  return rempli;
}

char* lireLigne(FILE *f){
  char *ligne;
  char c;
  int cpt = 0;
  while((c = fgetc(f)) != '\n'){
    ligne[cpt] = c;
    cpt ++;
  }
  ligne[cpt+1] = '\0';

  return ligne;
}

void remplirCase(SEleve *eleve, char *ligne){
  int cpt = 0;
  int cpt2 = 0;
  char* nom;
  char* moyenne;
  double moy =0;
  int puissance = 0.1;
  // On remplit le nom
  while (ligne[cpt] != ' '){
    nom[cpt] = ligne[cpt];
    cpt++;
  }
  nom[cpt+1]='\0';
  eleve->nom = nom;

  // On remplit la moyenne, on n'a plus le \n donc on suppose qu'il n'y a qu'une décimale
  while(ligne[cpt] != '.'){
    cpt++;
    moyenne[cpt2] = ligne[cpt];
    cpt2++;
  }
  // On ajoute la décimale
  moyenne[cpt2+1] = ligne[cpt+2];

  // conversion en double
  while (cpt2 >=0){
    moy += moyenne[cpt2]*puissance;
    cpt2--;
  }

  eleve->moyenne = moy;
}

void AffichageSupMoyenne(SEleve tab[], unsigned int size, double moyenneMin){
  for (int i=0; i<=size ;i++){
    if (tab[i].moyenne > moyenneMin){
      printf("La moyenne de %s est : %f\n", tab[i].nom, tab[i].moyenne);
    }
  }
}

double MoyenneDe(char *nom, SEleve tab[], unsigned int size){
  int trouve = 0;
  double retour = 0.0;
  for (int i =0; i<=size; i++){
    if (strcmp(tab[i].nom, nom) == 0){
      retour = tab[i].moyenne;
      trouve = 1;
    }
  }
  if (trouve){
    return retour;
  } else{
    return -1;
  }
}
