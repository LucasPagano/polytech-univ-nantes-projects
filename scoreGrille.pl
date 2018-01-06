%define the size of the grille
grilleSize(NbIndiv,Size):-
	Size is ceiling(sqrt(NbIndiv)).

generateIndivList(0, _, []).
generateIndivList(NbIndiv, NbDimensions, IndivList):-
	NbIndiv > 0,
	NbIndiv1 is NbIndiv - 1,
	generateIndiv(Indiv, NbDimensions),
	IndivList = [Indiv|T],
	generateIndivList(NbIndiv1, NbDimensions, T).

generateIndiv(Indiv, NbDimensions):-
	length(Indiv, NbDimensions),
	maplist(random(0.0, 1.0), Indiv).

%put all individuals into the grille
g([],_,0,_,[]).
g([HeadIndiv|TailIndiv],NbIndiv,Nb,GrilleSize,Grille):-
	Nb > 0,
	X is floor((NbIndiv-Nb)/GrilleSize),
	Y is (NbIndiv-Nb) mod GrilleSize,
	Grille = [[X,Y]-HeadIndiv|TailGrille],
	Nb1 is Nb-1,
	g(TailIndiv,NbIndiv,Nb1,GrilleSize,TailGrille).

%get the individual at the place [X,Y]
findIndiv([X,Y],Grille,Indiv):-
	member([X,Y]-Indiv, Grille).

%4 neighbour cases
case4([X,Y],[VoisinX,VoisinY],Size):-
	X > 0 0, Y >= 0,
	X < Size, Y < Size,
	VoisinX is X-1, VoisinY is Y.
case4([X,Y],[VoisinX,VoisinY],Size):-
	X >= 0 0, Y >= 0,
	X < Size-1, Y < Size,
	VoisinX is X+1, VoisinY is Y.
case4([X,Y],[VoisinX,VoisinY],Size):-
	X >= 0, Y > 0,
	X < Size, Y < Size,
	VoisinX is X, VoisinY is Y-1.
case4([X,Y],[VoisinX,VoisinY],Size):-
	X >= 0, Y >= 0,
	X < Size, Y < Size-1,
	VoisinX is X, VoisinY is Y+1.
cases4([X,Y],List,Size):-
	findall(Voisin, case4([X,Y],Voisin,Size), List).

%4 diagonal cases
case8([X,Y],[VoisinX,VoisinY],Size):-
	X > 0, Y > 0,
	X < Size, Y < Size,
	VoisinX is X-1,VoisinY is Y-1.
case8([X,Y],[VoisinX,VoisinY],Size):-
	X > 0, Y >= 0,
	X < Size, Y < Size-1,
	VoisinX is X-1, VoisinY is Y+1.
case8([X,Y],[VoisinX,VoisinY],Size):-
	X >= 0, Y > 0,
	X < Size-1, Y < Size,
	VoisinX is X+1,VoisinY is Y-1.
case8([X,Y],[VoisinX,VoisinY],Size):-
	X >= 0, Y >= 0,
	X < Size-1, Y < Size-1,
	VoisinX is X+1, VoisinY is Y+1.
cases8([X,Y],List,Size):-
	findall(Voisin, case8([X,Y],Voisin,Size), List).

%calculate the difference between two individuals
differenceIndiv([],[],0).
differenceIndiv([HeadDimension1|TailDimension1],[HeadDimension2|TailDimension2],Difference):-
	length([HeadDimension1|TailDimension1], X), X > 0,
	Differ is (HeadDimension1-HeadDimension2)*(HeadDimension1-HeadDimension2),
	differenceIndiv(TailDimension1,TailDimension2,TailDifference),
        Difference is Differ + TailDifference.
difference(Indiv1,Indiv2,Differ-Indiv2):-
	differenceIndiv(Indiv1,Indiv2,Difference),
	Differ is sqrt(Difference).

%get a list of differences between an indiv and others
differenceList(_,[],[]).
differenceList(Indiv,[HeadIndiv|TailIndiv],DifferenceList):-
	length([HeadIndiv|TailIndiv],X), X>0,
	difference(Indiv, HeadIndiv, Differ-HeadIndiv),
	DifferenceList = [Differ-HeadIndiv|TailDifferenceList],
	differenceList(Indiv, TailIndiv, TailDifferenceList).

%find NbMin minmums in the list and get the list of individuals
minDifferList(0,_,[]).
minDifferList(NbMin,List,MinList):-
	length(List,L), L > 0, NbMin > 0,
	min_member(Min,List),
	NbMin1 is NbMin -1,
	delete(List,Min,NewList),
	MinList = [Min|TailMinList],
	minDifferList(NbMin1,NewList,TailMinList).

