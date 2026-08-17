# COMPTER LES LETTRES #

# EXO 1 #

# Si le caractère est une letter minuscule son code ascii sera entre celui
# de a et celui de z
def lettreMinuscule(lettre):
    if ord(lettre)<=ord("z") and ord(lettre) >= ord("a"):
        test=True
    else:
        test=False
    return test

print(lettreMinuscule("a"))

# EXO 2 #

texte = ["aaa","bbb","zzz"]

# On décompose la liste en mots puis on regarde chaque lettre de chaque mot

def compteLettres(texte):
    tab = [0 for i in range(0,26)]
    for i in texte:
        mot = i
        for j in mot:
            lettre = str(j)
            tab[ord(lettre)-97] += 1 # car le code ascii de a est  97
    return tab
            

print(compteLettres(texte))

