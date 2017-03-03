#include <stdlib.h>
#include <stdio.h>
#include "Liste.h"

#define TAILLE_BLOCS 2
#define NOMBRE_BACKUP 2

// Prototypes
void backupArray(SList *list);
void backupFreed(SList *list);
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

    SCell *array; // Tableau de SCell
    int indexFreeCell; // Indice de la case libre dans le tableau actuel
    int indexLastBackupBlocks;
    SCell **backupBlocks;
    int nbBackup;

    SCell **freed; // Tableau de pointeurs vers des cellules libérées, pouvant être réutilisées
    int indexFreed;
    int indexLastBackupFreed;
    SCell ***backupFreed;
    int nbFreed;
};

void backupArray(SList *list){
  if (list->indexFreeCell >= TAILLE_BLOCS-1){
    if(list->indexLastBackupBlocks >= list->nbBackup-1){
      list->nbBackup += NOMBRE_BACKUP;
      // On multiplie par list->nbBAckup pour avoir une nouvelle taille additionnée
      list->backupBlocks = realloc(list->backupBlocks, sizeof(SCell*) * list->nbBackup);
    }
    list->array = malloc(sizeof(SCell)*TAILLE_BLOCS);
    list->indexLastBackupBlocks += 1;
    list->backupBlocks[list->indexLastBackupBlocks] = list->array;
    list->indexFreeCell = 0;
  }
}

void backupFreed(SList *list){
  if (list->indexFreed >= TAILLE_BLOCS-1){
    if(list->indexLastBackupFreed >= list->nbFreed-1){
      list->nbFreed += NOMBRE_BACKUP;
      // On multiplie par list->nbBAckup pour avoir une nouvelle taille additionnée
      list->backupFreed = realloc(list->backupFreed, sizeof(SCell**) * list->nbFreed);
    }
    list->freed = malloc(sizeof(SCell*)*TAILLE_BLOCS);
    list->indexLastBackupFreed += 1;
    list->backupFreed[list->indexLastBackupFreed] = list->freed;
    list->indexFreed = 0;
  }
}

SList* CreateList(){
    SList *list;
    list = malloc(sizeof(SList));
    list->head = NULL;
    list->indexFreeCell = 0;
    list->array = malloc(sizeof(SCell) * TAILLE_BLOCS);

    list->indexLastBackupBlocks = 0;
    list->backupBlocks = malloc(sizeof(SCell*) * NOMBRE_BACKUP);
    list->nbBackup = NOMBRE_BACKUP; // Nombre max de backup de blocks

    list->freed = malloc(sizeof(SCell*) * TAILLE_BLOCS);
    list->indexFreed = 0;

    list->indexLastBackupFreed = 0;
    list->backupFreed = malloc(sizeof(SCell**) * NOMBRE_BACKUP);
    list->nbFreed = NOMBRE_BACKUP;// Nombre max de backup de freeds
    return list;
}

// Fonciton qui retourne la prochaine cellule libre, en créant un tableau si besoin
SCell* getFirstFree(SList *list){
  backupArray(list);
  if (list->indexFreed > 0){ // On vérifie d'abord s'il y a une cellule libérée à utiliser
    list->indexFreed -= 1;
    printf("On réaffecte la cell qui avait la valeur %i\n", list->freed[list->indexFreed]->value);
    return list->freed[list->indexFreed];
  } else if (list->indexFreeCell > TAILLE_BLOCS-1){
      list->array = malloc(sizeof(SCell) * TAILLE_BLOCS);
      list->indexFreeCell = 0;
  }
  return list->array + list->indexFreeCell; // On retourne le pointeur de la cellule libre
}

// TODO Comprendre pourquoi ça foire ici
void DeleteList(SList *list){
// On ne doit pas faire avec DeleteCell car on ne veut pas stocker dans list->freed
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

  printf("On a free une cell de valeur %i\n", cell->value);
  backupFreed(list);
  printf("%i\n", list->indexFreed);
  list->freed[list->indexFreed] = cell;
  list->indexFreed += 1;
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
