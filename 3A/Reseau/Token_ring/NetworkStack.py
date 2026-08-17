# -*- coding: utf-8 -*-
import threading
import LayerPhy
from Tools.DebugOut import DebugOut
import time

class NetworkStack(object):

    def __init__(self, masterHost='127.0.0.1', baseport=10000, ownIdentifier='x', autoEnter=True):
        self.__debugOut=DebugOut()
        self.__applicationList=[]
        self.__sendDelay=0
        self.__layerDelay=0
        self.__layerPhy=LayerPhy.LayerPhy(ownIdentifier, upperLayerCallbackFunction=self.layer2_incomingPDU, masterHost=masterHost, baseport=baseport, autoEnter=autoEnter)
        # You may want to change the following part
        self.__ownIdentifier=ownIdentifier
        self.outgoingPacketStack=[]
        self.outgoingPacketStackLock=threading.Lock()
        self.maxMessages = 3 # L'indice du dernier message par paquet
        self.nbMessages = 0 # Le nombre actuel de messages traité dans le paquet
        self.paquet = bytearray([]) # Le paquet
        self.aEcrit =  False # Permet que de n'écrire qu'un message par ordinateur par paquet
        self.numberOfNodesPerRing = 4
        self.enTraitement = False # Détermine si on traite le paquet
        

    # On ne leave le réseau que si on ne travaille pas sur le paquet
    def leaveNetwork(self):
        while self.enTraitement:
            time.sleep(5)
        self.__layerPhy.API_leave()
        
    def enableGlobalDebug(self):
        self.__layerPhy.API_subscribeDebug()
        
    def configureDelay(self,sendDelay=None,layerDelay=None):
        if sendDelay!=None:
            self.__sendDelay=sendDelay
        if layerDelay!=None:
            self.__layerDelay=layerDelay

    # Do not change!
    # This is the application layer protocol part: Each application has its specific port
    # The application registers a callback function that is called when a packet arrives for that particular application
    def applicationAddCallback(self, applicationPort, callBack):
        self.__applicationList.append((applicationPort, callBack))

    # Do not change!
    # The application sends packets which are stored in a buffer before being submitted
    def applicationSend(self, destination, applicationPort, pdu):
        self.outgoingPacketStackLock.acquire()
        self.outgoingPacketStack.insert(0,(destination, applicationPort,pdu))
        self.outgoingPacketStackLock.release()


