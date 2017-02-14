def calc(string):
    nombre1=""
    nombre2=""
    operavu=False
    operateur=""
    for i in string:
        if (i >= "0") and (i <= "9"):
            if operavu:
                nombre1 = nombre1 + i
            else:
                nombre2=nombre2+i
        else:
            operavu=True
            operateur=i
    if (nombre1=="" or nombre2=="" or operateur==""):
        return("Input a correct operation")
    
    nombre1=int(nombre1)
    nombre2=int(nombre2)

    if (operateur=="+"):
        return(nombre1+nombre2)
    elif(operateur=="-"):
        return(nombre1-nombre2)
    elif(operateur=="/"):
        return(nombre1/nombre2)
    elif(operateur=="*"):
        return(nombre1*nombre2)
    else:
        return("Invalid operator")
