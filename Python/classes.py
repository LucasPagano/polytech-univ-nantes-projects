liste=[1,2,2]
y=[4,5]

def insert(tableau,element,position):
    tableau.append(element)
    for i in range(position-1):
        tableau[len(tableau)-i-1], tableau[len(tableau)-i-2] = tableau[len(tableau)-i-2], tableau[len(tableau)-i-1]
    return tableau

print(insert(liste,5,2))

def concat(tab1,tab2):
    for i in tab2:
        tab1.append(i)
    return tab1

print(concat(liste,y))

def recherche(tableau,valeur):
    for i in range(len(tableau)):
        if tableau[i]==valeur:
            return i

print(liste.index(2))
