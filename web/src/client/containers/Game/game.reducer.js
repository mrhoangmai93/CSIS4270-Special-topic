import Immutable from "immutable";
import * as ActionType from './game.action';

export const GameState = {
    PENDING: 'PENDING',
    WAITING: 'WAITING',
    IN_PROGRESS: 'IN_PROGRESS',
    FINISHED: 'FINISHED',
};

const initialState = Immutable.fromJS({
    gameState: GameState.PENDING,
    error: '',
});

export default function (state = initialState, {type, payload}) {
    switch (type) {
        default:
            return state;
    }
}