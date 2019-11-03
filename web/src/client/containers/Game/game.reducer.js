import Immutable from "immutable";
import * as ActionType from './game.action';

const initialState = Immutable.fromJS({
    gameState: ActionType.GAME_STATE.PENDING,
    gameData: [],
    error: '',
    level: 0,
    timeLimit: 0,
    isLoading: false,
    isMultiPlayer: false,
    needClearCellPicks: false,
});

export default function (state = initialState, {type, payload}) {
    switch (type) {
        case ActionType.LOAD_GAME_DATA:
            return state.set('isLoading', 'true');
        case ActionType.LOAD_GAME_DATA_SUCCESS:
            return state.mergeDeep({
                isLoading: false,
                gameData: Immutable.fromJS(payload),
                timeLimit: payload[0].timeLeft,
            });
        case ActionType.SET_GAME_STATE:
            return state.set('gameState', payload);
        case ActionType.SET_GAME_LEVEL_SUCCESS:
            return state.set('level', payload);
        case ActionType.DEDUCT_TIME:
            return state.setIn(['gameData', payload.level, 'timeLeft'], state.getIn(['gameData', payload.level, 'timeLeft']) - payload.sec);
        case ActionType.INCREASE_MATCH_COUNT_SUCCESS:
            return state.setIn(['gameData', payload, 'totalMatches'], state.getIn(['gameData', payload, 'totalMatches']) + 1);
        case ActionType.SET_CELL_MATCH:
            return state.setIn(['gameData', payload.level, 'data', payload.cid, 'isMatch'], payload.data);
        case ActionType.SET_CELL_FLIP:
            return state.setIn(['gameData', payload.level, 'data', payload.cid, 'isFlipped'], payload.data);
        case ActionType.CLEAR_CELL_PICKS:
            return state.set('needClearCellPicks', payload);
        case ActionType.GAME_ERROR:
            return state.merge({
                isLoading: false,
                error: payload,
                gameState: ActionType.GAME_STATE.ERROR,
            });
        case ActionType.EXIT:
            return initialState;
        default:
            return state;
    }
}