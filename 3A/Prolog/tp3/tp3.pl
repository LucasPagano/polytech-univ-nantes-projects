genarbre( noeud(noeud(noeud(nil,2,nil),3,noeud(nil,5,nil)),5,noeud(noeud(nil,7,nil),8,noeud(nil,10,nil)))).

prefixe(nil).

prefixe(noeud(G,Valeur,D)):-
	write(Valeur),
	write(','),
	prefixe(G),
	prefixe(D).


infixe(noeud(G,Valeur,D)):-
	infixe(G),
	write(Valeur),
	write(','),
	infixe(D).

