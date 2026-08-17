def verif(nombre):
    for i in nombre:
        if i not in '0123456789':
            return(False)
    return(True)

test=False
while not test:
    nombre = input("Input a number ")
    test = verif(nombre)
