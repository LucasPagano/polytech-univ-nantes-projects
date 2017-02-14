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
