/* javadoc -public -charset utf8 Heros.java */
public class Heros {
    public String nom ;

    int max_vie ;
    int points_vie ;
    int points_attaque ; 
    int points_defense ;
    int vitesse ;
    
    /** 
     * @param s Nom.
     * @param max_pv Nombre max de points de vie.
     * @param pa Points d'attaque.
     * @param pd Points de défense.
     * @param vit Vitesse.
     */
    public Heros(String s, int max_pv, int pa, int pd, int vit){
	nom = s ;
	max_vie = max_pv ;
	points_vie = max_pv ;
	points_attaque = pa ;
	points_defense = pd ;
	vitesse = vit ;
    }

    public boolean actif(){
	return points_vie > 0 ;
    }

    void ko(){
	this.points_vie = 0 ;
	System.out.println(nom + " KO ! ");
    }

    void subit_attaque(int pa){
	if (pa > this.points_defense) {
	    final int degats = pa - this.points_defense ;
	    if (degats > points_vie){
		this.ko();
	    }
	    else {
		this.points_vie = this.points_vie - degats ;
	    }
	}
	else {}
    }

    public void attaque(Heros p2){
	p2.subit_attaque(this.points_attaque) ;
	if (p2.actif()) 
	    {
		this.subit_attaque (p2.points_attaque);
		if (this.vitesse > p2.vitesse + 5) {
		    p2.subit_attaque(this.points_attaque) ;
		}
	    }
    }

    @Override
    public String toString(){
	return ("{" + nom + "(" + points_vie + ")}");
    }

}
