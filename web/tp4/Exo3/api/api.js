'use strict';

// import du module Express
var express = require('express');
var app = express();
var HttpProxyAgent = require( 'http-proxy-agent' );
var nodefetch = require('node-fetch');
var urlencore = require('urlencode');
var xpath = require('xpath');
var xmldom = require('xmldom');


// a passer en paramètre de vos appels à fetch pour que node puisse utiliser le proxy
var options = {
    agent: new HttpProxyAgent( 'http://cache.ha.univ-nantes.fr:3128'),
};

app.get('/genre', function (req, res){
    nodefetch.fetch('http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=2c08f218f45c6f367a0f4d2b350bbffc')
        .then (function (response){
            if (response.ok) {
                return response.text();
            }
            else {
                return Promise.reject('Erreur dans l\'url ou lors de la, requete');
            }
        })
        .then(function (data){
            xmldom.parseFromString(data, "text/xml");
    })
        .catch( function(error){
            console.error(error);
        })
});



// export de notre application vers le serveur principal
module.exports = app;
