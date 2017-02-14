


# Arbres binaires de recherche

class Node:
    """
    Noeud qui possède ou non :
    une valeur, un fils droit et un fils gauche
    """
    def __init__(self, value=None, left=None, right=None):
        self.value=value
        self.left=left
        self.right=right

    def __str__(self): # Sert à faire les tests
        return "[" + str(self.left) + ","+str(self.value) + "," + str(self.right) + "]"

    def printNode(self):
        # Pour afficher d'abord le supérieur, on teste de droite à gauche
        if self.right is not None:  # d'abord branche droite
            return self.right.printNode()
        if self.value is not None:  # ensuite nous-même
            print(str(self.value))
        if self.left is not None:  # enfin branche gauche
            return self.left.printNode()


class Tree:
    def __init__(self, node): # La classe arbre est la racine de l'arbre
        self.root = node

    def __str__(self):  # Sert à faire les tests
        return str(self.root)

    def addValue(self,value):#itératif, on suppose la branche gauche <=
        actualNode = self.root  # Le noeud actuel est la racine
        while actualNode.value != None: # Grâce à la structure on fait un parcours dichotomique
            if value <= actualNode.value:
                if actualNode.left == None:
                    actualNode.left = Node() # On crée un noeud si besoin
                actualNode=actualNode.left  # On va dans ce noeud
            else:
                if actualNode.right == None:
                    actualNode.right = Node() # On crée un noeud si besoin
                actualNode=actualNode.right  # On va dans ce noeud
        actualNode.value = value # On rajoute la valeur au noeud au final

    def occValue(self, value): # On suppose encore que la branche gauche est <=
        actualNode = self.root # Le noeud actuel est mis à la racine
        occ = 0
        ended = False
        while not ended: # Tant qu'on a pas fini
            if value < actualNode.value:
                if actualNode.left != None:
                    actualNode = actualNode.left # On passe sur ce noeud
                else:
                    ended = True # Il n'existe donc pas la valeur dans l'arbre, on termine
            elif value > actualNode.value:
                if actualNode.right != None:
                    actualNode = actualNode.right # On passe sur ce noeud
                else:
                    ended = True # Il n'existe donc pas la valeur dans l'arbre, on termine
            else:
                occ += 1 # On incrémente l'occurence
                if actualNode.left != None:
                    actualNode = actualNode.left # On passe sur ce noeud
                else:
                    ended = True # Il n'existe donc pas la valeur dans l'arbre, on termine
        return occ

    def printTree(self):
        return self.root.printNode() # On renvoie l'affichage du noeud racine


tree2 = Tree(Node())
print(tree2)
tree2.addValue(5)
print(tree2)
print("------")
g = Tree(Node(1, Node(0, Node(-1))))
g.printTree()


# CALCUL DE POLYNÔMES


polynome = [2, -1, 1, -2, 3] # le polynome de l'exemple

def horner(listeCoeff, point):
    calcul = listeCoeff[0] # On initialise au coefficient de la plus grosse puissance
    # À chaque tour, on multiplie par le point d'évaluation, et on ajoute le coeff inférieur
    for i in range(len(listeCoeff)-1):
        calcul *= point
        calcul += listeCoeff[i+1]
    return calcul

print(horner(polynome, 2))
