def pile(chaine): #Séparer par des espaces nombres et opérateurs
    pile=[]
    nbr=""
    for i in range(len(chaine)-1,-1,-1):
        if chaine[i]>='0'and chaine[i]<='9':
            nbr=chaine[i]+nbr
        elif chaine[i]==' ' and nbr != "":
            pile.append(int(nbr))
            nbr=""
        elif chaine[i]=='+':
            a=pile.pop()
            b=pile.pop()
            pile.append(a+b)
        elif chaine[i]=='-':
            a=pile.pop()
            b=pile.pop()
            pile.append(a-b)
        elif chaine[i]=='*':
            a=pile.pop()
            b=pile.pop()
            pile.append(a*b)
        elif chaine[i]=='/':
            a=pile.pop()
            b=pile.pop()
            pile.append(a/b)
    return pile.pop()
        
        

    '''
    def joli(self, compteur):#travail en cours
        inter=0#compteur intermediaire
        stringLeft = self.left.joli(compteur)
        stringRight = self.right.joli(compteur)

        string=""
        for i in range(int(len(stringLeft)/2)):
            string+="_"
        string+="["+str(self.value)+"]"
        for i in range(int(len(stringRight)/2)):
            string+="_"

        string += "" + stringLeft + "_"
        for i in range(len(str(self.value))):
            string+="_"
        string += "_" + stringRight +"\n"
        

    return string
    '''    
