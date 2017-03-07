#include <stdlib.h>
#include <stdio.h>

// compilation : gcc -o ds Arbre.c
typedef union
{
  int iNombre;
  char cSigne;
} UValeur;

typedef struct Sommet{
  UValeur valeur;
  struct Sommet *filsG, *filsD;
} Sommet;

typedef Sommet* Arbre;

// Prototypes
Arbre ConstruireFeuille(int iEntier);
Arbre ConstruireNoeud(char cOperateur, Arbre gauche, Arbre droit);
void Infixe(Arbre tree);
void Prefixe(Arbre tree);


int main(){
  Arbre arbre1 = ConstruireFeuille(12);
  Arbre arbre2 = ConstruireFeuille(15);

  Arbre arbre3 = ConstruireNoeud('+', arbre1, arbre2);

  Arbre arbre4 = ConstruireFeuille(7);
  Arbre arbre5= ConstruireFeuille(10);

  Arbre arbre6 = ConstruireNoeud('*', arbre4, arbre5);

  Arbre arbre7 = ConstruireNoeud('-', arbre3, arbre6);
  // printf("%c, %i, %i\n", arbre3->valeur.cSigne, arbre3->filsG->valeur.iNombre, arbre3->filsD->valeur.iNombre);

  Prefixe(arbre7);
}

Arbre ConstruireFeuille(int iEntier){
  Arbre arbre = malloc(sizeof(Arbre));
  arbre->valeur.iNombre = iEntier;
  return arbre;
}

Arbre ConstruireNoeud(char cOperateur, Arbre gauche, Arbre droit){
  Arbre arbre = malloc(sizeof(Arbre));
  arbre->valeur.cSigne = cOperateur;
  arbre->filsG = gauche;
  arbre->filsD = droit;
}

void Infixe(Arbre tree){
  Arbre gauche = tree->filsG;
  Arbre droit = tree->filsD;

  if (gauche != NULL){
    Infixe(gauche);
  } else{
    printf("(%i", tree->valeur.iNombre);
  }

  printf("%c", tree->valeur.cSigne);

  if (droit != NULL){
    if (droit->filsG != NULL){
      Infixe(droit);
    }else{
    printf("%i)", droit->valeur.iNombre);
    }
  }
}

void Prefixe(Arbre tree){
  Arbre gauche = tree->filsG;
  Arbre droit = tree->filsD;

  printf("%c ", tree->valeur.cSigne);

  if (gauche != NULL){
    Prefixe(gauche);
  } else{
    printf("%i ", tree->valeur.iNombre);
  }

  if (droit != NULL){
    if (droit->filsG != NULL){
      Prefixe(droit);
    }else{
    printf("%i ", droit->valeur.iNombre);
    }
  }
}
