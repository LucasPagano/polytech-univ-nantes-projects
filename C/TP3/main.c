#include <stdlib.h>
#include <stdio.h>
#include "Liste.h"

void PrintList(SList *list);

int main()
{
	SList *list;
	SCell *cell;

	list=CreateList();


	printf("Add 5, 3, 1 at beginning\n");
	AddElementBegin(list,5);
	cell=AddElementBegin(list,3);
	AddElementBegin(list,1);
	PrintList(list);
	printf("\n");

	printf("Add 6, 7 at end\n");
	AddElementEnd(list,6);
	AddElementEnd(list,7);
	PrintList(list);
	printf("\n");

	printf("Add 4 after 3 (cell)\n");
	AddElementAfter(list,cell,4);
	PrintList(list);
	printf("\n");


	for (int i=0;i<3;i++){
		printf("Delete head\n");
		DeleteCell(list,GetFirstElement(list));
		PrintList(list);
		printf("\n");
	}

	for (int i=0;i<3;i++){
		printf("Add %i after first\n", i);
		AddElementAfter(list,GetFirstElement(list),i);
		PrintList(list);
		printf("\n");
	}

	printf("Delete list\n");
	DeleteList(list);

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
