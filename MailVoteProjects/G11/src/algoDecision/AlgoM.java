package algoDecision;

import java.util.ArrayList;
import java.util.List;

public class AlgoM { // L'algo ne fonctionne qu'avec des matrices carrés

	private static int n; // nb lignes et colonnes
	private static int[][] m; // matrice
	private static int[] cMarq; // tableau de marquage des colonnes
	private static int[] lMarq; // tableau de marquage des lignes
	private static int[][] marquage; // Matrice qui contient les 0 encadrés

	private static List<List<Integer>> verifM(List<List<Integer>> a) { // Méthode qui vérifie que la marice (sous le type liste) est carré		
																	 // Si elle ne l'est pas, on rajoute des lignes avec pour valeur 0
		int nbL = a.size();
		int nbC = a.get(0).size();

//		//System.out.println("Nb de lignes :");
//		//System.out.println(a.size()); // Nb Lignes
//		//System.out.println("Nb de colonnes :");
//		//System.out.println(a.get(0).size()); // Nb Colonnes

		if (nbL != nbC) {
			if (nbL < nbC) { // Si il y a moins d'étudiants que de projet on
								// agrandis la matrice avec des 0 pour la rendre
								// carré
				// et pouvoir utiliser l'algo
				int diff = nbC - nbL;
				////System.out.println("La diff :" + diff);
				for (int i = 0; i < diff; i++) {
					List<Integer> ligne = new ArrayList<Integer>();
					for (int j = 0; j < nbC; j++) {
						ligne.add(new Integer(0)); // On initialise à 0
					}
					a.add(ligne);
				}
			} else {// Il ne peut pas y avoir moins de projets que d'étudiants
				//System.out.println("Error, il y a moins de projet que d'étudiants");
			}
		} else {
			////System.out.println("La matrice est carré");
		}
		return a;
	}

	private static int[][] convertLM(List<List<Integer>> a) { // Converti une matrice liste en matrice de int

		int[][] m = new int[a.size()][a.size()];

		for (int i = 0; i < a.size(); i++) {
		//	//System.out.println(a.get(i));
			for (int j = 0; j < a.size(); j++) {
				m[i][j] = a.get(i).get(j);
			}
		}

		return m;
	}

	public static void afficherMat(int[][] matrice) { // Méthode pour afficher le contenu d'une matrice												
		for (int i = 0; i < matrice.length; i++) {
			for (int y = 0; y < matrice[i].length; y++) {
//				System.out.print(matrice[i][y] + "|");
			}
//				System.out.println();
		}
//		System.out.println();
	}

	

	
	
	
	
	private static void clearMarq(int[] lMarq, int[] cMarq) //Méthode pour remettre les tableaux de marquages de lignes et de colonnes à 0
	{
		for (int i = 0; i < lMarq.length; i++) {
			lMarq[i] = 0;
		}
		for (int j = 0; j < cMarq.length; j++) {
			cMarq[j] = 0;
		}
	}

	private static int findMax(int[][] matrice) { // retourne le plus grand
												// élément d'une matrice
		int max = 0;
		for (int r = 0; r < matrice.length; r++) {
			for (int c = 0; c < matrice[r].length; c++) {
				if (matrice[r][c] > max) {
					max = matrice[r][c];
				}
			}
		}
		return max;
	}

	private static int[] findUncoveredZero(int[] row_col, int[][] m, int[] lMarq, int[] cMarq) { // Retourne lignes/Colonnes avec les 0 encadrés
	
		row_col[0] = -1; // Juste une valeur de base 
		row_col[1] = 0;

		int i = 0;
		boolean done = false;
		while (done == false) {
			int j = 0;
			while (j < n) {
				if (m[i][j] == 0 && lMarq[i] == 0 && cMarq[j] == 0){ // Si il y a un zero et qu'il n'es pas noté dans le tableau de
																	// zero														
				
					row_col[0] = i; // On note la position
					row_col[1] = j;
					done = true;
				}
				j = j + 1;
			}
			i = i + 1;
			if (i >= n) {
				done = true;
			}
		} 

		return row_col;
	}

