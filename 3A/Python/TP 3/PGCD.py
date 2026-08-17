def pgcd(a,b):
    dc=a
    if (b!=0):
        dc=pgcd(b,a%b)
    return dc
