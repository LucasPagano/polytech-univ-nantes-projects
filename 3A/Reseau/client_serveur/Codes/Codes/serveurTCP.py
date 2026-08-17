# -*-coding:utf-8 -*

import sys
sys.path # chemins vers les modules
sys.path.insert(0, "/home/ricordel/Documents/Enseignement/INFO/INFO3/Reseaux1/TP5-sockets/Codes/package")
sys.path

import os
os.getcwd() # répertoire courant
os.chdir("/home/ricordel/Documents/Enseignement/INFO/INFO3/Reseaux1/TP5-sockets/Codes")
os.getcwd()

from package.serveurs import serveurTCP_simple


serveurTCP_simple()

