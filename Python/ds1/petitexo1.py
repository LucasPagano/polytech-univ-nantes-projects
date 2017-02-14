tab = [2,2,3]

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
    ec = (ec-nbvaleurs*m**2)/nbvaleurs 
    return m, ec
    

print(moyenne(tab))
