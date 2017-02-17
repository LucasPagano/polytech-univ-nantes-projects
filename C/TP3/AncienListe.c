//
// Created by E144754R on 15/02/17.
//
#include <stdlib.h>
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
    SCell *cell;
    cell = malloc(sizeof(SCell));
    cell->value = elem;
    cell->next = list->head;

    if (list->head != NULL)
        list->head->previous = cell;

    list->head = cell;

    return cell;
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
    SCell *newCell;
    newCell = malloc(sizeof(SCell));
    newCell->value = elem;

    if(cell->next != NULL){
        newCell->next = cell->next;
        cell->next->previous = newCell;
    } else
        newCell->next = NULL;

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

    free(cell);
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
