stir(N,K,1):-
  K =< N,
  N>=1,
  N=K;K=1.

stir(N,K,R):-
  K =< N,
  N>=1,
  N1 is N-1,
  K1 is K-1,
  stir(N1,K1,R1),
  stir(N1,K,R2),
  R is R1 + K*R2.
