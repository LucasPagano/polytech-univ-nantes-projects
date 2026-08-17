
def lettreMinuscule(lettre):
    if ord(lettre)<=ord("z") and ord(lettre) >= ord("a"):
        test=True
    else:
        test=False
    return test

print(lettreMinuscule("a"))
