'use strict';

var http = require('http');
http.createServer(function requestListener(request, response){
    if(request.method === 'GET'){
        var html = '';
        var tab = request.url.split('?');

        html += ('<!DOCTYPE html>');
        html += ('<html>');
        html += ('<head>');
        html += ('<meta charset=\'UTF-8\'>');
        html += ('<title> Bonjour </title>');
        html += ('</head>');

        html += ('<body>');
        html += ('<p>');
        html += ('Analyse de votre requête:' + '<br>');
        html += ('Vous accedez à l\'url : ' + tab[0]+ '<br>');
        html += ('Les arguments sont : ' + tab[1]);
        html += ('</p>');
        html += ('</body>');
        html += ('</html>');

        response.writeHead(200, {
            'Content-Length': Buffer.byteLength(html),
            'Content-Type': 'text/html' });
        response.write(html, 'UTF-8');


    } else {
        response.writeHead(405);
    }
    response.end();
}).listen(8080);
