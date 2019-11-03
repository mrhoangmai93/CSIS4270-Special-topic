import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router'

import login from '../containers/Login/login.reducer';
import register from '../containers/Register/register.reducer';
import game from '../containers/Game/game.reducer';
import header from '../containers/Header/header.reducer';

const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    authentication: combineReducers({
        login,
        register
    }),
    game,
    header

});

export default createRootReducer;
