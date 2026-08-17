#PAGANO Lucas
#INFO3 G1

#EXERCICE 1, listes chaînées

from random import randint

class Liste:
    '''Classe liste, pointant sur le premier maillon'''
    def __init__(self,tete):
        self.tete=tete

    def __str__(self):
        '''Permet d'afficher la liste pour des tests plus clairs'''
        actuel=self.tete
        string="["+str(actuel.valeur)
        while actuel.suivant != None:
            actuel=actuel.suivant
            string+=","+str(actuel.valeur)
        string=string+"]"
        return string

    #Q1.3
    def len1(self,min,max):
        compteur=0 # un compteur du nombre d'éléments entre min et max
        actuel=self.tete
        while actuel.suivant!=None:
            if min<actuel.valeur and actuel.valeur<max:
                compteur+=1
            actuel=actuel.suivant
        if min<actuel.valeur and actuel.valeur<max: #le dernier nombre doit être pris en compte
            compteur+=1
        return compteur

     #Q1.4
    def append1(self,x):
        '''Ajoute un élément de valeur x à la fin de la liste'''
        actuel=self.tete #le maillon actuel
        while actuel.suivant != None: # on tombe sur le dernier maillon
            actuel=actuel.suivant
        m=Maillon(x,None)
        actuel.suivant=m
        
    #Q1.5
    def remove1(self,x):
        '''Retire l'élément de valeur x de la liste'''
        actuel=self.tete
        trouve = False # Booléen indiquant si on a trouvé la valeur
        if self.tete.valeur==x:#Si la valeur est à la tête de la liste
            self.tete=self.tete.suivant
            trouve = True
        while actuel.suivant!=None and trouve==False:
            if actuel.suivant.valeur!=x:
                actuel=actuel.suivant
            else:
                actuel.suivant=actuel.suivant.suivant
                trouve = True
        if trouve == False:
            return 'Element pas dans la liste'
        else:
            return print(self)
        
class Maillon:
    '''Classe maillon, composée de sa valeur et de son suivant
    dans la liste'''
    def __init__(self,valeur=None,suivant=None):
        self.valeur=valeur
        self.suivant=suivant

def creerListe(n): # n le nombre d'éléments
    if n==0:
        m=Maillon("",None)
        l=Liste(m)
    else:
        ''' quand i = 1 on crée la liste, puis on fait à
chaque maillon le lien entre eux'''
        for i in range(n):
            alea=randint(2,100)
            while alea%2 != 0: # On recalcule un nombre aléatoire à chaque tour
                alea=randint(2,100) # on s'assure qu'il est pair
            if i==0:
                m=Maillon(alea)
                l=Liste(m)
                actuel= l.tete # Maillon actuel
            else:
                m=Maillon(alea)
                actuel.suivant=m
                actuel=actuel.suivant
    return l        
            

#Exercice 2 Tableau de données

notes=[['André',9,13,16,8],['Brigitte',7,16,16,7],['Charles',8,5,15,9]]

#Q2.1
def verifNbLig(tab):
    nbElements = 0 #On calcule d'abord le nombre d'éléments de la première liste
    for i in range(len(tab[0])):
        nbElements +=1
        test=False #test servant à signifier la fin d'une liste du tableau
    verif = nbElements #On suppose avoir le même nombre d'éléments
    for i in range(1,len(tab)): # On ne teste logiquement pas la première liste
        while verif == nbElements and test==False:
            verif=0
            for j in tab[i]:
                verif+=1
            test=True
            '''Le test devient vrai dès qu'on a parcouru la deuxième obucle pour que le while ne soit pas infini'''
    if verif == nbElements:
        return verif
    else:
        print(None)
            
            
#Q2.2

def virerLigne(tab,ind):
    nvtab=[[]] # on crée un nouveau tableau
    if ind<0 or ind>len(tab):
        print("L'indice est incorrect")
        return tab
    else:  
        for i in range(len(tab)):
            if i!=ind:
                
                for j in range(len(tab[i])):
                    nvtab[i].append(tab[i][j])#on recopie les valeurs
                nvtab.append([]) #on rajoute une ligne s'il y a lieu
    return nvtab    


    
