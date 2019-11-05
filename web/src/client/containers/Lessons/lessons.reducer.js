import Immutable from 'immutable';
import * as ActionType from './lessons.action';

const initialState = Immutable.fromJS({
    lessons: [],
    isLoading: false,
});

const reducer = (state = initialState, {type, payload}) => {
    switch (type) {
        case ActionType.LOAD_LESSONS:
            return state.set('isLoading', true);
        case ActionType.LOAD_LESSONS_SUCCESS:
            return state.merge({
                lessons: payload,
                isLoading: false,
            });
        default:
            return state;
     }
  };

  export default reducer;