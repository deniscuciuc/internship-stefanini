
import {loginActions} from "../actions/login";
import {flagActions} from "../actions/flagActions";


const initialState = {
    showModalSessionFinished: false,
    processFinished: false,

}

export const flag = (state = initialState, action) => {
    switch (action.type) {
        case loginActions.FINISH_SESSION:
            return {
                ...state,
                showModalSessionFinished: true,
            };
        case flagActions.HIDE_MODAL_SESSION_FINISHED:
            return {
                ...state,
                showModalSessionFinished: false,
            };
            case loginActions.RECEIVE_USER_REGISTER:
                        return {
                            ...state,
                            processFinished: true,
                        };
            case flagActions.RETRY_REGISTER_PROCESS:
                  return {
                      ...state,
                       processFinished: false,
                        };
        default:
            return state;
    }
}