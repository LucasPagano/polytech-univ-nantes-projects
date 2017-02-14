import pickle
from utiliserPickle import Point
nbpoints=5


fichiersource = open('listepoints','rb')
listdest=[]
for i in range(5):
    test=pickle.load(fichiersource)
    listdest.append(test)
    print(listdest[i].x)
