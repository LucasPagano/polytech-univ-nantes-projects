toto(A,X,[A|X]).
toto(A,[X|Y],[X|YY]):-toto(A,Y,YY).

stir(N,K,1):-
  K =< N,
  N>=1,
  N=K;K=1.
