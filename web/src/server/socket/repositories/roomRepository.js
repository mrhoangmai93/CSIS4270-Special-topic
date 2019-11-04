const Room = require('../models/Room');
const Player = require('../models/Player');

module.exports = {
    async getRoomByCode(code) {
        return Room
            .findOne({code})
            .populate(['players']);
    },
};