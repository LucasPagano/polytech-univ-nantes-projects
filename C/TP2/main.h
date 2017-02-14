void initialisation(int* tab, int taille_tableau);
void afficherTableau(int* tab, int taille_tableau);
int testTri(int* tab, int taille_tableau, int (*comp)(int, int));
void swap_tab(int *i, int *j);
int partitionner(int* tab, int premier, int dernier, int pivot, int (*comp)(int, int));
void qsort_tab(int* tab, int premier, int dernier, int (*comp)(int, int));
int inf(int a, int b);
int sup(int a, int b);
int pair_croissant(int a, int b);
int impair_croissant(int a, int b);
void tri_shell(int* tableau,int longueur, int (*comp)(int, int));
void tri_rapide(int* tableau,int longueur, int (*comp)(int, int));

