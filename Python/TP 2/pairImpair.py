from random import randint
liste=[randint(0,100) for i in range(0,21)]

def pairImpair(liste):
    i=0
    borneInf = 0
    borneSup = len(liste)-1
    while(i<borneSup):
        if (liste[i]%2 == 0):
            borneInf+=1
            i+=1
        else:
            liste[i], liste[borneSup] = liste[borneSup], liste[i]
            borneSup = borneSup -1
            
    return(liste)
