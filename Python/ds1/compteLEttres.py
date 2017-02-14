texte = ["aaa","bbb","zzz"]

def compteLettres(texte):
    tab = [0 for i in range(0,26)]
    for i in texte:
        mot = i
        for j in mot:
            lettre = str(j)
            tab[ord(lettre)-97] += 1 # car le code ascii de a est  97
    return tab
            

print(compteLettres(texte))
