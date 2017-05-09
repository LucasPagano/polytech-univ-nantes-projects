'use strict';

// import du module Express
var express = require('express');
var app = express();
var data = require('./data/db.json');


app.get('/genres', function (req, res){
    res.set({
        'Content-type': 'text/json',
        'charset': 'utf-8',
    });

    res.send(JSON.stringify(data.genres));
});

app.get('/genre/:genre/artists', function (req, res){
    res.json(data.artists.filter( function (elem){
        return elem.genreId === req.params.genre;
    }));
});

app.get('/artist/:artist/albums', function (req, res){
    res.json(data.albums.filter(function (elem){
        return elem.artistId === req.params.artist;
    }));
});



// export de notre application vers le serveur principal
module.exports = app;
