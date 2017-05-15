'use strict';

// Auteurs : HUDIER Fabien, PAGANO Lucas

var http = require('http');
http.createServer(function requestListener(request, response){
    response.writeHead(200);
    response.write('The server\'s answer');
    response.end();
}).listen(8080);
