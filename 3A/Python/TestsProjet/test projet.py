import os
import pickle

a='35 et oui'
b=['t']
c=87,8

fichiertest = open('listesatellites','wb')
pickle.dump(a,fichiertest)
pickle.dump(b,fichiertest)
pickle.dump(c,fichiertest)

fichiertest.close()
fichiertest=open('listesatellites','rb')
k = pickle.load(fichiertest)
j = pickle.load(fichiertest)
j=j+[]
print(k)
print(type(k))
print(j)
print(type(j))

class Point:
    def __init__(self,x,y):
        self.x=x
        self.y=y

A=Point(0,0)
B=Point(5,5)

listedepoints=[]
nbpoints=5
for i in range(nbpoints):
    test=Point(i,i+1)
    listedepoints.append(test)

for i in range(nbpoints):
    print(listedepoints[i].x)
    print(listedepoints[i].y)

listedepoints[0].x='test'




