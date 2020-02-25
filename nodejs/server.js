const http = require('http');
const app = require('./app');
const port = 3009;
const server = http.createServer(app);
server.listen(port, ()=>{
    console.log("running on port "+port);
});