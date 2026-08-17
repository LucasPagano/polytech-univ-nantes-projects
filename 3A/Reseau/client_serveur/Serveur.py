import socket
import threading
import sys

HOTE = '127.0.0.1'
PORT = 7800

def servUDP_simple():
    mySocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    mySocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    try:
        mySocket.bind((HOTE, PORT))
    except socket.error:
        print("Liaison du socket UDP à l'adresse et au port choisi a échoué.")
        sys.exit()

    print("Le serveur UDP écoute sur le port {}".format(PORT))

    while True:  # attente sans fin de datagrammes clients
        # 3) reception datagramme d'un client
        msgClient, adresseClient = mySocket.recvfrom(1024)  # attente datagramme (ordre BLOQUANT)

        print("Message Client reçu : ", msgClient.decode('utf-8'))
        # print( "Reçu de @IP-client %s, port-client %s" % (adresseClient[0], adresseClient[1]) )

        # sortie de la boucle ?
        if msgClient.upper() == b"FIN" or msgClient == b"":
            break  # sortie de boucle

        # 4) serveur renvoie echo au client

        msgServeur = msgClient  # recopie message-client -> message-serveur
        mySocket.sendto(msgServeur, adresseClient)  # envoi datagramme

        # Fin du service
    print("Serveur fermé.")
    mySocket.close()
    sys.exit()