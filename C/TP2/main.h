int* initialisation(int taille_tableau);
void afficherTableau(int* tab, int taille_tableau);
int triValide(int* tab, int taille_tableau, int (*comp)(int, int));
void testDesFonctions(void (*tableau_de_fonctions[2])(int*, int, int (*comp)(int, int)), int taille_tableau, int nombre_de_fonctions, int (*comp)(int, int), double* tableau_des_temps);



