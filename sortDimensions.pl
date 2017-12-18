set(Elem, FromList, ToList, NewFromList, NewToList):-
	member(Elem, FromList),
	delete(FromList, Elem, NewFromList),
	append([Elem], ToList, NewToList).

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
	print("AreaIndiv : "), print(AreaIndiv), nl,
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
