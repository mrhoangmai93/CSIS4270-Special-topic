const mongoose = require('mongoose');

const playerSchema = mongoose.Schema({
    readyToPlay: {
        type: Boolean,
        default: false,
    },
    room: { type: mongoose.Schema.Types.ObjectId, ref: 'GameRoom' },
    finished: {
        type: Boolean,
        default: false,
    },
    currentLevel: {
        type: Number,
        default: 0,
    },
    matchCount: {
        type: Number,
        default: 0,
    },
    socketId: String,
    name: {
        type: String,
        default: 'Guest'
    },
});
playerSchema
    .virtual('roomId')
    .get(function get() {
        return this.room;
    });
playerSchema.methods.toJson = function toJson() {
    return {
        id: this.id,
        readyToPlay: this.readyToPlay,
        room: this.room,
        currentLevel: this.currentLevel,
        matchCount: this.matchCount,
        finished: this.finished,
        socketId: this.socketId,
        name: this.name,
    };
};

playerSchema.methods.setRoom = function setRoom(roomId) {
    this.set('room', roomId);
    return this;
};

playerSchema.methods.ready = function ready(isReady) {
    this.set('readyToPlay', isReady);
    return this;
};

playerSchema.methods.finish = function finish() {
    this.set('finished', true);
    return this;
};

const Player = mongoose.model('RoomPlayer', playerSchema);
module.exports = Player;


