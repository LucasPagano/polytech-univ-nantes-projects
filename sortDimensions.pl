%How to use : main(NbIndiv, NbDimensions, Result).

main(NbIndiv, NbDimensions, R):-
	generateIndivList(NbIndiv, NbDimensions, Individuals),
	createFirstState(Individuals, InitialState),
	nth1(1, InitialState, PrintInitialState),
	write("Individuals = "), write(Individuals), nl,
	write("Initial state : ("), write(PrintInitialState), write(")"), nl,
	aStar(Individuals, InitialState, R).

%Predicate used to create first state from indiv list
createFirstState([IndivListHead|_], [([], ToPlace)]):-
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
*Area = 3.5;
*
*computeAreaList([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,3,2,4], Area).
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

%Sum the values of the n'th dimension over all individuals
sumOverIndivs([], Dimension, 0-Dimension).
sumOverIndivs([FirstIndiv|OtherIndivs], Dimension, R-Dimension):-
	nth1(Dimension, FirstIndiv, Result),
	sumOverIndivs(OtherIndivs, Dimension, Result2-_),
	R is Result + Result2.

multForAllIndivs([], _, _, 0).
multForAllIndivs([FirstIndiv|OtherIndivs], FirstDimension, SecondDimension, R):-
	nth1(FirstDimension, FirstIndiv, FirstDimensionValue),
	nth1(SecondDimension, FirstIndiv, SecondDimensionValue),
	% must be larger than the real one, so we use 1 instead of 1/2
	AreaIndiv is FirstDimensionValue * SecondDimensionValue,
	multForAllIndivs(OtherIndivs, FirstDimension, SecondDimension, AreaList),
	R is AreaIndiv + AreaList.

%As the heuristic, we choose the product two by two of the remaining dimensions, ordered by max of the sum of the values of all individuals on them
%If there's only one dimension remaining, we take half of the sum of its value over all individuals
heuristic(IndivList, [_-FirstDimension|Tail], Result):-
	%Tail has at least two dimensions, terminal case is one
	length(Tail, L), L >= 2,
	nth1(1, Tail, _-SecondDimension),
	multForAllIndivs(IndivList, FirstDimension, SecondDimension, R1),
	heuristic(IndivList, Tail, R2),
	Result is R1 + R2.

heuristic(IndivList, [_-FirstDimension, _-SecondDimension], Result):-
	multForAllIndivs(IndivList, FirstDimension, SecondDimension, Result).

heuristic(_, [DimensionValue-_], Result):-
	%The value of the dimension is already the sum over all the individuals, so we only have to halve it
	Result is 1/2 * DimensionValue.

%When everything is placed, heuristic is 0 by definition
heuristic(_, [], 0).

%Predicate which forms a pair containing a state and its value, from a state and a list of individuals, to use with maplist
%In the A* algorithm, this is f(State) = g(State) + h(State)
%TODO : find out why f = -(GValue + 1/FValue) works so much better
stateValue(IndivList, (Placed, ToPlace), R-(Placed, ToPlace)):-
	write("		Child state : ("), write((Placed, ToPlace)), write(")"), nl,
	%g(State)
	computeAreaList(IndivList, Placed, GValue),
	%Now compute h(State)
	%order the dimensions
	maplist(sumOverIndivs(IndivList), ToPlace, ToPlaceWithValues),
	% order is from min to max but we don't care, we just want them ordered
	keysort(ToPlaceWithValues,  ToPlaceOrdered),
	%h(state)
	heuristic(IndivList, ToPlaceOrdered, FValue),
	R is (GValue + FValue),
	write("		GValue : "), write(GValue) , write(" FValue : "), write(FValue), nl,
	write("		F(State) = GValue + FValue = "), write(R), nl.

final(_, ToPlace):-
	ToPlace = [].
initial(Placed, _):-
	Placed = [].

%TODO : f should be max(f(state), f(state_parent))
aStar(_, [_-(HeadStatePlaced, HeadStateToPlace)|_], HeadStatePlaced):-
  final(HeadStatePlaced, HeadStateToPlace).

	%At first, two dimensions are chosen so area has a meaning and can be computed
aStar(IndivList, [(HeadStatePlaced, HeadStateToPlace)|_], Result):-
	initial(HeadStatePlaced, HeadStateToPlace),
	%Arbitrary choose the first two dimensions, here we take the first ones, we might want to change this
	nth1(1, HeadStateToPlace, Dimension1),
	nth1(2, HeadStateToPlace, Dimension2),
	set(Dimension1, HeadStateToPlace, HeadStatePlaced, NewToPlace, NewPlaced),
	set(Dimension2, NewToPlace, NewPlaced, NewToPlace2, NewPlaced2),

	write("	Starting state : ("), write((NewPlaced2, NewToPlace2)), write(")"), nl,

	%then compute the values and order them to be able to call the real aStar predicate
	findall(P, (set(_, NewToPlace2, NewPlaced2, NewFromList, NewToList), P = (NewToList, NewFromList)), Children),
	maplist(stateValue(IndivList), Children, StateValues),

	max_member(BestState, StateValues),
	delete(StateValues, BestState, StateValuesWithoutBest),
	append([BestState], StateValuesWithoutBest, StateValuesBestFirstPlace),
	aStar(IndivList, StateValuesBestFirstPlace, Result).

aStar(IndivList, [_-(HeadStatePlaced, HeadStateToPlace)|TailStates], Result):-
	not(final(HeadStatePlaced, HeadStateToPlace)),
	not(initial(HeadStatePlaced, HeadStateToPlace)),
	write("	Starting state : ("), write((HeadStatePlaced, HeadStateToPlace)), write(")"), nl,
	%find HeadState's children, as it's the best state
	findall(P, (set(_, HeadStateToPlace, HeadStatePlaced, NewFromList, NewToList), P = (NewToList, NewFromList)), Children),
	%give each children its value
	maplist(stateValue(IndivList), Children, ChildrenValues),
	%Append them to the states
	append(TailStates, ChildrenValues, NewStates),
	%get the max value
	max_member(BestState, NewStates),
	%add it at the beginning
	delete(NewStates, BestState, NewStatesWithoutBest),
	append([BestState], NewStatesWithoutBest, NewStatesBestFirstPlace),
	aStar(IndivList, NewStatesBestFirstPlace, Result).
