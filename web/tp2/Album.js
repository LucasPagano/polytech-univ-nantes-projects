'use strict';
/*
 * Authors : ['HUDIER Fabien', 'PAGANO Lucas'];
 */

module.exports = (function () {
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

    return Album;
}());
