% How to use : generateIndivList(2, 4, X), createFirstState(X, InitialState),  aStar(X, [InitialState], R).

%Predicate used to create first state from indiv list
createFirstState([IndivListHead|_], ([], ToPlace)):-
	length(IndivListHead, L),
	numlist(1, L, ToPlace).

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

computeAreaList([], _, 0).
computeAreaList([FirstIndiv|TailIndiv], Permutation, Area):-
	computeAreaIndiv(FirstIndiv, Permutation, AreaIndiv),
	computeAreaList(TailIndiv, Permutation, AreaList),
	Area is AreaList + AreaIndiv.

%Second argument is Permutation list
computeAreaIndiv(Indiv, [FirstPi, SecondPi|TailPi], Area):-
	nth1(FirstPi, Indiv, ValueOfFirst),
	%We have to remember the value of the first one for the last triangle
	hiddenComputeAreaIndiv(Indiv, [FirstPi, SecondPi|TailPi], ValueOfFirst, Area).
/**
*tests :
*computeAreaList([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,2,3,4], Area).
*"AreaIndiv : "2.0
*"AreaIndiv : "0.0
*"AreaIndiv : "1.0
*"AreaIndiv : "0.5
*Area = 3.5;
*
*computeAreaList([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,3,2,4], Area).
*"AreaIndiv : "2.0
*"AreaIndiv : "0.5
*"AreaIndiv : "1.125
*"AreaIndiv : "0.375
*Area = 4.0 ;
*/

hiddenComputeAreaIndiv(_, [], _, 0).
hiddenComputeAreaIndiv(Indiv, [FirstPi, SecondPi|TailPi], VeryFirst, Area):-
	length(TailPi, X), X > 1,
	nth1(FirstPi, Indiv, Dim1),
	nth1(SecondPi, Indiv, Dim2),
	triangleArea(Dim1, Dim2, Triangle),
	hiddenComputeAreaIndiv(Indiv, [SecondPi|TailPi], VeryFirst, AreaOfTail),
	Area is Triangle + AreaOfTail.

%If we got to the three last dimensions, we take care to compute the according triangle areas
hiddenComputeAreaIndiv(Indiv, [FirstPi, SecondPi, TailPi], VeryFirst, Area):-
	nth1(FirstPi, Indiv, Dim1),
	nth1(SecondPi, Indiv, Dim2),
	nth1(TailPi, Indiv, DimTail),
	triangleArea(Dim1, Dim2, Triangle),
	triangleArea(Dim2, DimTail, Triangle2),
	triangleArea(DimTail, VeryFirst, Triangle3),
	Area is Triangle + Triangle2 + Triangle3.

triangleArea(Dimension1, Dimension2, Area):-
	Dimension1 =< 1, Dimension1 >=0,
	Dimension2 =< 1, Dimension2 >=0,
	Area is 0.5*Dimension1*Dimension2.

set(Elem, FromList, ToList, NewFromList, NewToList):-
	member(Elem, FromList),
	delete(FromList, Elem, NewFromList),
	%We put elem in second place because we want to append at end of list
	append(ToList, [Elem], NewToList).

%Predicate which forms a pair containing a state and its value, from a state and a list of individuals, to use with maplist
%For now the value is only the area, we want to include the heuristic
stateValue(IndivList, (Placed, ToPlace), R-(Placed, ToPlace)):-
	computeAreaList(IndivList, Placed, Value),
	%we negate the value so keysort puts the larger at the front
	R is - Value.

initialState(state([],[1,2,3])).
final(_, ToPlace):-
	ToPlace = [].
initial(Placed, _):-
	Placed = [].

aStar(_, [(HeadStatePlaced, HeadStateToPlace)|_], HeadStatePlaced):-
  final(HeadStatePlaced, HeadStateToPlace).

	%At first, two dimensions are chosen so area has a meaning and can be computed
aStar(IndivList, [(HeadStatePlaced, HeadStateToPlace)|_], Result):-
	initial(HeadStatePlaced, HeadStateToPlace),
	%Arbitrary choose the first two dimensions, here we take the first ones, we might want to change this
	nth1(1, HeadStateToPlace, Dimension1),
	nth1(2, HeadStateToPlace, Dimension2),
	set(Dimension1, HeadStateToPlace, HeadStatePlaced, NewToPlace, NewPlaced),
	set(Dimension2, NewToPlace, NewPlaced, NewToPlace2, NewPlaced2),
	aStar(IndivList, [(NewPlaced2, NewToPlace2)], Result).

aStar(IndivList, [(HeadStatePlaced, HeadStateToPlace)|TailStates], Result):-
	not(final(HeadStatePlaced, HeadStateToPlace)),
	not(initial(HeadStatePlaced, HeadStateToPlace)),
	%find head of stack's children
	findall(P, (set(_, HeadStateToPlace, HeadStatePlaced, NewFromList, NewToList), P = (NewToList, NewFromList)), Children),
	append(TailStates, Children, NewStates),
	%give each state its value
	maplist(stateValue(IndivList), NewStates, StateValues),
	%sort the values, since they are negated, the bigger one will come first
	keysort(StateValues, [_- BestState| _ ]),
	%Put the best one at the front
	delete(NewStates, BestState, NewStatesWithoutBest),
	append([BestState], NewStatesWithoutBest, NewStatesWithBestAtFirst),
	aStar(IndivList, NewStatesWithBestAtFirst, Result).
