/*
Lucas Pagano    Mon Jan 8 18:04:46 2018 +0100   added usage instructions
Lucas Pagano    Mon Jan 8 17:56:53 2018 +0100   added main file
*/

:- include('sortDimensions.pl').
:- include('scoreGrille.pl').
:- include('tests.pl').

/*
How to use :

For best permutation problem :
main(NbIndiv, NbDimensions, (Permutation, Area)).
or : main(NbIndiv, NbDimensions, Result).
example : main(4, 8,(Permutation, Area)).
For few individuals and dimensions, can use mainWithBest(NbIndiv, NbDimensions, (Permutation, Area)). to have best solution

For grid problem :
main. gives a demonstration of what the program can do

To run tests :
run_tests.

*/
