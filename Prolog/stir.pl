st(N,N,1).
st(_,1,1).
st(N,K,R):- K < N, K > 1,
	N1 is N-1, K1 is K-1,
	st(N1,K1,R1),
	st(N1,K,R2),
	R is R1 + K*R2.
