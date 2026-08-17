package Freemium.users;

import S3_FREEMIUM.CarteBancaire;

public class Premium extends Utilisateur {
	
	protected static final int montant = 10;
	
	public Premium(Personne personne, CarteBancaire cb) throws Exception{
		super(personne);
		if(cb == null){
			throw new Exception("Je veux une carte bancaire !!!");
		}
		personne.setCb(cb);
	}

	protected boolean droit() {
		return true;
	}

	@Override
	public void updateMonthly() {
		personne.getCb().paye(montant);
	}
	
}
