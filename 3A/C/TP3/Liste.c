#include <stdlib.h>
#include "Liste.h"
#define TAILLE_TABLEAU 1
#define NOMBRE_BACKUP 1 //TODO : trouver pourquoi 1 fonctionne pas

// Fonctions privées
SCell* getFirstFree(SList *list);


struct SCell
{
    Data value;
    SCell *previous;
    SCell *next;
};

struct SList
{
    SCell *head;
    int indexFreeCell;
    SCell *array;
    SCell **backup;
    int indexLastBackup;
    int nbBackup;
};

SList* CreateList(){
    SList *list;
    list = malloc(sizeof(SList));
    list->head = NULL;

    list->indexFreeCell = 0;
    list->indexLastBackup = 0;

    list->array = malloc(sizeof(SCell)*TAILLE_TABLEAU); //Tableau de SCell

    list->backup = malloc(sizeof(SCell *)*NOMBRE_BACKUP); // Tableau possédant les pointeurs des tableaux de SCell
    list->backup[list->indexLastBackup] = list->array; // Le premier pointeur est le premier tableau initialisé

    list->nbBackup = NOMBRE_BACKUP;
    return list;
}

SCell* getFirstFree(SList *list){
  if (list->indexFreeCell >= TAILLE_TABLEAU-1){
    if(list->indexLastBackup >= list->nbBackup){
      list->backup = realloc(list->backup, sizeof(SCell) * NOMBRE_BACKUP);
      list->nbBackup += NOMBRE_BACKUP;
    }
    list->array = malloc(sizeof(SCell)*TAILLE_TABLEAU);
    list->indexLastBackup += 1;
    list->backup[list->indexLastBackup] = list->array;
    list->indexFreeCell = 0;
  }
  return list->array + list->indexFreeCell;
}

void DeleteList(SList *list)
{
    if (list->head !=  NULL)
    {
        SCell *tmp = list->head;
        SCell *delete;
        while (tmp->next != NULL) {
            delete = tmp;
            tmp = tmp->next;
            free(delete);
        }
    }
}

SCell* AddElementBegin(SList *list, Data elem)
{
    SCell *newCell = getFirstFree(list);
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
    SCell *newCell = getFirstFree(list);
    list->indexFreeCell += 1;
    newCell->value = elem;

    if(cell->next != NULL){
        newCell->next = cell->next;
        cell->next->previous = newCell;
    } else {
        newCell->next = NULL;
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
