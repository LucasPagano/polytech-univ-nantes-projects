#include <stdlib.h>
#include <stdio.h>
#include "AncienListe.h"

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

SList* AncienCreateList(){
    SList *list;
    list = malloc(sizeof(SList));
    list->head = NULL;
    return list;
}

// Création d'une cellule avec initialisation de ses pointeurs à NULL
SCell* AncienCreateCell(){
    SCell *cell;
    cell = malloc(sizeof(SCell));
    cell->next = NULL;
    cell->previous = NULL;
    cell->value = 0;

    return cell;
}

void AncienDeleteList(SList *list){
    while (list->head !=  NULL){
        AncienDeleteCell(list, list->head);
    }
    free(list);
}

void AncienDeleteCell(SList *list, SCell *cell){
    if (cell){
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
}

SCell* AncienAddElementBegin(SList *list, Data elem){
    SCell *newCell = AncienCreateCell();
    newCell->value = elem;
    newCell->next = list->head;

    if (list->head != NULL)
        list->head->previous = newCell;

    list->head = newCell;


    return newCell;
}

SCell* AncienAddElementEnd(SList *list,Data elem){
    if (list->head != NULL)
    {
        return AncienAddElementAfter(list, AncienGetLastElement(list), elem);
    } else {
        return AncienAddElementBegin(list, elem);
    }
}

SCell* AncienAddElementAfter(SList *list,SCell *cell,Data elem){
    SCell *newCell = AncienCreateCell();

    newCell->value = elem;
    newCell->value = elem;

    if (cell != NULL){
        if(cell->next != NULL){
            newCell->next = cell->next;
            cell->next->previous = newCell;

        } else{
            newCell->next = NULL;
        }
        newCell->previous = cell;
        cell->next = newCell;
    } else{
        list->head = newCell;
        newCell->next = NULL;
    }
    return newCell;

}

SCell* AncienGetLastElement(SList *list){
    SCell *tmp = list->head;
    while (tmp->next != NULL) {
        tmp = tmp->next;
    }

    return tmp;
}

SCell* AncienGetPrevElement(SCell *cell){
    return cell->previous;
}

SCell* AncienGetFirstElement(SList *list){
    return list->head;
}

SCell* AncienGetNextElement(SCell *cell){
    return cell->next;
}

Data AncienGetData(SCell *cell){
    return cell->value;
}
