/*
 Lucas Pagano    Mon Jan 8 23:14:12 2018 +0100   added postconditions and preconditions
 Lucas Pagano    Mon Jan 8 19:39:29 2018 +0100   added possibility to submit list to main and mainWithBest
 Lucas Pagano    Mon Jan 8 17:56:17 2018 +0100   moved tests to tests file
 Lucas Pagano    Sun Jan 7 23:29:09 2018 +0100   added unit tests and checks in predicates
 Lucas Pagano    Mon Jan 1 16:11:05 2018 +0100   changed the dimension to pick in ToPlaceOrdered for last triangle in StateValue
 Lucas Pagano    Mon Jan 1 16:06:35 2018 +0100   added unit tests
 Lucas Pagano    Sun Dec 31 05:10:25 2017 +0100  added mainWithBest, which explores all solutions and gives the best one, only use to debug
 Lucas Pagano    Sun Dec 31 05:09:16 2017 +0100  changed heuristic to take last triangle into account and changed way of setting first dimension
 Lucas Pagano    Wed Dec 27 02:13:33 2017 +0100  change A* initialzation thanks to fix, now we only need to choose one dimension to start. Changed main to print area as well
 Lucas Pagano    Wed Dec 27 02:03:09 2017 +0100  fix area computation to take last triangle into account only when the permutation is the same same as the individuals
 Lucas Pagano    Wed Dec 27 00:19:17 2017 +0100  used max_member insead of keysort
 Lucas Pagano    Wed Dec 27 00:05:03 2017 +0100  added heuristic in A* and main
 Lucas Pagano    Tue Dec 26 00:16:04 2017 +0100  finished A* algorithm and added initialState generation
 Lucas Pagano    Mon Dec 18 18:04:44 2017 +0100  add compute area and generate indiv lists functions
*/

%Notable fact : individuals are generated normalized, so we don't divide by the dimension max in the area computation

/**
* NbIndiv : int
* NbDimensions : int
*
* Permutation : list of ints
* Area : float
*/
main(NbIndiv, NbDimensions, (Permutation, Area)):-
	once(generateIndivList(NbIndiv, NbDimensions, Individuals)),
	createFirstState(Individuals, InitialState),
	nth1(1, InitialState, PrintInitialState),
	write("Individuals = "), write(Individuals), nl,
	write("Initial state : ("), write(PrintInitialState), write(")"), nl,
	once(aStar(Individuals, InitialState, Permutation)),
	computeAreaList(Individuals, Permutation, Area-_).

/**
* Individuals : list of lists of ints
*
* Permutation : list of ints
* Area : float
*/
main(Individuals, (Permutation, Area)):-
	createFirstState(Individuals, InitialState),
	nth1(1, InitialState, PrintInitialState),
	write("Individuals = "), write(Individuals), nl,
	write("Initial state : ("), write(PrintInitialState), write(")"), nl,
	once(aStar(Individuals, InitialState, Permutation)),
	computeAreaList(Individuals, Permutation, Area-_).


/**
* NbIndiv : int
* NbDimensions : int
*
* Permutation : list of ints
* Area : float
*/
%Only use this for debug as it computes all permutations
mainWithBest(NbIndiv, NbDimensions, (Permutation, Area)):-
	%Can't call main here because the indivuals generation is random, so we must use the same
	once(generateIndivList(NbIndiv, NbDimensions, Individuals)),
	createFirstState(Individuals, InitialState),
	nth1(1, InitialState, PrintInitialState),
	write("Individuals = "), write(Individuals), nl,
	write("Initial state : ("), write(PrintInitialState), write(")"), nl,
	once(aStar(Individuals, InitialState, Permutation)),
	computeAreaList(Individuals, Permutation, Area-_),
	%print the optimal permutation with the max reachable area
	createFirstState(Individuals, [([], ToPlace)]),
	findall(X, permutation(ToPlace, X), Permutations),
	once(maplist(computeAreaList(Individuals), Permutations, PermutationsWithValues)),
	max_member(Value-MaxPermutation, PermutationsWithValues),
	write("Max permutation : "), write(MaxPermutation), write(" with area : "), writeln(Value).

/**
* Individuals : list of lists of floats
*
* Permutation : list of ints
* Area : float
*/
mainWithBest(Individuals, (Permutation, Area)):-
	createFirstState(Individuals, InitialState),
	nth1(1, InitialState, PrintInitialState),
	write("Individuals = "), write(Individuals), nl,
	write("Initial state : ("), write(PrintInitialState), write(")"), nl,
	once(aStar(Individuals, InitialState, Permutation)),
	computeAreaList(Individuals, Permutation, Area-_),
	%print the optimal permutation with the max reachable area
	createFirstState(Individuals, [([], ToPlace)]),
	findall(X, permutation(ToPlace, X), Permutations),
	once(maplist(computeAreaList(Individuals), Permutations, PermutationsWithValues)),
	max_member(Value-MaxPermutation, PermutationsWithValues),
	write("Max permutation : "), write(MaxPermutation), write(" with area : "), writeln(Value).

