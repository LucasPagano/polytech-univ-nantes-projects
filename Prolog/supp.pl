supp(_,[],[]).
supp(E,[E|L],R):-
	supp(E,L,R).
supp(E,[X|L],[X|R]):-
	X\=E,
	supp(E,L,R).
	