#include <stdlib.h>
#include "Liste.h"
#include <stdio.h>

#define BLOCK_SIZE 5

typedef struct SBlock SBlock;

//Prototypes
SBlock* createBlock();
SCell* getCell(SList *list);
void addToDeleted(SList *list, SCell *cell);

struct SCell{
    Data value;
    SCell *previous;
    SCell *next;
};

struct SList{
    SCell *head;
    SBlock *currentBlock;
    SCell *deletedCells; // pile de cellules supprimées, deleted est la "head" de ce tableau
};

// Block de pointeurs sur des SCell
struct SBlock{
    SCell scellArray[BLOCK_SIZE];
    SBlock *previous;
    int counter; // Nombre de cellules utilisées
};

SList* CreateList(){
    SList *list = malloc(sizeof(SList));
    list->head = NULL;
    list->currentBlock = createBlock();
    list->currentBlock->previous = NULL;
    list->deletedCells = NULL;
    return list;
}

SBlock* createBlock(){
    SBlock *Block = malloc(sizeof(SBlock));
    Block->counter = 0;
    return Block;
}

// Fonction qui renvoie la première SCell libre, et crée un Block si besoin
SCell* getCell(SList *list){
    if (list->deletedCells != NULL){
        SCell* cell = list->deletedCells;
        printf("On réutilise la cellule qui avait la valeur %d\n", cell->value);
        list->deletedCells = list->deletedCells->next;
        return cell;
    }
    // Si le block est plein, on en crée un nouveau
    else if (list->currentBlock->counter > BLOCK_SIZE - 1){
        SBlock *newBlock = createBlock();
        newBlock->previous = list->currentBlock;
        newBlock->counter = 0;
        list->currentBlock = newBlock;
    }
    // On retourne l'adresse du nième element du block
    return &(list->currentBlock->scellArray[list->currentBlock->counter]);
}

void DeleteList(SList *list){
    SBlock *tempBlock = list->currentBlock;
    SBlock *deleteBlock = tempBlock;
    while (tempBlock->previous !=  NULL){
        deleteBlock = tempBlock;
        tempBlock = tempBlock->previous;
        free(deleteBlock);
    }
    free(tempBlock);
    free(list);
}

void DeleteCell(SList *list, SCell *cell){
    if (cell != NULL){
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

        addToDeleted(list, cell);
    }
}

//Ajoute la cellule à la pile de cellules réutilisables
void addToDeleted(SList *list, SCell *cell){
    if (list->deletedCells != NULL){
        // On ajoute toujours en haut de la pile
        list->deletedCells->previous = cell;
        cell->next = list->deletedCells;
        list->deletedCells = cell;
    } else{
        // On s'assure que la fin de la pile sera NULL
        cell->next = NULL;
        list->deletedCells = cell;
    }
}

SCell* AddElementBegin(SList *list, Data elem){
    SCell *newCell = getCell(list);
    list->currentBlock->counter += 1;

    newCell->value = elem;
    newCell->next = list->head;

    if (list->head != NULL){
        list->head->previous = newCell;
    }

    list->head = newCell;

    return newCell;
}

SCell* AddElementEnd(SList *list,Data elem){
    if (list->head != NULL)
    {
        return AddElementAfter(list, GetLastElement(list), elem);
    } else {
        return AddElementBegin(list, elem);
    }
}

SCell* AddElementAfter(SList *list,SCell *cell,Data elem){
    SCell *newCell = getCell(list);
    list->currentBlock->counter += 1;

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

SCell* GetLastElement(SList *list){
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