%Predicate used to create first state from indiv list
createFirstState([IndivListHead|IndivListTail], [([], ToPlace)]):-
	checkSize([IndivListHead|IndivListTail], L),
	numlist(1, L, ToPlace).

%Predicate used to check that size of all individuals is the same
checkSize([], _).
checkSize([IndivListHead|IndivListTail], L):-
	length(IndivListHead, L),
	checkSize(IndivListTail, L).

/**
* NbIndiv : int
* NbDimensions : int
*
* IndivList : list of lists of floats
*/
generateIndivList(0, _, []).
generateIndivList(NbIndiv, NbDimensions, IndivList):-
	NbIndiv > 0, NbDimensions > 0,
	NbIndiv1 is NbIndiv - 1,
	generateIndiv(NbDimensions, Indiv),
	IndivList = [Indiv|T],
	generateIndivList(NbIndiv1, NbDimensions, T).

/**
* NbIndiv : int
* NbDimensions : int
*
* Indiv : list of floats
*/
generateIndiv(NbDimensions, Indiv):-
	length(Indiv, NbDimensions),
	maplist(random(0.0, 1.0), Indiv).

/**
* IndivList : list of lists of floats
* Permutation : list of ints
*
* Area : float
*/
computeAreaList([], Permutation, 0-Permutation).
computeAreaList([FirstIndiv|TailIndiv], Permutation, Area-Permutation):-
	%if permutation is same size as individuals, we must count the last triangle, else we shouldn't
	length(FirstIndiv, Li), length(Permutation, Lp),
	(Li == Lp -> once(computeAreaIndiv(FirstIndiv, Permutation, AreaIndiv)) ; once(computeAreaIndivWithoutLast(FirstIndiv, Permutation, AreaIndiv))),
	computeAreaList(TailIndiv, Permutation, AreaList-_),
	Area is AreaList + AreaIndiv.

/**
* Indiv : list of floats
* [FirstPi, SecondPi|TailPi] : permutation, list of ints
*
* Area : float
*/
%Not counting the triangle between last dimension and first one
computeAreaIndivWithoutLast(_, [], 0).
computeAreaIndivWithoutLast(Indiv, [FirstPi, SecondPi|TailPi], Area):-
	length(TailPi, X), X > 0,
	nth1(FirstPi, Indiv, Dim1),
	nth1(SecondPi, Indiv, Dim2),
	triangleArea(Dim1, Dim2, Triangle),
	computeAreaIndivWithoutLast(Indiv, [SecondPi|TailPi], AreaOfTail),
	Area is Triangle + AreaOfTail.

/**
* Indiv : list of floats
* [FirstPi, SecondPi] : list of ints, of size 2
*
* Area : float
*/
computeAreaIndivWithoutLast(Indiv, [FirstPi, SecondPi], Area):-
	nth1(FirstPi, Indiv, Dim1),
	nth1(SecondPi, Indiv, Dim2),
	triangleArea(Dim1, Dim2, Area).

/**
* Indiv : list of floats
* [FirstPi, SecondPi|TailPi] : permutation, list of ints
*
* Area : float
*/
%Counting the triangle between last dimension and first one
computeAreaIndiv(Indiv, [FirstPi, SecondPi|TailPi], Area):-
	nth1(FirstPi, Indiv, ValueOfFirst),
	%We have to remember the value of the first one for the last triangle
	hiddenComputeAreaIndiv(Indiv, [FirstPi, SecondPi|TailPi], ValueOfFirst, Area).

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

/**
* Dimension1 : float
* Dimension2 : float
*
* Area : float
*/
triangleArea(Dimension1, Dimension2, Area):-
	Dimension1 =< 1, Dimension1 >=0,
	Dimension2 =< 1, Dimension2 >=0,
	Area is 0.5*Dimension1*Dimension2.

/**
* Elem : element member of FromList
* FromList : list which contains Elem
* ToList : list
*
* NewFromList : Fromlist without Elem
* NewToList : ToList with Elem at the end
*/
set(Elem, FromList, ToList, NewFromList, NewToList):-
	member(Elem, FromList),
	select(Elem, FromList, NewFromList),
	%We put elem in second place because we want to append at end of list
	append(ToList, [Elem], NewToList).

/**
* [FirstIndiv|OtherIndivs] : list of lists of floats
* Dimension : int
*
* R : float
*/
%Sum the values of the n'th dimension over all individuals
sumOverIndivs([], Dimension, 0-Dimension).
sumOverIndivs([FirstIndiv|OtherIndivs], Dimension, R-Dimension):-
	nth1(Dimension, FirstIndiv, Result),
	sumOverIndivs(OtherIndivs, Dimension, Result2-_),
	R is Result + Result2.

/**
* [FirstIndiv|OtherIndivs] : list of lists of floats
* FirstDimension : int
* SecondDimension : int
*
* R : float
*/
%Sum of triangles between FirstDimension and Second of all individuals
multForAllIndivs([], _, _, 0).
multForAllIndivs([FirstIndiv|OtherIndivs], FirstDimension, SecondDimension, R):-
	nth1(FirstDimension, FirstIndiv, FirstDimensionValue),
	nth1(SecondDimension, FirstIndiv, SecondDimensionValue),
	triangleArea(FirstDimensionValue, SecondDimensionValue, AreaIndiv),
	multForAllIndivs(OtherIndivs, FirstDimension, SecondDimension, AreaList),
	R is AreaIndiv + AreaList.