#############################################################################################################################################
#############################################################################################################################################

    def initiateToken(self):
        self.__debugOut.debugOutLayer(self.__ownIdentifier,2,self.__debugOut.INFO,"Initiating TOKEN" )
        tokenThread=threading.Thread(target=self.multipleToken())
        tokenThread.start()

    # On utilise ceci pour remplir le paquet à l'initialisation
    def multipleToken(self):
        for i in range(0,self.maxMessages):
            self.application_layer_outgoingPDU(True)

    def application_layer_incomingPDU(self, applicationPort, source, pdu):
        time.sleep(self.__layerDelay)
        self.__debugOut.debugOutLayer(self.__ownIdentifier,5,self.__debugOut.INFO,"%s: application_layer_in: received (%s) " % (self.__ownIdentifier,pdu))
        if pdu!=None:
            sdu = pdu
            print("Le message est " + str(sdu.decode("utf-8")))

            # We deliver the SDU to the application that handles this message
            for (thisApplicationPort, thisApplication) in self.__applicationList:
                if thisApplicationPort==applicationPort:
                    thisApplication(source, applicationPort, sdu.decode('UTF-8'))

        
        # On ne replonge pas dans le réseau car on arrive ici seulement pour transmettre le message à l'application
        # Ainsi, le paquet est déjà renvoyé en tant qu'accusé de réception dans la couche 3
        # self.application_layer_outgoingPDU(forceToken)

    def application_layer_outgoingPDU(self, forceToken=False):
        time.sleep(self.__layerDelay)
        self.outgoingPacketStackLock.acquire()
        # Si on a déjà écrit, on envoie un Token, pour laisser écrire les autres et ne pas surcharger le paquet
        if len(self.outgoingPacketStack)==0 or forceToken or self.aEcrit:
            destination=""
            applicationPort = 20
            plein = 0
            sdu = "TOKEN"
        else:
            destination,applicationPort,sdu=self.outgoingPacketStack.pop()
            plein = 1
            self.aEcrit = True
        self.outgoingPacketStackLock.release()
        pdu=sdu.encode("UTF-8")
        self.__debugOut.debugOutLayer(self.__ownIdentifier,5,self.__debugOut.INFO,"%s: application_layer_out: sending (%s) " % (self.__ownIdentifier,pdu))
        self.layer4_outgoingPDU(applicationPort, destination, pdu, plein)

        
    # Please adapt!
    # Take care: The parameters of incoming (data packets arriving at the computer) and outgoing (data packets leaving from the computer)
    # should generally agree with one layer difference (i.e. here we treat the applicationPort, an identifier that knows which application
    # is asked to handle the traffic
    def layer4_incomingPDU(self, source, pdu):
        time.sleep(self.__layerDelay)
        applicationPort = int.from_bytes(pdu[0:1], byteorder="little", signed=False)
        sdu = pdu[1:]
        self.__debugOut.debugOutLayer(self.__ownIdentifier,4,self.__debugOut.INFO,"%s: Layer4_in: Received (%s) from %s " % (self.__ownIdentifier,pdu, source))
        self.application_layer_incomingPDU(applicationPort, source, sdu)

    # Please adapt
    def layer4_outgoingPDU(self, applicationPort, destination, pdu, plein):
        time.sleep(self.__layerDelay)
        pdu = applicationPort.to_bytes(1, byteorder="little", signed=False) + pdu
        self.__debugOut.debugOutLayer(self.__ownIdentifier,4,self.__debugOut.INFO,"%s: Layer4_out: Sending (%s) to %s " % (self.__ownIdentifier, pdu, destination))
        # Nous sommes le destinataire du message, on envoie notre ID en source
        self.layer3_outgoingPDU(self.__ownIdentifier, destination, 0, pdu, 0, plein)

   #Couche qui détermine l'emetteur, le destinataire, si le paquet est un accusé de réception et le timeout
    def layer3_incomingPDU(self, interface, pdu):
        time.sleep(self.__layerDelay)


        # On montre bien qu'à chaque fois on tronque le paquet
        source = pdu[0:1].decode('utf-8')
        pdu = pdu[1:]
        destination = pdu[0:1].decode('utf-8')
        pdu = pdu[1:]
        ack = int.from_bytes(pdu[0:1], byteorder="little",signed=False)
        pdu = pdu[1:]
        # Le timeout, gère si un pc se déconnecte du réseau en nombre de sauts
        timeToLive = int.from_bytes(pdu[0:1], byteorder="little",signed=False)
        sdu = pdu[1:]

        self.__debugOut.debugOutLayer(self.__ownIdentifier,3,self.__debugOut.INFO,"%s: Layer3_in: Received (%s) on interface %d " % (self.__ownIdentifier, pdu, interface))

        # Je suis le destinataire du message
        if destination == self.__ownIdentifier:
            self.__debugOut.debugOutLayer(self.__ownIdentifier, 3, self.__debugOut.INFO,
                                          "%s: Layer3_in: Packet meant for me(%s) -> Layer4_in\n" % (
                                              self.__ownIdentifier, sdu))
            # Ce n'est pas un accusé de reception
            if ack == 0:
                self.layer4_incomingPDU(source, sdu)
                # ack = 1, timetolive = 0, plein = 1
                self.layer3_outgoingPDU(destination, source, 1, sdu, 0, 1)
            # C'est un accusé de réception, on force un token pour être gentil
            else:
                    self.application_layer_outgoingPDU(True)

        # Je ne suis pas le destinataire du message
        else:
            if timeToLive < self.numberOfNodesPerRing and self.__ownIdentifier != source:
                timeToLive += 1
                # On le renvoie tel quel et il est toujours plein
                self.__debugOut.debugOutLayer(self.__ownIdentifier,3,self.__debugOut.INFO,"%s: Layer3_in: Packet not meant for me (%s) -> Layer3_out\n" % (self.__ownIdentifier, pdu))
                self.layer3_outgoingPDU(source, destination, ack,  sdu, timeToLive, 1)
            else:
                # La durée de vie du message a expiré, on réécrit dedans
                self.__debugOut.debugOutLayer(self.__ownIdentifier,3,self.__debugOut.INFO,"%s: Layer3_in: Packet timeout (%s) -> Layer3_out\n" % (self.__ownIdentifier, pdu))
                self.application_layer_outgoingPDU(False)


    def layer3_outgoingPDU(self, source, destination, ack, pdu, timeToLive, plein):
        time.sleep(self.__layerDelay)

        # Ajout de l'émetteur du destinataire, de l'ack et du temps à vivre
        pdu = source.encode('utf-8') + \
              destination.encode('utf-8') +\
              ack.to_bytes(1, byteorder="little", signed=False) + \
              timeToLive.to_bytes(1, byteorder="little", signed=False) + \
              pdu

        self.__debugOut.debugOutLayer(self.__ownIdentifier,3,self.__debugOut.INFO,"%s: Layer3_out: Sending out (%s) via interface %d " % (self.__ownIdentifier, pdu, 0))
        self.layer2bis_outgoingPDU(0, pdu, plein)

    # Traite si le paquet est plein
    def layer2bis_incomingPDU(self, interface, pdu):
        time.sleep(self.__layerDelay)
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                      "%s: Layer2_in: Received (%s) on Interface %d " % (
                                      self.__ownIdentifier, pdu, interface))

        jeton = int.from_bytes(pdu[0:1], byteorder="little", signed=False)
        pdu = pdu[1:]

        # Le paquet est un jeton
        if jeton == 0:
            self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                          "%s: Layer2_in: Le paquet est un jeton (%s) -> app_layer_out\n" % (
                                          self.__ownIdentifier, pdu))
            self.application_layer_outgoingPDU(False)
        # Le paquet n'est pas un jeton
        else:
            self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                          "%s: Layer2_in: Le paquet n'est pas un jeton (%s) -> layer3_in\n" % (
                                          self.__ownIdentifier, pdu))
            self.layer3_incomingPDU(interface, pdu)

    def layer2bis_outgoingPDU(self, interface, pdu, plein):
        time.sleep(self.__layerDelay)

        # Si le plein est à 1, on met le header à 1, signifiant que le paquet est plein.
        if plein == 1:
            pdu = plein.to_bytes(1, byteorder="little", signed=False) + pdu
        # Sinon, on le met à 0 : le paquet est vide
        else:
            pdu = plein.to_bytes(1, byteorder="little", signed=False) + pdu

        self.__debugOut.debugOutLayer(self.__ownIdentifier,2,self.__debugOut.INFO,"%s: Layer2bis_out: Sending out (%s) via interface %d " % (self.__ownIdentifier, pdu, 0))

        self.layer2_outgoingPDU(interface, pdu)



    # Traite le multiplexage
    def layer2_incomingPDU(self, interface, pdu):
        self.enTraitement = True
        for i in range(0,self.maxMessages):
            # On decapsule la taille du paquet
            size = int.from_bytes(pdu[0:2], byteorder="little", signed=False)
            pdu = pdu[2:]
            # Un des "fragments" du paquet
            pduInc = pdu[:size]

            pdu = pdu[size:]
            self.layer2bis_incomingPDU(interface, pduInc)

    def layer2_outgoingPDU(self, interface, pdu):

        size = len(pdu)
        pdu = size.to_bytes(2, byteorder="little", signed=False) + pdu
        self.paquet += pdu
        self.nbMessages += 1

        # Si on a traité tous les messages
        if self.nbMessages == self.maxMessages:
            if self.__sendDelay != 0:
                self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                              "%s: Layer2_out: Sleeping for %ds" % (self.__ownIdentifier, self.__sendDelay))
                time.sleep(self.__sendDelay)
            self.__layerPhy.API_sendData(interface, self.paquet)
            # On remet a 0 le paquet, le nombre de messages traités et on considère ne plus avoir écrit
            self.paquet=bytearray([])
            self.aEcrit = False
            self.nbMessages = 0
            self.enTraitement = False


