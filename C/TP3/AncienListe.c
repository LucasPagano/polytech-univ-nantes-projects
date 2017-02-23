#include <stdlib.h>
#include <stdio.h>
#include "Liste.h"

struct SCell
{
    Data value;
    SCell *previous;
    SCell *next;
};

struct SList
{
    SCell *head;
};

SList* CreateList(){
    SList *list;
    list = malloc(sizeof(SList));
    list->head = NULL;
    return list;
}

// Création d'une cellule avec initialisation de ses pointeurs à NULL
SCell* CreateCell(){
    SCell *cell;
    cell = malloc(sizeof(SCell));
    cell->next = NULL;
    cell->previous = NULL;
    cell->value = 0;

    return cell;
}

void DeleteList(SList *list)
{
    while (list->head !=  NULL)
    {
        DeleteCell(list, list->head);
    }
    free(list);
}

void DeleteCell(SList *list, SCell *cell){
    // Si la cellule est la tête de liste
    if (cell == list->head){
        // Si la cellule a un suivant, on change la tête de liste
        if (cell->next != NULL){
            list->head = cell->next;
        // Si elle n'a pas de suivant, on met la tête de liste à NULL
        } else {
            list->head = NULL;
        }
    } else
        cell->previous->next = cell->next;

    if (cell->next != NULL)
        cell->next->previous = cell->previous;

    free(cell);
}

SCell* AddElementBegin(SList *list, Data elem)
{
    SCell *newCell = CreateCell();
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
    SCell *newCell = CreateCell();

    newCell->value = elem;

    if(cell->next != NULL){
        newCell->next = cell->next;
        cell->next->previous = newCell;
    }

    newCell->previous = cell;
    cell->next = newCell;
    return newCell;

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
