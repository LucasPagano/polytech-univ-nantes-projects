'use strict';
/*
 * Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
 */
/*

1. Expliquez la nature du problème.
i etant en variable globale il est modifié en même temps dans findMinIndex et sortTable -> sortTable échoue

2. Expliquez de quelle manière très simple on aurait pu détecter le problème.
En rajoutant 'use strict' en haut du fichier.

3. Corrigez ce code défaillant (le code corrigé doit être le plus proche possible du code initial).
*/

var t = [0, 3, 2, 5];
console.log('Plus petite valeur ' +
    t[findMinIndex(t, 0, t.length)]);
console.log('Plus petite valeur parmi les trois dernières ' +
    t[findMinIndex(t, 1, t.length)]);
sortTable(t);
console.log(t);

/**
 * return the index of the minimal value in the array 't' from index
 * 'from' to index 'to' (excluded)
 */
function findMinIndex(t, from, to) {
    var i, j = from;
    for (i = from + 1; i < to; i += 1) {
        if (t[j] > t[i]) {
            j = i;
        }
    }

    return j;
}

/**
 * sort the table 't'
 */
function sortTable(t) {
    var j, s, i;
    for (i = 0; i < t.length - 1; i += 1) {
        // Find the index of the minimal value in the unsorted part of
        // the array
        j = findMinIndex(t, i, t.length);
        // Swap the ith minimal value
        s = t[j];
        t[j] = t[i];
        t[i] = s;
    }
}
