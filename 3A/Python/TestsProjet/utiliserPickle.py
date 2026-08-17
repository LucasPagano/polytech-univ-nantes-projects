import pickle

class Point:
    def __init__(self,x,y):
        self.x=x
        self.y=y
        
listedepoints=[]
nbpoints=5
for i in range(nbpoints):
    test=Point(i,i+1)
    listedepoints.append(test)

##for i in range(nbpoints):
  ##  print(listedepoints[i].x)


listedepoints[0].x='test'
listedepoints[4].x='test2'


fichiertest = open('listepoints','wb')
for i in range(nbpoints):
    pickle.dump(listedepoints[i],fichiertest)
fichiertest.close()

    


