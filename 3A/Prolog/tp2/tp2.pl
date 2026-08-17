route1(nantes,rennes,110).
route1(nantes,vannes,120).
route1(nantes,saint_nazaire,60).
route1(nantes,bordeaux,340).
route1(nantes,angers,90).
route1(rennes,brest,250).
route1(rennes,angers,130).
route1(rennes,saint_malo,70).
route1(rennes,mont_saint_michel,70).
route1(rennes,tours,240).
route1(angers,tours,130).
route1(vannes,brest,190).
route1(brest,saint_malo,230).
route1(saint_malo,mont_saint_michel,60).
route1(bordeaux,tours,350).
route1(mont_saint_michel,caen,200).

/* Question 1 : Ecrire un prédicat rendant ce graphe non orienté : on doit pouvoir passer dans les 2 sens */

route(A,B,D):-
    route1(A,B,D).
route(A,B,D):-
    route1(B,A,D).

/* Question 2 : Ecrire un programme PROLOG cherchant tous les chemins entre deux villes. */

absent(_,[]).
absent(X,[Y|L]):- X\=Y,
    absent(X,L).

chemin(X,Y,D,[],_):-
    route(X,Y,D).
chemin(X,Y,D,[T|INTER],DEJA):-
    route(X,T,D1),
    absent(T, DEJA),
    chemin(T,Y,D2,INTER,[T|DEJA]),
    D is D1+D2.

/* Question 3 : Créer une liste comportant tous les chemins entre deux villes */

liste(X,Y,R):-
    findall(L,chemin(X,Y,_,L,[X]),R).


/*Question 4 : Le jeu le compte est bon peut être modélisé par un graphe orienté. Les sommets du graphe
contiennent l'état du jeu à un moment donné (liste des nombres disponibles et nombre à
trouver). Un arc correspond à une opération arithmétique licite entre 2 nombres disponibles.
Une solution est un chemin dans ce graphe entre le sommet de départ et un sommet final (le
nombre à trouver est dans la liste des nombres disponibles).*/

elem([E|_],E).
elem([_|L],E):- elem(L,E).

supp(_,[],[]).
supp(E,[E|L],L).
supp(E,[X|L],[X|R]):-
	X\=E,
	supp(E,L,R).


present(N,L):- not(absent(N,L)).

resoudre(L,E,[]):-
    member(E,L).


/* X est l'opération réalisée*/

resoudre(L,E,[X|R]):-
    not(member(E,L)),
    combiner(L,L1,X),
    resoudre(L1, E, R).

combiner(L,L3,UNE_OP):-
    elem(L,NB1),
    supp(NB1, L, L1),
    elem(L1,NB2),
    supp(NB2,L1,L2),
    operation(NB1,NB2,R,UNE_OP),
    L3 = [R|L2].

operation(NB1,NB2,RES,[NB1, '+', NB2, '=', RES]):- 
    NB1 > 0,
    NB2 > 0,
    NB1 >= NB2,
    RES is NB1 + NB2.

operation(NB1,NB2,RES,[NB1, '-', NB2, '=', RES]):-
    NB1 > 0,
    NB2 > 0,
    RES is NB1 - NB2.

operation(NB1,NB2,RES,[NB1, '*', NB2, '=', RES]):-
    NB1 > 1,
    NB2 > 1,
    NB1 >= NB2,
    RES is NB1 * NB2.

operation(NB1,NB2,RES,[NB1, '/', NB2, '=', RES]):-
    NB1 > 0,
    NB2 > 0,    
    RES is NB1 / NB2.

afficher([]).
afficher([X|Y]):- writeln(X), afficher(Y).
    
resoudre2(L,E):-
    resoudre(L,E,R),
    afficher(R).