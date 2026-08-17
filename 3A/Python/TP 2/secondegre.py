def secondegre(a,b,c):
    det = b**2 - 4*a*c
    if (det==0):
        return (-b/2*a)
    elif (det > 0):
        return(str((-b + det**0.5)/(2*a)) + " " + str((-b - det**0.5)/(2*a)))
    else:
        return("Resultat en complexes")

