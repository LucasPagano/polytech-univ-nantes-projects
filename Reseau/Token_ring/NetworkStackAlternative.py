# -*- coding: utf-8 -*-
import threading
import LayerPhy
from Tools.DebugOut import DebugOut
import time


class NetworkStack(object):
    def __init__(self, masterHost='127.0.0.1', baseport=10000, ownIdentifier='x', autoEnter=True):
        self.__debugOut = DebugOut()
        self.__applicationList = []
        self.__sendDelay = 0
        self.__layerDelay = 0
        self.__layerPhy = LayerPhy.LayerPhy(ownIdentifier, upperLayerCallbackFunction=self.layer2_incomingPDU,
                                            masterHost=masterHost, baseport=baseport, autoEnter=autoEnter)
        # You may want to change the following part
        self.__ownIdentifier = ownIdentifier
        self.outgoingPacketStack = []
        self.outgoingPacketStackLock = threading.Lock()

    def leaveNetwork(self):
        self.__layerPhy.API_leave()

    def enableGlobalDebug(self):
        self.__layerPhy.API_subscribeDebug()

    def configureDelay(self, sendDelay=None, layerDelay=None):
        if sendDelay != None:
            self.__sendDelay = sendDelay
        if layerDelay != None:
            self.__layerDelay = layerDelay

    # Do not change!
    # This is the application layer protocol part: Each application has its specific port
    # The application registers a callback function that is called when a packet arrives for that particular application
    def applicationAddCallback(self, applicationPort, callBack):
        self.__applicationList.append((applicationPort, callBack))

    # Do not change!
    # The application sends packets which are stored in a buffer before being submitted
    def applicationSend(self, destination, applicationPort, pdu):
        self.outgoingPacketStackLock.acquire()
        self.outgoingPacketStack.insert(0, (destination, applicationPort, pdu))
        self.outgoingPacketStackLock.release()

    #############################################################################################################################################
    #############################################################################################################################################

    # Please change: This sends the first TOKEN to the ring
    # In fact, sending a TOKEN requires the creation of a new thread
    def initiateToken(self):
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO, "Initiating TOKEN")
        tokenThread = threading.Thread(target=self.application_layer_outgoingPDU, args=(True,))
        tokenThread.start()

    # Please adapt if required : This is the top layer that usually sends the data to the application
    # If pdu is None, the packet is not valid
    # forceToken determines that the return packet needs to be a TOKEN
    def application_layer_incomingPDU(self, forceToken, source, pdu):
        time.sleep(self.__layerDelay)
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 5, self.__debugOut.INFO,
                                      "%s: application_layer_in: received (%s) " % (self.__ownIdentifier, pdu))
        if pdu != None:
            applicationPort = int.from_bytes(pdu[0:1], byteorder="little", signed=False)
            sdu = pdu[1:]
            print("Le message est " + str(sdu.decode("utf-8")))

            # We deliver the SDU to the application that handles this message
            for (thisApplicationPort, thisApplication) in self.__applicationList:
                if thisApplicationPort == applicationPort:
                    thisApplication(source, applicationPort, sdu.decode('UTF-8'))


                    # On ne replonge pas dans le réseau car on arrive ici seulement quand on reçoit un message
                    # Ainsi, le paquet est déjà renvoyé en tant qu'accusé de réception dans la couche 3
                    # self.application_layer_outgoingPDU(forceToken)

    # Please adapt if required: This is the top layer that retrieves one element from the application layer
    def application_layer_outgoingPDU(self, forceToken=False):
        time.sleep(self.__layerDelay)
        self.outgoingPacketStackLock.acquire()
        if len(self.outgoingPacketStack) == 0 or forceToken:
            destination = ""
            applicationPort = 20
            plein = 0
            sdu = "TOKEN"
        else:
            destination, applicationPort, sdu = self.outgoingPacketStack.pop()
            plein = 1
        self.outgoingPacketStackLock.release()
        pdu = applicationPort.to_bytes(1, byteorder="little", signed=False) + sdu.encode("UTF-8")
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 5, self.__debugOut.INFO,
                                      "%s: application_layer_out: sending (%s) " % (self.__ownIdentifier, pdu))
        self.layer4_outgoingPDU(destination, pdu, plein)

    # Please adapt!
    # Take care: The parameters of incoming (data packets arriving at the computer) and outgoing (data packets leaving from the computer)
    # should generally agree with one layer difference (i.e. here we treat the applicationPort, an identifier that knows which application
    # is asked to handle the traffic
    def layer4_incomingPDU(self, source, pdu):
        time.sleep(self.__layerDelay)
        # Let us assume that this is the layer where we determine the applicationPort
        # We also decide whether we can send immediately send a new packet or whether we need to be friendly and send a TOKEN
        # We are not friendly and send a packet if our application has one with 100% chance
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 4, self.__debugOut.INFO,
                                      "%s: Layer4_in: Received (%s) from %s " % (self.__ownIdentifier, pdu, source))
        self.application_layer_incomingPDU(False, source, pdu)

    # Please adapt
    def layer4_outgoingPDU(self, destination, pdu, plein):
        time.sleep(self.__layerDelay)
        # Nous sommes le destinataire du message, on envoie notre ID en source
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 4, self.__debugOut.INFO,
                                      "%s: Layer4_out: Sending (%s) to %s " % (self.__ownIdentifier, pdu, destination))
        self.layer3_outgoingPDU(self.__ownIdentifier, destination, 0, pdu, plein)

        # Couche qui détermine l'emetteur, le destinataire et si le paquet est un accusé de réception

    def layer3_incomingPDU(self, interface, pdu):
        time.sleep(self.__layerDelay)

        # On montre bien qu'à chaque fois on tronque le paquet
        # On utilise [0:1] au lieu de [0] pour bypass une erreur sur decode
        source = pdu[0:1].decode('utf-8')
        pdu = pdu[1:]
        destination = pdu[0:1].decode('utf-8')
        pdu = pdu[1:]
        ack = int.from_bytes(pdu[0:1], byteorder="little", signed=False)
        pdu = pdu[1:]

        self.__debugOut.debugOutLayer(self.__ownIdentifier, 3, self.__debugOut.INFO,
                                      "%s: Layer3_in: Received (%s) on interface %d " % (
                                      self.__ownIdentifier, pdu, interface))

        # Je suis le destinataire du message
        if destination == self.__ownIdentifier:
            self.__debugOut.debugOutLayer(self.__ownIdentifier, 3, self.__debugOut.INFO,
                                          "%s: Layer3_in: Packet meant for me(%s) -> Layer4_in\n" % (
                                              self.__ownIdentifier, pdu))
            # Ce n'est pas un accusé de reception
            if ack == 0:
                self.layer4_incomingPDU(source, pdu)
                self.layer3_outgoingPDU(destination, source, 1, pdu, 1)
            # C'est un accusé de réception, on force un token pour être gentil
            else:
                self.application_layer_outgoingPDU(True)

        # Je ne suis pas le destinataire du message
        else:
            # On le renvoie tel quel et il est toujours plein
            self.__debugOut.debugOutLayer(self.__ownIdentifier, 3, self.__debugOut.INFO,
                                          "%s: Layer3_in: Packet not meant for me (%s) -> Layer3_out\n" % (
                                          self.__ownIdentifier, pdu))
            self.layer3_outgoingPDU(source, destination, ack, pdu, 1)

    # Please adapt
    def layer3_outgoingPDU(self, source, destination, ack, pdu, plein):
        time.sleep(self.__layerDelay)
        # Here, we store the packet and wait until an empty token packet arrives

        # Ajout de l'émetteur et du destinataire
        pdu = source.encode('utf-8') + destination.encode('utf-8') + ack.to_bytes(1, byteorder="little",
                                                                                  signed=False) + pdu

        self.__debugOut.debugOutLayer(self.__ownIdentifier, 3, self.__debugOut.INFO,
                                      "%s: Layer3_out: Sending out (%s) via interface %d " % (
                                      self.__ownIdentifier, pdu, 0))
        self.layer2_outgoingPDU(0, pdu, plein)

    # Please adapt
    def layer2_incomingPDU(self, interface, pdu):
        time.sleep(self.__layerDelay)
        self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                      "%s: Layer2_in: Received (%s) on Interface %d " % (
                                      self.__ownIdentifier, pdu, interface))
        if interface == 0:  # same ring


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
        else:  # Another Ring, this is for routing, see later
            pass

    def layer2_outgoingPDU(self, interface, pdu, plein):
        time.sleep(self.__layerDelay)

        # Si le plein est à 1, on met le header à 1, signifiant que le paquet est plein.
        if plein == 1:
            pdu = plein.to_bytes(1, byteorder="little", signed=False) + pdu
        # Sinon, on le met à 0 : le paquet est vide
        else:
            pdu = plein.to_bytes(1, byteorder="little", signed=False) + pdu

        self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                      "%s: Layer2_out: Sending out (%s) via interface %d " % (
                                      self.__ownIdentifier, pdu, interface))
        if self.__sendDelay != 0:
            self.__debugOut.debugOutLayer(self.__ownIdentifier, 2, self.__debugOut.INFO,
                                          "%s: Layer2_out: Sleeping for %ds" % (self.__ownIdentifier, self.__sendDelay))
            time.sleep(self.__sendDelay)
        self.__layerPhy.API_sendData(interface, pdu)
