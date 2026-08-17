from random import randint

liste=[randint(0,100) for i in range(20)]
liste.sort()
print(liste)

def dichotomie2(liste,borneInf,borneSup,n):
    if borneInf > borneSup :
        return "Nombre non trouvé"
    else:
        milieu = (borneSup+borneInf)//2
        if liste[milieu] == n:
            return milieu
        elif liste[milieu]<n :
            return dichotomie2(liste,milieu+1,borneSup,n)
        else:
            return dichotomie2(liste,borneInf,milieu-1,n)
        
def dichotomie(liste):
    n = int(input("Un nombre"))
    borneInf = 0
    borneSup = len(liste)-1
    return dichotomie2(liste,borneInf,borneSup,n)
    
    
    
        

    
