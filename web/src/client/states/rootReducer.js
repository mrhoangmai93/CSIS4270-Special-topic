import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router'

import login from '../containers/Login/login.reducer';
import register from '../containers/Register/register.reducer';

const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    authentication: combineReducers({
        login,
        register
    }),
});

export default createRootReducer;
