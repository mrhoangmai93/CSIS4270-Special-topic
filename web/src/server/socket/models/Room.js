/* eslint-disable no-invalid-this */
const mongoose = require('mongoose');
const statuses = {
    IN_PROCESS: 'IN_PROCESS',
    PAUSE: 'PAUSE',
    WAIT_PLAYERS: 'WAIT_PLAYERS',
    FINISHED: 'FINISHED',
};

const RoomSchema = new mongoose.Schema({
    code: { type: String, required: true, unique: true },
    players: [{ type: mongoose.Schema.Types.ObjectId, ref: 'RoomPlayer' }],
    status: {
        type: String,
        enum: Object.values(statuses),
        default: statuses.WAIT_PLAYERS,
    },
});

const model = mongoose.model('GameRoom', RoomSchema);


module.exports = model;