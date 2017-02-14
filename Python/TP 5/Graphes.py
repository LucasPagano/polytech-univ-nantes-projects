def matOriente(matrice): #non orienté : A voisin B <-> B voisin A
    test=True
    for i in range(len(matrice)):
        for j in range(i,len(matrice[i])):
            if matrice[i][j] != matrice[j][i]:
                test=False
    return not test
    
        
matrice=[[0,1,1,1,0,0,0,0],[1,0,1,0,1,0,0,0],[1,1,0,1,1,0,0,0],[1,0,1,0,0,1,0,0],[0,1,1,0,0,1,1,0],[0,0,0,1,1,0,0,1],[0,0,0,0,1,0,0,1],[0,0,0,0,0,1,1,0]]

dicofig1 = {'oriente':False, 0:[1,2,3],1:[0,2,4],2:[0,1,3,4],3:[0,2,5],4:[2,5,6],5:[3,4,6,7],6:[4,7],7:[5,6]}

class Graphe:
    def __init__(self):     
        self.nbsommets=0
        self.sommets = []
    def addSommet(self,valeur,voisins=[]):
        """Si le sommet est déjà présent, ajoute seulement les voisins"""
        voisinsObjets=[]
        sommet=None
        for i in self.sommets:
            if i.valeur in voisins:
                voisinsObjets.append(i)
                voisins = [value for value in voisins if value != i.valeur] #supprime toutes les occurences de i.valeur
            if i.valeur == valeur:
                sommet = i
        for j in voisins:
            sommet2 = Sommet(j)
            self.sommets.append(sommet2)
            voisinsObjets.append(sommet2)
            self.nbsommets += 1
        if not sommet:
            sommet=Sommet(valeur,voisinsObjets)
            self.sommets.append(sommet)
            self.nbsommets+=1
        else:
            sommet.addvoisins(voisinsObjets)

                
class Sommet:
    """Voisins est une liste"""
    def __init__(self,valeur,voisins=None):
        if not voisins :
            self.voisins=[]
        else:
            self.voisins=voisins
        self.valeur=valeur
    def addvoisins(self,voisins):
        for i in voisins:
            if i not in self.voisins:
                self.voisins.append(i)

    def __repr__(self):
        return 'Sommet numéro ' + str(self.valeur)
    


def listeOrientee(graphe):
    test=True
    for i in range(graphe.nbsommets):
        for j in graphe.sommets[i].voisins:
            if i not in graphe.sommets[j].voisins:
                test=False
    return not test        


def matriceToListe(matrice):
    graphe=Graphe()
    for i in range(len(matrice)):
        for j in range(len(matrice[i])):
            if matrice[i][j]:
                graphe.addSommet(i,[j])
    return graphe

def listeToMatrice(graphe):
    matrice = [[0 for i in range(graphe.nbsommets)] for j in range(graphe.nbsommets)]
    for i in range(graphe.nbsommets):
        for j in graphe.sommets[i].voisins:
            matrice[i][j]=1
    return matrice
    
    



graphe=Graphe()
graphe.addSommet(0,[1,2,3])
graphe.addSommet(1,[0,2,4])
graphe.addSommet(2,[0,1,3,4])
graphe.addSommet(3,[0,2,5])
graphe.addSommet(4,[1,2,5,6])
graphe.addSommet(5,[3,4,7])
graphe.addSommet(6,[4,7])
graphe.addSommet(7,[5,6])

b=matriceToListe(matrice)
a=6+5
