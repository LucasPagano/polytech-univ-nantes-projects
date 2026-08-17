# PETITS EXERCICES #

# EXO 1 #

from math import sqrt
tab = [1,2]

def moyenne(tab):
    m=0
    nbvaleurs=0
    ec=0
    for i in tab:
        m=m+i
        nbvaleurs += 1
        ec=ec+i**2
    m = m/nbvaleurs
    #on soustrait n*m² à la fin au lieu de soustraire m² n fois
    #on prend la racine de ec² pour avoir ec
    ec = sqrt((ec-nbvaleurs*m**2)/nbvaleurs )
    return m, ec
    

print(moyenne(tab))

# EXO 2 #

#on appelle récursivement la fonction car m tombera au bout d'un moment à 0

def ack(m,n):
    if n < 0 or m < 0:
        return None
    elif m == 0:
        return n+1
    elif m != 0 and n == 0:
        return ack(m-1,1)
    elif m>0 and n>0:
        return ack(m-1,ack(m,n-1))

print(ack(3,2))
