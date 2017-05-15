'use strict';

// import du module Express
var express = require('express');
var app = express();
var HttpProxyAgent = require('http-proxy-agent');
var nodefetch = require('node-fetch');
var xpath = require('xpath');
var xmldom = require('xmldom');


// a passer en paramètre de vos appels à fetch pour que node puisse utiliser le proxy
var options = {
    agent: new HttpProxyAgent( 'http://cache.ha.univ-nantes.fr:3128'),
};

app.get('/genres', function (req, res) {
    nodefetch('http://ws.audioscrobbler.com/2.0/?method=tag.getTopTags&api_key=2c08f218f45c6f367a0f4d2b350bbffc', {options})
        .then(function (response) {
            if (response.ok) {
                return response.text();
            }
            else {
                return Promise.reject('Erreur dans l\'url ou lors de la requête');
            }
        })
        .then(function (data) {
            var doc = new xmldom.DOMParser().parseFromString(data, 'text/xml');
            var nodes = xpath.select('//name', doc);
            var genresList = [];
            var promises = [];
            var i;
            for (i = 0; i < 20; i += 1){
                genresList.push(nodes[i].firstChild.data);
            }
            console.log(genresList);

            promises = genresList.map(elem => nodefetch('http://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=' + elem + '&api_key=2c08f218f45c6f367a0f4d2b350bbffc', {options}));

            Promise.all(promises).then(function (values){
                var result = [];
                values = values.map(x => x.text());
                Promise.all(values).then(function (x){
                    var docSum = new xmldom.DOMParser().parseFromString(x.toString(), 'text/xml');
                    var nodesSum = xpath.select('//content', docSum);
                    values.map(function (elem, index){
                        result.push({
                            id: genresList[index],
                            name: genresList[index],
                            description: nodesSum[index].firstChild.data,
                        });
                    });
                    res.json(result);
                });
            });
        })

        .catch(function (error) {
            console.error(error);
        });
});



// export de notre application vers le serveur principal
module.exports = app;
