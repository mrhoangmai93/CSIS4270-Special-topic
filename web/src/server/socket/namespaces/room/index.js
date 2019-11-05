
const {
    userReadyToPlay,
} = require('./actions');

module.exports = (io) => io.of('/room').on('connection', function(socket) {
    // Join a chatroom
    socket.on('join', async (roomId) => {
        await userReadyToPlay(io, 'test', 'test');
        console.log(roomId);
    });
    // When a socket exits
    socket.on('disconnect', async() => {
        console.log('client disconnected');
    });
});