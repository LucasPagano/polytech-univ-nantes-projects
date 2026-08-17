#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int* generator(int n)
{
	int *tab=(int*)malloc(n*sizeof(int));
	for (int i=0;i<n;++i) tab[i]=i+1;
	return(tab);
}

void afficher(int *tab,int n)
{
	printf("[");
	for (int i=0;i<n;++i)
	{
		if (i) printf(", ");
		printf("%d",tab[i]);
	}
	printf("]\n");
}

int rrand(int n)
{
	return((int)((rand()/(((double)RAND_MAX)+1))*n));
}

void shuffle(int *tab,int n)
{
	if (n>2)
	{
		for (int i=n-1;i>=1;i--)
		{
			int j=rrand(i);
			int tmp=tab[j];
			tab[j]=tab[i];
			tab[i]=tmp;
		}
	}
}

int main()
{
	srand(time(NULL));
	int *tab=generator(10);
	afficher(tab,10);
	shuffle(tab,10);
	afficher(tab,10);

	free(tab);

	return(0);
}
