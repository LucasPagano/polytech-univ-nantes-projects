from random import randint

def tribulles():
    liste = [randint(0,100) for i in range(21)]
    for i in range (len(liste)):
        for j in range(len(liste)-i-1):
            if (liste[j]>liste[j+1]):
                liste[j], liste[j+1] = liste[j+1], liste[j]
    return(liste)
