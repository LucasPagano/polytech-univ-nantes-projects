
# AUTOUR DE PI #

# CALCUL PI

from math import sqrt

#On voit qu'il faut a chaque tour prendre la racine du dénominateur précedent
# et ajouter 2

def calculpi():
    pi = 2
    num = 2
    denom = sqrt(2)
    terme = num/denom
    while terme > 1+10**(-15):
        pi = pi*terme
        denom=sqrt((denom)+2)
        terme = num/denom
    return pi

print(calculpi())

# POEME DE PI #

#On convertit chaque longueur de mot en string pour pouvoir concaténer
# Finalement on reconvertit en entier

pi=["que","j","aime","à","faire"]
pi2=["Immortel"]

def poemeDePi(liste):
    pi=""
    for i in range(0,len(liste)):
        pi=pi+str(len(liste[i]))
    pi=int(pi)/(10**(len(liste)-1))    
    return pi
print(poemeDePi(pi+pi2))
               
#La valeur renvoyée par poemeDePi(pi+pi2) est correcte
# Car on concatène les deux listes par l'opération "pi+pi2", ce qui revient à
# n'en créer qu'une seule


