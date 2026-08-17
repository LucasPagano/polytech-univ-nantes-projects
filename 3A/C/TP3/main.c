#include <stdlib.h>
#include <stdio.h>
#include "SBlock.h"
#include "AncienListe.h"
#include <time.h>

void PrintList(SList *list);

int main()
{
	// L'allocation par blocks est traitée dans SBlock.c et la version de base dans AncienListe.c

	int nombreOperations = 10000000;

	clock_t start, end;
	double cpu_time_used;
	start = clock();
	SList *list;
	SCell *cell;

	list=CreateList();

	AddElementBegin(list,5);
	cell=AddElementBegin(list,3);
	AddElementBegin(list,1);

	AddElementEnd(list,6);
	AddElementEnd(list,7);

	AddElementAfter(list,cell,4);

	// Modification du fichier de test pour que les temps soient impactés
	for (int i=0;i<nombreOperations;i++){
		AddElementAfter(list,GetFirstElement(list),i);
	}

	for (int i=0;i<nombreOperations;i++){
		DeleteCell(list,GetFirstElement(list));
	}

	DeleteList(list);

	end = clock();
	cpu_time_used = ((double)end-start)/CLOCKS_PER_SEC;
	printf("Le temps avec blocks a été de %f secondes\n", cpu_time_used);

	clock_t start2, end2;
	double cpu_time_used2;
	start2 = clock();
	SList *list2;
	SCell *cell2;

	list2=AncienCreateList();

	AncienAddElementBegin(list2,5);
	cell2=AncienAddElementBegin(list2,3);
	AncienAddElementBegin(list2,1);

	AncienAddElementEnd(list2,6);
	AncienAddElementEnd(list2,7);

	AncienAddElementAfter(list2,cell2,4);

	// Modification du fichier de test pour que les temps soient impactés
	for (int i=0;i<nombreOperations;i++){
		AncienAddElementAfter(list,GetFirstElement(list),i);
	}

	for (int i=0;i<nombreOperations;i++){
		AncienDeleteCell(list,GetFirstElement(list));
	}

	AncienDeleteList(list2);

	end2 = clock();
	cpu_time_used2 = ((double)end2-start2)/CLOCKS_PER_SEC;
	printf("Le temps sans blocks a été de %f secondes\n", cpu_time_used2);

	return 0;
}


void PrintList(SList *list)
{
	if (list)
	{
		SCell *cell=GetFirstElement(list);
		while (cell!=NULL)
		{
			printf("[%d] -> ",GetData(cell));
			cell=GetNextElement(cell);
		}
		printf("NULL\n");
	}
}
