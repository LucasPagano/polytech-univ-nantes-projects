fo'use strict';
/*
* Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
*/

/*Recréation de la référence circulaire */

var album;
var album2;
var albums;

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

Album.prototype.getTitle = function () {
    return this.title;
};

Artist.prototype.addAlbum = function (album) {
    this.albumsProduits[album.getTitle()] = album;
};

function Album(album) {
    Object.assign(this, album);
    this.artist = Artist.withName(this.artist);
    this.artist.addAlbum(this);
}

album = new Album({
    title: 'L\'album de Jean Michel',
    artist: 'Jean Michel',
    year: 1966,
});

album2 = new Album({
    title: 'L\'album de Jean Michel 2 (besoin de sous)',
    artist: 'Jean Michel',
    year: 1966,
});

albums = {album, album2};

function replacer(key, value) {
    if (key === 'albumsProduits'){
        return Object.keys(value);
    }
    else {
        return value;
    }
}

console.log('===========================');
console.log('Sans le replacer');
console.log(JSON.stringify(albums));


console.log('===========================');
console.log('Avec le replacer');
console.log(JSON.stringify(albums, replacer, 4)  );
