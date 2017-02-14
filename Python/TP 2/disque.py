from numpy import pi

r = float(input("Rayon du disque ? "))
def perimetre(r):
    return(2*pi*r)

def surface(r):
    return(pi*r**2)

print(perimetre(r))
print(surface(r))
