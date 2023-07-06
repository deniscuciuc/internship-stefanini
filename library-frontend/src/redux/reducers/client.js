import {clientActions} from "../actions/client";
import {loginActions} from "../actions/login";

const initialState = {
    client: {}
}

export const clientData = (state = initialState, action) => {
    switch (action.type) {
        case clientActions.GET_CLIENT_DATA:
            return {
                ...state,
                client: action.payload
            }
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;
        default:
            return state;
    }
}
