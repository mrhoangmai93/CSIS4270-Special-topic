const Player = require('../models/Player');

module.exports = {
    async getPlayerBySocketIdAndRoom(socketId, room) {
        return Player.findOne({ socketId, room });
    },
};