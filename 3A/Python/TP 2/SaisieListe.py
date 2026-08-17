from math import inf
def verif(nombre):
    if(nombre!=""):
        monTest = True
        indice = 0
        length = len(nombre)
        while(monTest and indice < length):
            monTest = (nombre[indice] >= "0" or nombre[indice] <= "9")
            indice = indice +1
    else:
        monTest = False
    return(monTest)

def inverse(liste):
    for i in range(len(liste)//2):
        liste[i], liste[len(liste)-1-i] = liste[len(liste)-1-i], liste[i]



    
def saisieListe():
    somme=0
    saisie=input("Nombre pour la liste : ")
    liste=[]
    monMax=-inf
    monMin=+inf
    while(saisie!=""):
        if (verif(saisie)):
            liste.append(int(saisie))
            monMax=max(monMax,int(saisie))
            monMin=min(monMin,int(saisie))
            somme+=int(saisie)
        else:
            print("Not a number")
        print("Maximum de la liste est égal à ", monMax, "Minimum de la liste est égal à ", monMin, "La moyenne de la liste est égale à ", somme/len(liste))
        saisie=input("Nombre pour la liste")
    print(liste)
    inverse(liste)
    print(liste)
    
    
