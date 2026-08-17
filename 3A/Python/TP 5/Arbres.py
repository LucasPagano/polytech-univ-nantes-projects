class Node:
    def __init__(self, value=None, left=None, right=None):
        self.value=value
        self.left=left
        self.right=right
    def __str__(self):
        return "[" + str(self.left) + ","+str(self.value) + "," + str(self.right) + "]"
    


    def __srevnelarts__(self):#stralenvers lol
        string = "["
        if self.right != None:
            string += self.right.__srevnelarts__() + ","
        else:
            string+="None,"
        string+=str(self.value) + ","
        if self.left != None:
            string+=self.left.__srevnelarts__()
        else:
            string+="None"
        return string+"]"


    def __array__(self):
        array = []
        if self.left:
            array += self.left.__array__()
        array += [self.value]
        if self.right:
            array += self.right.__array__()
        return array

        
class Tree:
    def __init__(self, node):
        self.root = node
        
    def empty(self):
        return self.root.value == None
    
    def __str__(self):
        return str(self.root)
    def __srevnelarts__(self):
        return self.root.__srevnelarts__()
    
    def __array__(self):#pour remplir la liste à la fin du tri q4
        return self.root.__array__()

    def depth(self):
        depth = 1
        if self.root == None:
            depth = 0
        else:
            u1=Tree(self.root.left)
            u2=Tree(self.root.right)
            depth += max(u1.depth(),u2.depth())
        return depth
    
    def nb_node(self):
        compteur=1
        if self.root != None:
            u1=Tree(self.root.left)
            u2=Tree(self.root.right)
            compteur+=u1.nb_node()+u2.nb_node()
        return compteur

    def sumtree(self):
        somme=0
        if self.root != None:
            if self.value != None:   
                somme += self.root.value
            u1=Tree(self.root.left)
            u2=Tree(self.root.right)
            somme+=u1.sumtree()+u2.sumtree()
        return somme
    
    def inc(self):
        if self.root != None:
            if self.root.value != None:
                self.root.value += 1
            u1=Tree(self.root.left)
            u2=Tree(self.root.right)
            u1.inc()
            u2.inc()
        return self
    
    def hierarchy(self, testValue=None): #Par def b est vrai si pas d'enfant
        b = True
        if self.root != None:
            u1=Tree(self.root.left)
            u2=Tree(self.root.right)
            if testValue != None:
                b = self.root.value <= testValue
            b = b and u1.hierarchy(self.root.value) and u2.hierarchy(self.root.value)
        else:
            raise Exception('Tree is empty')
        return b

    def addValue(self,value): #récursif,branche gauche : inf ou égal
        if self.root != None:
            if self.root.value== None:
                self.root.value = value
            else:
                if value <= self.root.value:
                    if self.root.left == None:
                       self.root.left = Node(value)
                    else:
                        Tree(self.root.left).addValue(value)
                else:
                    if self.root.right == None:
                       self.root.right = Node(value)
                    else:
                        Tree(self.root.right).addValue(value)
        else:
            self.root = Node(value)

    def addValueIt(self,value):#itératif
        actualNode=self.root
        while actualNode.value != None:
            if value <= actualNode.value:
                if actualNode.left == None:
                    actualNode.left = Node()
                actualNode=actualNode.left
            else:
                if actualNode.right == None:
                    actualNode.right = Node()
                actualNode=actualNode.right
        actualNode.value=value


    def search(self,value):#récursif
        found = False
        if self.root != None and self.root.value != None:
            if value > self.root.value:
                found = Tree(self.root.right).search(value)
            elif value < self.root.value:
                found = Tree(self.root.left).search(value)
            else:
                found=True
            
        return found

    def searchIt(self,value):
        actualNode=self.root
        found = False
        fini = False
        while actualNode.value != None and not found and not fini:
            if  value > actualNode.value:
                if actualNode.right != None:
                    actualNode = actualNode.right
                else:
                    fini = True
            elif value < actualNode.value:
                if actualNode.left != None:
                    actualNode = actualNode.left
                else:
                    fini=True                
            else:
                found=True
        return found

    def sortList(self,list):
        for i in list:
            self.addValue(i)
        return self.__array__()
         


class TreePrefixe(Tree):
    def __init__(self,node):
        Tree.__init__(self,node)
    def evaluate(self):
        if self.root:
            if type(self.root.value)==int:
                return self.root.value
            else:
                if self.root.right and self.root.left:
                    u1 = TreePrefixe(self.root.left)
                    u2 = TreePrefixe(self.root.right)
                    if self.root.value == "+":
                        return u1.evaluate() + u2.evaluate()
                    if self.root.value == "-":
                        return u1.evaluate() - u2.evaluate()
                    if self.root.value == "*":
                        return u1.evaluate() * u2.evaluate()
                    if self.root.value == "/":
                        return u1.evaluate() / u2.evaluate()
                    else:
                        print(None)
                else:
                    print(None)
        else:
            print(None)

import re

def evalchaine(string):
    string=string.replace("(","Node(")
    string=string.replace(" ",",")
    string=re.sub(r'([\+\-\*\/])',r'"\1"',string)
    string = re.sub(r'([0-9])',r'Node(\1)',string)

    return TreePrefixe(eval(string)).evaluate()
            
            


method = 'images/:id/huge'
method = re.sub(r'(:[a-z]+)', r'<span>\1</span>', method)

        
def leaf():
    return Node()


def testExcept(tree):
    try:
        return tree.hierarchy()
    except Exception as error:
        print('Erreur :' + repr(error))



l=[1,3,-5,6,8,7]
def sortList(list):
    tree=Tree(None)
    return tree.sortList(list)
        

def NombreNoeudInterne(T): renvoie un entier

    if isempty(T):
        return 0
    elif not(self.left or self.right):
        return 0
    else
        return 1 + NombreNoeudInterne(FilsGauche(T)) +
                NombreNoeudInterne(FilsDroit(T))



g=Tree(Node (1 , Node(0,Node(-1))))
d=Tree(Node(4,Node(3),Node(5)))
h=Tree(Node(2,g.root,d.root))
tree2=Tree(Node())
