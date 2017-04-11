'use strict';
/*
 * Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
 */

var album;
/* Question 1 */
Artist.list = [];

function Artist(nom) {
    this.nom = nom;
    this.albumsProduits = [];
    Artist.list.push(this);
}

Artist.withName = function (name) {
    var artist = null;
    Artist.list.forEach(function (element) {
        if (element.nom === name) {
            artist = element;
        }
    });

    if (!artist) {
        artist = new Artist(name);
    }

    return artist;
};

/*Question 2*/
Artist.prototype.addAlbum = function (album) {
    this.albumsProduits.push(album);
};

/*Question 3*/
function Album(album) {
    Object.assign(this, album);
    this.artist = Artist.withName(this.artist);
}

album = new Album({
    title: 'Fresh Cream',
    artist: 'Jean Michel',
    year: 1966,
});

console.log(album);

Artist.withName('Jean Michel').addAlbum(album);
console.log(album);
