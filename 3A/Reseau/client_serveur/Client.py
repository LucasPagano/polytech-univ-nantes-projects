# -*-coding:utf-8 -*
""" Module contenant les Clients
"""

HOTE = '127.0.0.1'      # @IP interfaces du serveur => ici lo
PORT =  7800            # n° port écoute du serveur

import sys
import socket

#########################

# Definition d'un client UDP pour le serveur d'écho
# Ce client envoie un message au serveur, ce dernier renvoie au client le même message

def clientUDP_simple():
    """ un client UDP (pour le service d'écho) """

    # 1) création du socket du client
    mySocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        # AF_INET : famille d'adresse, ici adresses Internet
        # SOCK_DGRAM : type de socket => mode datagramme (UDP)

    # message du client
    msgClient = input("Message ? ")
    if msgClient == "":     # sortie du programme
        print("Sortie du programme")
        mySocket.close()
        sys.exit()

    while True: # boucle infinie (de messages)

        # 2) envoi message au serveur
        mySocket.sendto( bytes(msgClient, 'utf-8'), ( (HOTE, PORT) ) )

        # 3) réception echo du serveur

        # attente echo (ordre BLOQUANT)
        msgServeur, adresseServeur = mySocket.recvfrom(1024)

        print( "Echo reçu : ", msgServeur.decode('utf-8') )
        #print( "Echo reçu de @IP-serveur %s, port-serveur  %s" % (adresseServeur[0], adresseServeur[1]) )

        # nouveau message du client ?
        msgClient = input("Message ? (sinon taper FIN) ") # message client

        # sortie de la boucle ?
        if msgClient.upper() == "FIN" or msgClient == "":
                break # sortie de boucle ?

    # 4) Fermeture de la connexion :
    print("Fin du programme")
    mySocket.close()
    sys.exit()

# test de la fonction clientUDP_simple()

if __name__ == "__main__":
    clientUDP_simple()

#########################

# Definition d'un client TCP pour le serveur d'écho
# Ce client se connecte au serveur, lui envoie un message puis récupère l'echo

def clientTCP_simple():
    """ un client TCP (pour le service d'écho) """

    # 1) création du socket du client
    mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # AF_INET : famille d'adresse, ici adresses Internet
        # SOCK_STREAM : type de socket (flux de données) => TCP

    # 2) envoi d'une requete de connexion au serveur
    try:
        mySocket.connect( (HOTE, PORT) )
    except socket.error:
        print( "La connexion a échoué." )
        sys.exit()

    print("Connexion établie avec le serveur.")

    # message du client
    msgClient = input("Message ? ")
    if msgClient == "":     # sortie du programme
        print("Sortie du programme")
        mySocket.close()
        sys.exit()

    while 1: # boucle infinie (de messages)

        # 2) envoi message au serveur
        mySocket.send( bytes(msgClient, 'utf-8'))

        # 3) réception echo du serveur

        msgServeur = mySocket.recv(1024) # attente (bloquante) echo du serveur

        print("Echo reçu : ", msgServeur.decode('utf-8') ) # affichage echo du serveur

        # nouveau message du client ?
        msgClient = input("Message ? (sinon taper FIN) ") # message client

        # sortie de la boucle ?
        if msgClient.upper() == "FIN" or msgClient == "":
                break # sortie de boucle ?

    # 4) Fermeture de la connexion :
    print("Fin du programme.")
    mySocket.close()
    sys.exit()

# test de la fonction clientTCP_simple()

if __name__ == "__main__":
        clientTCP_simple()
