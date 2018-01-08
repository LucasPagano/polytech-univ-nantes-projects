:- begin_tests(tests).

%sortDimensions tests
test(triangleArea):-
	triangleArea(1,1,Area), assertion(float(Area)), assertion(Area == 0.5),
	triangleArea(1,0,Area2), assertion(float(Area2)), assertion(Area2 == 0.0),
	triangleArea(0.5,0.25,Area3), assertion(float(Area3)), assertion(Area3 == 0.0625).

test(checkSize):-
	%if size isn't the same for all individuals, returns no solution
	checkSize([[1,2], [1,2]], L), assertion(L == 2).

test(createFirstState):-
	createFirstState([[1,1]], State), assertion(State == [([], [1,2])]),
	createFirstState([[1,1],[1,1]], State2), assertion(State2 == [([], [1,2])]),
	createFirstState([[1,1,1],[1,1,1]], State3), assertion(State3 == [([], [1,2,3])]).

test(generateIndiv):-
	once(generateIndiv(2, Indiv)), length(Indiv, NbDimensions), assertion(NbDimensions == 2).

test(generateIndivList):-
	once(generateIndivList(5,2, IndivList)),
	length(IndivList, NumberIndivs), assertion(NumberIndivs == 5).

test(computeAreaIndiv):-
	%Standard test
	once(computeAreaIndiv([1,1,1,1], [1,2,3,4], Area)),
	assertion(float(Area)),
	assertion(Area == 2.0),

	%If there are 0's at some dimensions, we end up with no area
	once(computeAreaIndiv([1,0,1,0], [1,2,3,4], Area2)),
	assertion(float(Area2)),
	assertion(Area2 == 0.0),

	%But if we change permutation, we get an area
	once(computeAreaIndiv([1,0,1,0], [1,3,2,4], Area3)),
	assertion(float(Area3)),
	assertion(Area3 == 0.5).

test(computeAreaIndivWithoutLast):-

	%Without the last axis, if permutation isn't the same size as indiv
	once(computeAreaIndivWithoutLast([1,1,1,1], [1,2,3], Area)),
	assertion(float(Area)),
	assertion(Area == 1.0).

test(computeAreaList):-
	%If permutation is same size as list, last triangle counts
	computeAreaList([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,2,3,4], Area-[1,2,3,4]),
	assertion(float(Area)),
	assertion(Area == 3.5),

	%Area depends on permutation
	computeAreaList([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,3,2,4], Area2-[1,3,2,4]),
	assertion(float(Area2)),
	assertion(Area2 == 4.0),

	%If permutation isn't the same size as individuals, it doesn't count the last triangle
	computeAreaList([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,2,3], Area3-[1,2,3]),
	assertion(float(Area3)),
	assertion(Area3==1.75).

test(sumOverIndivs):-

	sumOverIndivs([[1,0], [1,0], [0.5, 0.25]], 1, Dim1-1),
	sumOverIndivs([[1,0], [1,0], [0.5, 0.25]], 2, Dim2-2),
	assertion(float(Dim1)), assertion(float(Dim2)),
	assertion(Dim1 == 2.5), assertion(Dim2 == 0.25).

test(orderForHeuristic):-
	orderForHeuristic([[1,1,1,1], [0,1,0,1], [0.5,1,0.5,1], [0,0.5,1,0.5]], [1,2,3,4], ToPlaceOrdered),
	assertion(ToPlaceOrdered == [1.5-1, 2.5-3, 3.5-2, 3.5-4]).


%scoreGrille tests
test(cases4):-
	%Standard case
	cases4([1,1],Cases, 4),
	assertion(Cases = [[0,1],[2,1],[1,0],[1,2]]),

	%Border cases
	cases4([3,3],Cases2, 4),
	assertion(Cases2 = [[2,3],[3,2]]),

	cases4([0,0],Cases3, 4),
	assertion(Cases3 = [[1,0],[0,1]]),

	%Out of range
	cases4([0,0],Cases4, 1),
	assertion(Cases4 = []).

test(cases8):-
	%Standard case
	cases8([1,1],Cases, 4),
	assertion(Cases = [[0,0],[0,2],[2,0],[2,2]]),

	%Border cases
	cases8([3,3],Cases2, 4),
	assertion(Cases2 = [[2,2]]),

	cases8([0,0],Cases3, 4),
	assertion(Cases3 = [[1,1]]),

	%Out of range
	cases8([0,0],Cases4, 1),
	assertion(Cases4 = []).

test(voisins4):-
	IndivList = [[1,1,1,1], [1,1,1,0], [1,1,0,1], [1,0,1,1], [0,1,1,1], [0,1,0,1], [0,0.5,1,0.5]],
	once(voisins4([1,1,1,1], IndivList, Voisins)),
	assertion(Voisins == [[0,1,1,1],[1,0,1,1],[1,1,0,1],[1,1,1,0]]),

	%Duplicate elements correctly handled
	IndivList2 = [[1,1,1,1], [1,1,1,1], [1,1,1,1], [1,1,1,1], [0,1,1,1], [0,1,0,1], [0,0.5,1,0.5]],
	once(voisins4([1,1,1,1], IndivList2, Voisins2)),
	assertion(Voisins2 == [[1,1,1,1],[1,1,1,1],[1,1,1,1],[0,1,1,1]]),

  %Fails if not enough neighbours
	IndivList3 = [[0,1,0,1], [0,0.5,1,0.5]],
	\+ once(voisins4([0, 0.5, 1, 0.5], IndivList3, _)).

test(voisins8):-
  IndivList = [[1,1,1,1], [1,1,1,0], [1,1,0,1], [1,0,1,1], [0,1,1,1], [0,0,0,0], [0,0.5,1,0.5], [0,0,0,1], [0,0.5,1,0.5]],
  once(voisins8([1,1,1,1], IndivList, Voisins)),
  assertion(Voisins ==  [[0,0.5,1,0.5],[0,0.5,1,0.5],[0,0,0,1],[0,0,0,0]]),

  %Duplicate elements correctly handled
  IndivList2 = [[1,1,1,1], [1,1,1,1], [1,1,1,1], [1,1,1,1], [0,1,1,1], [0,1,0,1], [0,0.5,1,0.5],  [0,0.5,1,0.5], [0,0.5,1,0.5]],
  once(voisins8( [0,0.5,1,0.5], IndivList2, Voisins2)),
  assertion(Voisins2 == [[1,1,1,1],[1,1,1,1],[1,1,1,1],[1,1,1,1]]),

  %Fails if not enough neighbours
  IndivList3 = [[0,1,0,1], [0,0.5,1,0.5]],
  \+ once(voisins8([0, 0.5, 1, 0.5], IndivList3, _)).

:- end_tests(tests).
