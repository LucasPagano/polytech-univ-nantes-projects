package Graphe;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


class TestLecture {

    public static void main(String[] args){
	try {
	    File fichier =  new File("/tmp/maville.ser") ;

	   // ouverture d'un flux sur un fichier
	    ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;
		
	    // désérialization de l'objet
	    Map map = new Map();

	    Vertex brest = (Vertex)ois.readObject() ;
	    Vertex rennes = (Vertex)ois.readObject() ;
	    Vertex rouen = (Vertex)ois.readObject() ;
	    Vertex paris = (Vertex)ois.readObject() ;
	    Vertex leMans = (Vertex)ois.readObject() ;
	    Vertex tours = (Vertex)ois.readObject() ;
	    Vertex angers = (Vertex)ois.readObject() ;
	    Vertex poitiers = (Vertex)ois.readObject() ;
	    Vertex nantes = (Vertex)ois.readObject() ;
	    
	    map.add(brest);
		map.add(rennes);
		map.add(rouen);
		map.add(paris);
		map.add(leMans);
		map.add(tours);
		map.add(angers);
		map.add(poitiers);
		map.add(nantes);
	    
	    // Pour vérification 
	    for(Vertex v : map.townList){
	    	System.out.println(v);
		}
	    ois.close();
	}
	catch (Exception e)
	    { System.out.println (e) ; }
		
    }



}
