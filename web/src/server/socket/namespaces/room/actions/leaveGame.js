const roomRepository = require('../../../repositories/roomRepository');
const playerRepository = require('../../../repositories/playerRepository');

module.exports = async (io, socket, room) => {
    const newRoom = await roomRepository.getRoomByCode(room.code);
    if(!newRoom) return;
    const {players} = newRoom;
    if(players.length === 1) {
        await roomRepository.removeRoomByCode(newRoom.code);
    } else {
        const player = await playerRepository.getPlayerBySocketIdAndRoom(socket.id, newRoom.id);
        if(player) await room.removePlayer(player.id);
    }
    await playerRepository.removePlayerBySocketIdAndRoom(socket.id, room.id);
};