	/*--------------------------------------------------------------------------------------------------*/

	
	private static int[][] AlgoHongrois(int[][] matrice) {  // Corps de la fonction de l'algorithme Hongrois

		n = matrice.length;
		cMarq = new int[n];
		lMarq = new int[n];

		// Copie de la matrice
		m = new int[n][n];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				m[x][y] = matrice[x][y];
			}
	//		//System.out.println();
		}

		marquage = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				marquage[i][j] = 0;
			}
		}

		int max = findMax(m);

		
		  for (int i = 0; i < n; i++){ // Ici on ramènne l'affectation maximale
			  						 // une affectation minimale pour pour voir utiliser l'algo 
			  for (int j = 0; j < n; j++)
			  { 
				  m[i][j] = (max - m[i][j]); // On soustrait donc la valeur max de la matrice par les éléments de celle-ci
			  							    
			  } 		  
		  }
		  

		int maxCout = findMax(m); // Pour l'étape 6 on cherche le plus grand élément de la matrice

		int[] zero_RC = new int[2]; // Position (x et y) du dernier 0 (pour
									// l'étape 4)
		int step = 1;

		boolean finis = false;
		while (finis == false) { // Comme il est possible de revenir à des étapes précédentes, un switch case est parfaitement adapté
			switch (step) {
			case 1:
				step = step1(step, m);
				break;
			case 2:
				step = step2(step, m, marquage, lMarq, cMarq);
				break;
			case 3:
				step = step3(step, marquage, cMarq);
				break;
			case 4:
				step = step4(step, m, marquage, lMarq, cMarq, zero_RC);
				break;
			case 5:
				step = step5(step, marquage, lMarq, cMarq, zero_RC);
				break;
			case 6:
				step = step6(step, m, lMarq, cMarq, maxCout);
				break;
			case 7:
				////System.out.println("Step 7");
				finis = true;
				break;
			}
		} 

	int[][] assignment = new int[n][2]; //Ici on créer la matrice de retour qui affectera chaque ligne à une colonne
		for (int i = 0; i < marquage.length; i++) {
			for (int j = 0; j < marquage[i].length; j++) {
				if (marquage[i][j] == 1) {
					assignment[i][0] = i;
					assignment[i][1] = j;
				}
			}
		}
	return assignment;
	}

	private static int step1(int step, int[][] m) {

		// STEP 1 :
		// Pour chaque element de la ligne trouver le plus petit élément
		// et le soustraire à chaque éléméent de la ligne pour réduire le  tableau
		// En effet, on cherche à avoir au moins un 0 par ligne et par colonnes
		
		////System.out.println("Step 1");
		//afficherMat(m);
		int min;
		for (int x = 0; x < n; x++) {
			min = m[x][0];
			for (int y = 0; y < n; y++) // Pour chaque lignes on recherche la
										// plus petite valeur
			{
				if (min > m[x][y]) {
					min = m[x][y];
				}
			}
			for (int y = 0; y < n; y++) // On soustrait ensuite chaque valeur de
										// la ligne concerné du tableau .
			{
				m[x][y] = m[x][y] - min;
			}
		}

		for (int y = 0; y < n; y++) {
			min = m[0][y];
			for (int x = 0; x < n; x++) // Pour chaque colonnes on recherche la
										// plus petite valeur
			{
				if (min > m[x][y]) {
					min = m[x][y];
				}
			}

			for (int x = 0; x < n; x++) { // On soustrait ensuite chaque valeur
											// du tableau.

				m[x][y] = m[x][y] - min;
			}
		}

		//System.out.println("\nTableau réduit: ");
		//afficherMat(m);

		step = 2;
		return step;
	}

	
	private static int step2(int step, int[][] m, int[][] marquage, int[] lMarq, int[] cMarq) {
		// STEP 2 :
		// On marque les 0 dans l'ordre et on marque leur lignes et colonnes
		// (via les tableaux lMarq et cMarq)
//		//System.out.println("Step 2");
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if ((m[row][col] == 0) && (cMarq[col] == 0) && (lMarq[row] == 0)) { // Si il y  a un zero et que la ligne et colonne 
																					// n'est pas marquée
																			
					marquage[row][col] = 1; // on ajoute à 1 dans la matrice de
											// marquage (0 encadré sur feuille)
					cMarq[col] = 1; // on ajoute 1 dans le tableau des colonnes
									// Marquees
					lMarq[row] = 1; // on ajoute 1 dans le tableau des lignes
									// Marquees
				}
			}
		}
	//	//System.out.println("Matrice de marquage");
	//	afficherMat(marquage);
		clearMarq(lMarq, cMarq);
		step = 3;
		return step;
	}

	private static int step3(int step, int[][] marquage, int[] cMarq) {
		// STEP 3:
		// Dans cette étape, on vérifie si on a bien un 0 par ligne et par colonne 
		// Si c'est la cas, on a fini
		// Sinon on passe à l'étape 4 (on marques les lignes et les colonnes)

		for (int i = 0; i < n; i++) {
									
			for (int j = 0; j < marquage[i].length; j++) {
				if (marquage[i][j] == 1) {
					cMarq[j] = 1;
				}
			}
		}

	//	//System.out.println("Step 3");

		int cmpt = 0;
		for (int j = 0; j < n; j++){ // On regarde si un a un 0 par colonnes donc
									// si toutes les colonnes sont marquées
								 	// On ne peut pas avoir deux 0 par colonnes vu qu'on les a marquées
									// dans l'ancienne étape
			cmpt = cmpt + cMarq[j];
		}

		if (cmpt >= n) {
	//		//System.out.println("un 0 par lignes et par colonnes : Finis");
			step = 7; // Finis
		} else {
  //			//System.out.println("pas assez de 0 : on modifie le tableau");
			step = 4;
		}

		return step;
	}

	/*-------------------------------------------------------------------------------------------------------*/

	private static int step4(int step, int[][] m, int[][] marquage, int[] lMarq, int[] cMarq, int[] zero_RC) {
		// STEP 4 :
		// Dans cette étape, on marque les lignes et les colonnes:
		//a) on marque toutes les lignes sans 0 encadrés 
		//b) on marque les colonnes où il y a un zero barré sur la ligne barré
		//c) on marque les lignes où les colonnes sont marqués et qui contiennent un 0 encadré
		// On répète tant que possible l'étape b) et c) puis on raye les lignes non marquées et les colonnes marquées

//		//System.out.println("Step 4");
		int[] row_col = new int[2]; 
		boolean done = false;
		while (done == false) {
			row_col = findUncoveredZero(row_col, m, lMarq, cMarq); // etape a) on cherche les ligne sans 0 couverts (encadré)
			if (row_col[0] == -1) // Si il n'y pas de position du dernier 0
			{
				done = true; // On passe à l'étape 6 (on réduit les cellules doublements barrées)
				step = 6;
			} else {
				marquage[row_col[0]][row_col[1]] = 2; //On prend en compte un deuxieme 0 par lignes et colonnes 
				
				boolean zeroEncadre = false;
				for (int j = 0; j < marquage[row_col[0]].length; j++) {
					if (marquage[row_col[0]][j] == 1) // SI il y a un 0 encadré dans la meme colonnes 
					{
						zeroEncadre = true;
						row_col[1] = j; // On garde en mémmoire cette colones
					}
				}

				if (zeroEncadre == true) {
					lMarq[row_col[0]] = 1; //On raye la ligne non marqué 
					cMarq[row_col[1]] = 0; // On raye la colonne marquée
				} else {
					zero_RC[0] = row_col[0]; 
					zero_RC[1] = row_col[1]; 
					done = true;
					step = 5;
				}
			}
		}
	//	//System.out.println("Nouvelle martrice de marquage : ");
		//afficherMat(marquage);
		return step;
	}

	private static int step5(int step, int[][] marquage, int[] lMarq, int[] cMarq, int[] zero_RC) {
	//	//System.out.println("Step 5");
		// STEP 5:
		// Dans cette étape on selectionne les zero dans la matrice
		// On prends la ligne avec le moind de 0, on le selectionne et barre tous les 0 sur sa ligne et colonne
		
		int count = 0; // Counts rows of the path matrix.
		int[][] path = new int[(marquage[0].length * marquage.length)][2]; // Path matrix qui stocke les lignes et colonnes
																			
		//System.out.println("Path vide:");
		//afficherMat(path);
		
		path[count][0] = zero_RC[0];  
		path[count][1] = zero_RC[1]; 

		boolean done = false;
		while (done == false) {
			int r = findStarInCol(marquage, path[count][1]);
			if (r >= 0) {
				count = count + 1;
				path[count][0] = r; 
				path[count][1] = path[count - 1][1]; 
			} else {
				done = true;
			}

			if (done == false) {
				int c = findPrimeInRow(marquage, path[count][0]);
				count = count + 1;
				path[count][0] = path[count - 1][0]; // Row of primed zero.
				path[count][1] = c; // Col of primed zero.
			}
		} 

		convertPath(marquage, path, count);
		clearMarq(lMarq, cMarq);
		erasePrimes(marquage);
		step = 3;
		return step;

	}

	private static int findStarInCol (int[][] marquage, int col) {  //retourne la ligne avec les 0 marqué dans une colonne 
		int r = -1; 
		for (int i = 0; i < marquage.length; i++) {
			if (marquage[i][col] == 1) {
				r = i;
			}
		}

		return r;
	}

	private static int findPrimeInRow (int[][] marquage, int row) {	//Retourne une colonne avec les 0 marqués dans une ligne
		int c = -1;
		for (int j = 0; j < marquage[row].length; j++) {
			if (marquage[row][j] == 2) {
				c = j;
			}
		}

		return c;
	}

	private static void convertPath 	(int[][] marquage, int[][] path, int cmpt) { //Méthode pour convertir la matrice Path et met à jour 
																				 //la matrice de marquage
		for (int i = 0; i <= cmpt; i++) {
			if (marquage[(path[i][0])][(path[i][1])] == 1) {
				marquage[(path[i][0])][(path[i][1])] = 0;
			} else {
				marquage[(path[i][0])][(path[i][1])] = 1;
			}
		}
	}

	private static void erasePrimes(int[][] marquage) { // Rayes les 0 non selectionnés dans la matrice de marquage
		for (int i = 0; i < marquage.length; i++) {
			for (int j = 0; j < marquage[i].length; j++) {
				if (marquage[i][j] == 2) {
					marquage[i][j] = 0;
				}
			}
		}
	}

	private static int step6(int step, int[][] m, int[] lMarq, int[] cMarq, int maxCout) {
		//  STEP 6 :
		// On trouve la plus petite valeur dans la nouvelle matrice 
		//Qui contient les éléments de a matices qui ne sont pas barrés
		// On soustrait ce chifre à tous les chiffres de la nouvelle matrice 
		//(chiffres non barré). ON additionne ce chiffre aux chiffre doublement barré
		// de la matrice originale
		
		//System.out.println("Step 6");
		int minval = findMin(m, lMarq, cMarq, maxCout);

		for (int i = 0; i < lMarq.length; i++) {
			for (int j = 0; j < cMarq.length; j++) {
				if (lMarq[i] == 1) {
					m[i][j] = m[i][j] + minval;
				}
				if (cMarq[j] == 0) {
					m[i][j] = m[i][j] - minval;
				}
			}
		}

		step = 4;
		return step;
	}

	private static int findMin(int[][] m, int[] lMarq, int[] cMarq, int maxCout) { // fonction qui retourne 
																				  //le plus petit élément d'une matrice
		int minval = maxCout; 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m[i].length; j++) {
				if (lMarq[i] == 0 && cMarq[j] == 0 && (minval > m[i][j])) {
					minval = m[i][j];
				}
			}
		}

		return minval;
	}
	
	public static int[][] algo(List<List<Integer>> liste) { 
		/** Fonction principale. Elle prend en paramètre un liste venant des données locales.
		 * Elle retorune une matrice d'entiers qui affecte chaque lignes à une colonne.
		 * Dans cette fonction, la liste est rendu carré puis est converti en matrice de int[][].  
		 */
		int[][] matrice = new int[liste.size()][liste.size()]; 
		int[][] fin = new int[2][liste.size()]; //
		verifM(liste);
		matrice=convertLM(liste);
		fin= AlgoHongrois(matrice);
		//afficherMat(fin);
		return fin;
	}
	
	

//	public static void main(String[] args) {
//		
//		/*int[][] matrice = { { 4, 12, 10, 11}, {12, 6, 16, 15}, { 16, 20, 18, 16},{13, 16, 15, 14}};
//		int[][] fin = new int[n][n];
//		fin = AlgoHongrois(matrice);
//		//System.out.println("Assignement : ");
//		afficherMat(fin);*/
//
//		
//		// Partie de test avec une liste 
//		
//		List<List<Integer>> a = new ArrayList<List<Integer>>();
//
//		for (int i = 0; i <2 ; i++) {
//			List<Integer> ligne = new ArrayList<Integer>();
//			for (int j = 0; j < 8; j++) {
//				ligne.add(new Integer(1)); // On initialise à 0
//			}
//			a.add(ligne);
//		}
//		////System.out.println(a.size());
//
//		/*verifM(a);
//		verifM(a);
//
//		afficherMat(convertLM(a));*/
//		algo(a);
//	}
//	
	
	
}
