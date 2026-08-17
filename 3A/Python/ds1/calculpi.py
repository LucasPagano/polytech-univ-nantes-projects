from math import sqrt

def calculpi():
    pi = 2
    num = 2
    denom = sqrt(2)
    terme = num/denom
    while terme > 1+10**(-15):
        pi = pi*terme
        denom=sqrt((denom)+2)
        terme = num/denom
        print(pi)
    return pi

print(calculpi())
