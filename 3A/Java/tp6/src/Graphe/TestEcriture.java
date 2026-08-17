package Graphe;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;


class TestEcriture {

	public static void main(String[] args) {
		try {
			File fichier = new File("/tmp/maville.ser");

			// ouverture d'un flux sur un fichier
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(fichier));

			// création d'un objet à sérializer
			Map map = new Map();
			
			Vertex brest = new Vertex("Brest");
			Vertex rennes = new Vertex("Rennes");
			Vertex rouen = new Vertex("Rouen");
			Vertex paris = new Vertex("Paris");
			Vertex leMans = new Vertex("Le Mans");
			Vertex tours = new Vertex("Tours");
			Vertex angers = new Vertex("Angers");
			Vertex poitiers = new Vertex("Poitiers");
			Vertex nantes = new Vertex("Nantes");
			
			map.add(brest);
			map.add(rennes);
			map.add(rouen);
			map.add(paris);
			map.add(leMans);
			map.add(tours);
			map.add(angers);
			map.add(poitiers);
			map.add(nantes);
			
			brest.add(rennes, 2.4);
			rennes.add(rouen, 2.8).add(leMans, 0.9).add(nantes, 1.05);
			rouen.add(leMans, 1.9).add(paris, 1.2);
			paris.add(leMans, 1.8);
			leMans.add(tours, 1.1).add(angers, 0.95);
			angers.add(tours, 1.2).add(nantes, 0.95);
			poitiers.add(tours, 0.8).add(nantes, 2.1);

			// sérialization de l'objet
			for(Vertex v : map.townList){
				oos.writeObject(v);
			}
			oos.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
