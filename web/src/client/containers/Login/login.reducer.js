import Immutable from "immutable";


const initialState = Immutable.fromJS({
    isLoading: false,
    isAuthenticated: false,
});

export default function(state = initialState, {type, payload}) {
    switch (type) {
        default:
            return state;
    }
}