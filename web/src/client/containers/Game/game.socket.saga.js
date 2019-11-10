import {
    all, put, call, takeLatest, takeEvery, take, select, fork, cancel
} from 'redux-saga/effects';
import {eventChannel, END} from 'redux-saga';
import * as GAME_ACTION from './game.action';
import openSocket from 'socket.io-client'
import {SOCKET_URL} from '../../config/constants';
import {EXIT} from "./game.action";
import {SET_GAME_STATE} from "./game.action";

function connect() {
    const socket = openSocket(`${SOCKET_URL}/room`);

    return new Promise(resolve => {
        socket.on('connect', () => {
            resolve(socket)
        })
    })
}

function subscribe(socket) {
    return eventChannel(emit => {
        socket.on('action', data => {
            switch (data.type) {
                case 'SERVER_CREATE_GAME_SUCCESS':
                    emit(GAME_ACTION.socketCreateGameSuccess(data.payload));
                    break;
                case 'SERVER_JOIN_GAME_SUCCESS':
                    emit(GAME_ACTION.socketJoinGameSuccess(data.payload));
                    break;
                case 'SERVER_ENOUGH_PLAYERS':
                    emit(GAME_ACTION.socketEnoughPlayers(data.payload));
                    emit(GAME_ACTION.loadGameData());
                    break;
                case 'SERVER_OPPONENT_READY':
                    emit(GAME_ACTION.opponentReady());
                    break;
                case 'SERVER_START_GAME':
                    emit(GAME_ACTION.gameStart());
                    break;
                case 'SERVER_GAME_ERROR':
                    emit(GAME_ACTION.gameError(data.payload));
                default:
                    break;

            }
        });

        socket.on('disconnect', e => {
        });
        return () => {
        };
    });
}

function* read(socket) {
    const channel = yield call(subscribe, socket);

    while (true) {
        const action = yield take(channel);
        yield put(action)
    }
}

function* createGame(socket) {
    while (true) {
        let data;
        data = yield take(`${GAME_ACTION.SOCKET_CREATE_GAME}`);
        socket.emit('create', data.payload);
    }
}

function* joinGame(socket) {
    while (true) {
        const data = yield take(`${GAME_ACTION.SOCKET_JOIN_GAME}`);
        socket.emit('join', data.payload);
    }
}

function* changeGameStatus(socket) {
    while (true) {
        const data = yield take(`${GAME_ACTION.SET_GAME_STATE}`);
        socket.emit('set.status', data.payload);
    }
}

function* playerReady(socket) {
    while (true) {
        const data = yield take(`${GAME_ACTION.MULTI_PLAYER_READY}`);
        socket.emit('player.ready', data.payload);
    }
}

// * * * * * * * * * * * * *  //
// * MAIN SOCKET LISTENERS * //
// * * * * * * * * * * * * *  //
function* socketWatch(socket) {
    yield fork(read, socket);
    yield fork(changeGameStatus, socket);
    yield fork(createGame, socket);
    yield fork(joinGame, socket);
    yield fork(playerReady, socket);
}

function* startup() {
    // let { user: { token, info } } = yield select(state => state.game.getIn(['gameData', payload, 'totalMatches']));

    const socket = yield call(connect);
    const task = yield fork(socketWatch, socket);
    // yield put({type: GAME_ACTION.SOCKET_START_GAME});

    // If exit game, remove socket loop
    yield take(GAME_ACTION.EXIT);
    yield cancel(task);
    socket.disconnect();
}


// * * * * * * * * * * * * *  //
// *     Saga functions    *  //
// * * * * * * * * * * * * *  //


export default function* watcher() {
    yield all([
        yield takeLatest(GAME_ACTION.GO_MULTI_PLAYERS, startup),
    ]);
}
