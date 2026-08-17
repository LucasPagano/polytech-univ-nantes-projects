from random import randint

t=[1,5,3,7,9,5,78]

def partitionner(t, premier, dernier, pivot):
    t[pivot], t[dernier] = t[dernier], t[pivot]
    j = premier
    for i in range(premier, dernier):
        if t[i]<=t[dernier]:
            t[i], t[j] = t[j], t[i]
            j = j+1
    t[dernier], t[j] = t[j], t[dernier]
    return j
            


def trirapide(t, premier, dernier):
    if premier<dernier: #premier et dernier sont les indices du tableau, on arrête
        pivot = premier # lorsque le tableau a une taille <= 1
        pivot = partitionner(t, premier, dernier, pivot)
        trirapide(t,premier,pivot-1)
        trirapide(t,pivot+1,dernier)
    return(t)


print(trirapide(t,0,len(t)-1))
