Promise = require('bluebird');
const app = require('./server');
const socket = require('socket.io');

const server = require('http').Server(app);
const io = socket(server);

const {
    port, env
} = require('./config');
const database = require('./database');

// Connect mongoDB server
database.connect();

// Connect Socket.IO
app.use((req, res, next) => {
    req.io = io;
    next();
});


server.listen(port, () => {
    console.info(`Server started on port ${port} (${env})`);
});

module.exports = server;