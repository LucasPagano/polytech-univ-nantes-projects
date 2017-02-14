puiss(_,0,1).
puiss(X,Y,R):- 
	Y>0,
	Y1 is Y-1,
	puiss(X,Y1,R1),
	R is X * R1.
