import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router'

import login from '../containers/Login/login.reducer';
const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    authentication: combineReducers({
        login
    }),
});

export default createRootReducer;
