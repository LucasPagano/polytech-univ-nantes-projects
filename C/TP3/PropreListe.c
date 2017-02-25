#include <stdlib.h>
#include <stdio.h>
#include "Liste.h"

#define TAILLE_BLOCS 5

struct SCell
{
    Data value;
    SCell *previous;
    SCell *next;
};

struct SList
{
    SCell *head;
    SCell *array; // Tableau de SCell
    int indexFreeCell; // Indice de la case libre dans le tableau actuel

    SCell **freed; // Tableau de pointeurs vers des cellules libérées, pouvant être réutilisées
    int numberFreed;
};

SList* CreateList(){
    SList *list;
    list = malloc(sizeof(SList));
    list->head = NULL;
    list->indexFreeCell = 0;
    list->array = malloc(sizeof(SCell) * TAILLE_BLOCS);

    list->freed = malloc(sizeof(SCell*) * TAILLE_BLOCS);
    list->numberFreed = 0;
    return list;
}

// Fonciton qui retourne la prochaine cellule libre, en créant un tableau si besoin
SCell* getFirstFree(SList *list){
  if (list->numberFreed > 0){ // On vérifie d'abord s'il n'y a pas de cellule libérée à utiliser
    list->numberFreed -= 1;
    SCell *reused = list->freed[(list->numberFreed)];
    return reused;
  } else if (list->indexFreeCell > TAILLE_BLOCS-1){
      list->array = malloc(sizeof(SCell) * TAILLE_BLOCS);
      list->indexFreeCell = 0;
  }
  return list->array + list->indexFreeCell; // On retourne le pointeur de la cellule libre
}

// TODO Comprendre pourquoi ça foire ici
void DeleteList(SList *list)
{
    if (list->head !=  NULL)
    {
        SCell *tmp = list->head;
        SCell *delete;
        while (tmp->next != NULL) {
            printf("%i\n", tmp->value);
            delete = tmp;
            tmp = tmp->next;
            free(delete);
            printf("%i\n", tmp->value);

        }
    }
}

SCell* AddElementBegin(SList *list, Data elem)
{
    SCell* newCell = getFirstFree(list);
    list->indexFreeCell += 1;
    newCell->value = elem;
    newCell->next = list->head;

    if (list->head != NULL)
        list->head->previous = newCell;

    list->head = newCell;


    return newCell;
}

SCell* AddElementEnd(SList *list,Data elem)
{
    if (list->head != NULL)
    {
        return AddElementAfter(list, GetLastElement(list), elem);
    } else {
        return AddElementBegin(list, elem);
    }
}

SCell* AddElementAfter(SList *list,SCell *cell,Data elem)
{
    SCell* newCell = getFirstFree(list);
    list->indexFreeCell += 1;
    newCell->value = elem;

    if(cell->next != NULL){
        newCell->next = cell->next;
        cell->next->previous = newCell;
    }

    newCell->previous = cell;
    cell->next = newCell;
    return newCell;

}

void DeleteCell(SList *list, SCell *cell){
    if (cell == list->head)
        list->head = cell->next;
    else
        cell->previous->next = cell->next;
    if (cell->next != NULL)
        cell->next->previous = cell->previous;

    cell->next = NULL;
    cell->previous = NULL;
    list->freed[list->numberFreed] = cell;
    list->numberFreed += 1;
}

SCell* GetLastElement(SList *list) {
    SCell *tmp = list->head;
    while (tmp->next != NULL) {
        tmp = tmp->next;
    }

    return tmp;
}

SCell* GetPrevElement(SCell *cell){
    return cell->previous;
}

SCell* GetFirstElement(SList *list){
    return list->head;
}

SCell* GetNextElement(SCell *cell){
    return cell->next;
}

Data GetData(SCell *cell){
    return cell->value;
}
