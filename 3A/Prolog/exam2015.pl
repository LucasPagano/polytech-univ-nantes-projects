toto(A,X,[A|X]).
toto(A,[X|Y],[X|YY]):-toto(A,Y,YY).

stir(N,K,1):-
  K =< N,
  N>=1,
  N=K;K=1.

stir(N,K,R):-
  K < N,
  N>=1,
  N1 is N-1,
  K1 is K-1,
  stir(N1,K1,R1),
  stir(N1,K,R2),
  R is R1 + K*R2.

bell(N,R):-
	vraiBell(N,N,R).

vraiBell(N,1,R):-
	N>=1,
	stir(N,1,R).

vraiBell(N,K,R):-
	K =< N,
	N>=1,
  K1 is K-1,
	stir(N,K,R1),
	vraiBell(N,K1,R2),
	R is R1 + R2.

inserer(A,[],[A]).

inserer(A,[X|Y],[A,X|L1]):-
  inserer(A,Y,L1).
