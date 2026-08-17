'use strict';
/*
 * Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
 */

var monAlbum;

function Album(album) {
    Object.assign(this, album);
}

Album.prototype.getTitle = function () {
    return this.title;
};

Album.prototype.getArtist = function () {
    return this.artist;
};

Album.prototype.getYear = function () {
    return this.year;
};

monAlbum = new Album({
    title: 'Fresh Cream',
    artist: 'Cream',
    year: 1966,
});

console.log(monAlbum);
console.log(monAlbum.getArtist());
