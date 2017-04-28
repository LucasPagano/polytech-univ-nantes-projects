'use strict';
/*
 * Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
 */

var liste;
var tabAlbums;
var JSONalbums;
var tabTitles;
var monObjFinal;

var Album = require('./Album.js');

if (!Object.entries) {
    require('object.entries').shim();
}

/* Question 1*/
JSONalbums = require('./albums.json');
liste = Object.entries(JSONalbums);

console.log('===========================');
console.log('======> Question 1 <=======');
console.log('===========================');
console.log(liste);

/* Question 2*/
tabAlbums = liste.map(function (array) {
    array[1] = new Album(array[1]);
    return array;
});

console.log('===========================');
console.log('======> Question 2 <=======');
console.log('===========================');
console.log(tabAlbums);

/*Question 3*/
tabTitles = tabAlbums.map(function (array) {
    var item = {};
    item[array[0]] = array[1];
    return item;
});

console.log('===========================');
console.log('======> Question 3 <=======');
console.log('===========================');
console.log(tabTitles);

/*Question 4*/
monObjFinal = Object.assign.apply(null, tabTitles);

console.log('===========================');
console.log('======> Question 4 <=======');
console.log('===========================');
console.log(monObjFinal);
