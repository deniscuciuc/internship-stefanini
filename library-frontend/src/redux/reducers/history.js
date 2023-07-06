import {historyActions} from "../actions/history";
import {loginActions} from "../actions/login";


const initialState = {
    historyList: {},
};

export const history = (state = initialState, action) => {
    switch (action.type) {
        case historyActions.GET_USER_HISTORY:
            return {
                ...state,
                historyList: action.payload
            };
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;

        default:
            return state;
    }

}