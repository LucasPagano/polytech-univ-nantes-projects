def palind():
    mot=input("Saisir un mot ")
    milieu=len(mot)//2
    palindro=True
    while (i<len(mot) and palindro):
        if (mot[i]!=mot[len(mot)-1-i]):
            palindro=False
        i+=1
    return(palindro)
        
        
    
