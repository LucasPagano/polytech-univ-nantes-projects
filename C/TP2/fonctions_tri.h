void swap_tab(int *i, int *j);
int partitionner(int* tab, int premier, int dernier, int pivot, int (*comp)(int, int));
void qsort_tab(int* tab, int premier, int dernier, int (*comp)(int, int));
void tri_bulles(int* tableau,int longueur, int (*comp)(int, int));
void tri_rapide(int* tableau,int longueur, int (*comp)(int, int));
