pile(P,S,[S|P]).


calcul([],P,R):-
	pile(_,R,P).

calcul(L,P,R):- L\=[],
	pile(L2,S,L),
	number(S),
	pile(P,S,P2),
	calcul(L2,P2,R).

calcul(L,P,R):- L\=[], pile(L2,S,L), S = +,
	pile(P2,S1,P),
	pile(P3,S2,P2),
	R is S2+S1,
	pile(P3,R,Q),
	calcul(L2,Q,R).

calcul(L,P,R):- L\=[], pile(L2,S,L), S = -,
	pile(P2,S1,P),
	pile(P3,S2,P2),
	R is S2-S1,
	pile(P3,R,P4),
	calcul(L2,P4,R).
	 







