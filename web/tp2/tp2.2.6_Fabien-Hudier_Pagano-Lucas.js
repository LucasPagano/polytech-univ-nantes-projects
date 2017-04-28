'use strict';
/*
 * Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
 */

var album;
var album2;
var albums;
/* Question 1 */
Artist.list = [];

Album.prototype.getTitle = function () {
    return this.title;
};

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
    this.albumsProduits[album.getTitle()] = album;
};

/*Question 3*/
function Album(album) {
    Object.assign(this, album);
    this.artist = Artist.withName(this.artist);
    this.artist.addAlbum(this);
}

album = new Album({
    title: 'L\'album de Jean Michel',
    artist: 'Jean Michel',
    year: 2012,
});

album2 = new Album({
    title: 'Sans titre',
    artist: 'Sans nom',
    year: 0,
});

albums = {album, album2};

console.log('===========================');
console.log('Albums');
console.log('===========================');
console.log(albums);

console.log('===========================');
console.log('La liste des artistes');
console.log('===========================');
console.log(Artist.list);
