#ifndef _ANCIEN_LISTE_H
    #define _ANCIEN_LISTE_H

    typedef int Data;
    typedef struct SCell SCell;
    typedef struct SList SList;

    SList* AncienCreateList();
    void AncienDeleteList(SList *list);

    SCell* AncienAddElementBegin(SList *list,Data elem);
    SCell* AncienAddElementEnd(SList *list,Data elem);
    SCell* AncienAddElementAfter(SList *list,SCell *cell,Data elem);
    void AncienDeleteCell(SList *list,SCell *cell);

    SCell* AncienGetFirstElement(SList *list);
    SCell* AncienGetLastElement(SList *list);
    SCell* AncienGetPrevElement(SCell *cell);
    SCell* AncienGetNextElement(SCell *cell);
    Data AncienGetData(SCell *cell);

#endif
