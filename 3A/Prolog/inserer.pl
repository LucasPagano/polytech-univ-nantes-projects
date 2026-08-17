inserer(A,L,[A|L]).

inserer(A,[E|L],[E|AL]):- inserer(A,L,AL).