%As the heuristic, we choose the product two by two of the remaining dimensions, ordered by max of the sum of the values of all individuals on them
heuristic(IndivList, [_-FirstDimension|Tail], Result):-
	%Tail has at least two dimensions, terminal case is one
	length(Tail, L), L >= 2,
	nth1(1, Tail, _-SecondDimension),
	multForAllIndivs(IndivList, FirstDimension, SecondDimension, R1),
	heuristic(IndivList, Tail, R2),
	Result is R1 + R2.

heuristic(IndivList, [_-FirstDimension, _-SecondDimension], Result):-
	multForAllIndivs(IndivList, FirstDimension, SecondDimension, Result).

%If there's only one dimension remaining, we don't give anything, since we add the last triangle in StateValue
heuristic(_, [_-_], 0).

%When everything is placed, heuristic is 0 by definition
heuristic(_, [], 0).

orderForHeuristic(IndivList, ToPlace, ToPlaceOrdered):-
	maplist(sumOverIndivs(IndivList), ToPlace, ToPlaceWithValues),
	keysort(ToPlaceWithValues,  ToPlaceOrdered).

%Predicate which forms a pair containing a state and its value, from a state and a list of individuals, to use with maplist
%In the A* algorithm, this is f(State) = g(State) + h(State)
stateValue(IndivList, (Placed, ToPlace), R-(Placed, ToPlace)):-
	write("		Child state : ("), write((Placed, ToPlace)), writeln(")"),
	%g(State)
	computeAreaList(IndivList, Placed, GValue-_),

	orderForHeuristic(IndivList, ToPlace, ToPlaceOrdered),
	%h(state)
	heuristic(IndivList, ToPlaceOrdered, HeuristicValue),
	%add the triangle between last of ToPlaceOrdered and first Placed
	%We only do it if there's a remaining dimension to place
	length(ToPlace, L),
	(L < 1  -> LastTriangle is 0 ; nth1(1, Placed, FirstDimension),
																 %the order is from min to max, so we take the first one, since it's the one to end up in last if we pick them by value
																 nth1(1, ToPlaceOrdered, _-LastDimension),
																 multForAllIndivs(IndivList, FirstDimension, LastDimension, LastTriangle)),
  FValue is LastTriangle + HeuristicValue,
	R is GValue + FValue,
	write("		GValue : "), write(GValue) , write(" FValue : "), writeln(FValue),
	write("		F(State) = GValue + FValue = "), writeln(R).

final(_, ToPlace):-
	ToPlace = [].
initial(Placed, _):-
	Placed = [].

aStar(_, [_-(HeadStatePlaced, HeadStateToPlace)|_], HeadStatePlaced):-
  final(HeadStatePlaced, HeadStateToPlace).

aStar(IndivList, [(HeadStatePlaced, HeadStateToPlace)|_], Result):-
	initial(HeadStatePlaced, HeadStateToPlace),
	%Choose the first dimension to be the one which maximizes the sum of its values over all individuals
	maplist(sumOverIndivs(IndivList), HeadStateToPlace, DimensionValues),
	max_member(_-Dimension, DimensionValues),
	set(Dimension, HeadStateToPlace, HeadStatePlaced, NewToPlace, NewPlaced),
	write("	Starting state : ("), write((NewPlaced, NewToPlace)), writeln(")"),

	%then compute the values and order them to be able to call the real aStar predicate
	findall(P, (set(_, NewToPlace, NewPlaced, NewFromList, NewToList), P = (NewToList, NewFromList)), Children),
	maplist(stateValue(IndivList), Children, StateValues),
	max_member(BestState, StateValues),
	select(BestState, StateValues, StateValuesWithoutBest),
	append([BestState], StateValuesWithoutBest, StateValuesBestFirstPlace),
	aStar(IndivList, StateValuesBestFirstPlace, Result).

aStar(IndivList, [_-(HeadStatePlaced, HeadStateToPlace)|TailStates], Result):-
	not(final(HeadStatePlaced, HeadStateToPlace)),
	not(initial(HeadStatePlaced, HeadStateToPlace)),
	write("	Starting state : ("), write((HeadStatePlaced, HeadStateToPlace)), writeln(")"),
	%find HeadState's children, as it's the best state
	findall(P, (set(_, HeadStateToPlace, HeadStatePlaced, NewFromList, NewToList), P = (NewToList, NewFromList)), Children),
	%give each children its value
	maplist(stateValue(IndivList), Children, ChildrenValues),
	%Append them to the states
	append(TailStates, ChildrenValues, NewStates),
	%get the max value
	max_member(BestState, NewStates),
	%add it at the beginning
	select(BestState, NewStates, NewStatesWithoutBest),
	append([BestState], NewStatesWithoutBest, NewStatesBestFirstPlace),
	aStar(IndivList, NewStatesBestFirstPlace, Result).
