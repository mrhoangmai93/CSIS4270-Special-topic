import {
    all, put, call, takeLatest, takeEvery, take, select, fork, cancel
} from 'redux-saga/effects';
import {eventChannel, END} from 'redux-saga';
import * as GAME_ACTION from './game.action';
import openSocket from 'socket.io-client'
import {SOCKET_URL} from '../../config/constants';

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

function* write(socket) {
    while (true) {
        const test = yield take(`${GAME_ACTION.SOCKET_START_GAME}`);
        console.log('vo day');
        socket.emit('join', 'testtterer');
    }
}

function* socketWatch(socket) {
    yield fork(read, socket);
    yield fork(write, socket);
}

function* startup() {
    // let { user: { token, info } } = yield select(state => state.game.getIn(['gameData', payload, 'totalMatches']));

    const socket = yield call(connect);
    const task = yield fork(socketWatch, socket);
    yield put({type: GAME_ACTION.SOCKET_START_GAME});
    const action = yield take(GAME_ACTION.SOCKET_EXIT_GAME);
    yield cancel(task);

    // socket.emit('users.logout', { id: info._id, name: info.name })
}

export default function* watcher() {
    // yield takeLatest(GAME_ACTION.GO_MULTI_PLAYERS, startup)
}
