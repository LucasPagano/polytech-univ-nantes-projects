package Freemium.users;

public class Free extends Utilisateur {

	protected final int maxEcoute = 3;
	
	public Free(Personne personne){
		super(personne);
	}
	
	protected boolean droit(){
		return this.personne.nbEcoute <= maxEcoute;
	}

	@Override
	public void updateMonthly() {}
	
}