%find 4 individuals who return the smallest difference with the indiv.
voisins4(Indiv,IndivList,VoisinIndivList):-
	differenceList(Indiv, IndivList, DifferList),
	minDifferList(5,DifferList,[_|DiVoisinIndivList]),
	getIndiv(DiVoisinIndivList,VoisinIndivList).

% find 4 individuals who return the 5th-8th smallest difference with the
% indiv.
voisins8(Indiv,IndivList,VoisinIndivList):-
	differenceList(Indiv, IndivList, DifferList),
	minDifferList(9,DifferList,[_,_,_,_,_|DiVoisinIndivList]),
	getIndiv(DiVoisinIndivList,VoisinIndivList).

%get the list of individuals from the list differ-indiv
getIndiv([],[]).
getIndiv([Differ-Indiv|TailList],IndivList):-
	length([Differ-Indiv|TailList],L),L>0,
	IndivList = [Indiv|TailIndiv],
	delete([Differ-Indiv|TailList],Differ-Indiv,NewList),
	getIndiv(NewList,TailIndiv).


%if the indiv is placed at the right place, count 1 point.
point(Indiv,IndivList,Point):-
	member(Indiv,IndivList) -> Point is 1.
point(Indiv,IndivList,Point):-
	not(member(Indiv,IndivList)) -> Point is 0.


%calculate the score for a situation
scoreVoisin(_,_,[],_,0).
scoreVoisin(Grille,Indiv,[HeadVoisinCase|TailVoisinCase],VoisinIndivList,Score):-
	length([HeadVoisinCase|TailVoisinCase],L),L > 0,
	findIndiv(HeadVoisinCase,Grille,IndivInVoisinCase),
	point(IndivInVoisinCase,VoisinIndivList,P),
	scoreVoisin(Grille,Indiv,TailVoisinCase,VoisinIndivList,TailScore),
	Score is P + TailScore.

%calculate the score of a case
scoreCase(Grille,GrilleSize,Case,Indiv,IndivList,S):-
	cases4(Case,VoisinCase4,GrilleSize),
	cases8(Case,VoisinCase8,GrilleSize),
	voisins4(Indiv,IndivList,VoisinIndiv4),
	voisins8(Indiv,IndivList,VoisinIndiv8),
	scoreVoisin(Grille,Indiv,VoisinCase4,VoisinIndiv4,S1),
	scoreVoisin(Grille,Indiv,VoisinCase8,VoisinIndiv8,S2),
	scoreVoisin(Grille,Indiv,VoisinCase4,VoisinIndiv8,S3),
	S is S1+S2+S3/2.

%calculate the score for the whole grille
score(_,_,[],_,0,0).
score(Grille,GrilleSize,[HeadCase-HeadIndiv|TailGrille],IndivList,NbIndiv,Score):-
	length([HeadCase-HeadIndiv|TailGrille],L), L > 0,
	write(HeadCase),
	write(HeadIndiv),
	scoreCase(Grille,GrilleSize,HeadCase,HeadIndiv,IndivList,S),
	write(S), nl,
        Nb1 is NbIndiv - 1,
	score(Grille,GrilleSize,TailGrille,IndivList,Nb1,TailS),
	Score is S+TailS.

list([[1,3],[4,4],[1,1],[2,3],[7,5],[10,5],[6,6],[7,27],[18,8]]).

%test
main():-
	generateIndivList(25,2,IndivList),
%	list(IndivList),
	write("IndivList = "), write(IndivList), nl,
	length(IndivList,NbIndiv),
	write("NbIndiv = "), write(NbIndiv), nl,
	grilleSize(NbIndiv,Size),
	write("GrilleSize "), write(Size), nl,
	g(IndivList,NbIndiv,NbIndiv,Size,Grille),
	write("Grille = "), write(Grille), nl,
	cases4([0,0],Cases4,Size),
	write("Neighbor cases for [0,0] = "), write(Cases4), nl,
        cases8([0,0],Cases8,Size),
	write("Diag cases for [0,0] = "), write(Cases8), nl,
        findIndiv([0,0],Grille,Indiv),
	write("Indiv at [0,0] = "), write(Indiv), nl,
        voisins4(Indiv,IndivList,Voisins4),
	write("D-Voisins4 for Indiv = "), write(Voisins4), nl,
        voisins8(Indiv,IndivList,Voisins8),
	write("D-Voisins4 for Indiv = "), write(Voisins8), nl,
	scoreVoisin(Grille,Indiv,Cases4,Voisins4,S1),
	write("Score for voisin4 = "),write(S1), nl,
        scoreCase(Grille,Size,[0,0],Indiv,IndivList,S),
	write("Score for case1 = "),write(S), nl,
	score(Grille,Size,Grille,IndivList,NbIndiv,Score),
	write("Score is = "),write(Score).
