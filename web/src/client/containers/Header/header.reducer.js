import Immutable from 'immutable';
import * as ActionType from './header.action';

const initialState = Immutable.fromJS({
    topics: [],
});

const reducer = (state = initialState, {type, payload}) => {
    switch (type) {
        case ActionType.GET_TOPICS:
            return state.set('isLoading', true);
        case ActionType.LOAD_TOPICS:
            return state.set('topics', payload);
        default:
            return state;
     }
  };

  export default reducer;