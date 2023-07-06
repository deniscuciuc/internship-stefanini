import {loginActions} from "../actions/login";
import {emailConfirmationActions} from "../actions/emailConfirmation";


const initialState = {
    forgotPasswordUserData :{},
    userData: {},
    tokenValid: true,
    lastUserAction: '',
};

export const login = (state = initialState, action) => {
    switch (action.type) {
        case loginActions.RECEIVE_USER_AUTH:
            return {
                ...state,
                userData: action.payload
            };
        case loginActions.RECEIVE_REFRESH_TOKEN:
            return {
                ...state,
                userData: {
                    id: state.userData.id,
                    email: state.userData.email,
                    access_token: action.payload.access_token,
                    refresh_token: action.payload.refresh_token,
                    roles: state.userData.roles,
                    confirmedByEmail: state.userData.confirmedByEmail
                },
                tokenValid: true
            };
        case loginActions.CHECK_ACCESS_TOKEN:
            return {
                ...state,
                tokenValid: action.payload
            };
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;
        case loginActions.RECEIVE_USER_REGISTER:
            return {
                ...state,
                userData: action.payload
            };
        case loginActions.CHANGE_USER_PASSWORD:
            return {
                ...state,
                userData: {
                    id: state.userData.id,
                    email: state.userData.email,
                    access_token: state.userData.access_token,
                    refresh_token: state.userData.refresh_token,
                    confirmedByEmail: state.userData.confirmedByEmail,
                    roles: state.userData.roles,
                    hasTemporaryPassword: false
                },
            };
        case loginActions.FORGOT_PASSWORD:
            return {
                ...state,
                forgotPasswordUserData: action.payload,
            };
        case loginActions.SET_LAST_USER_ACTION:
            return {
                ...state,
                lastUserAction: action.payload,
            }
        case emailConfirmationActions.CONFIRM_EMAIL:
            return {
                ...state,
                userData: {
                    id: state.userData.id,
                    email: state.userData.email,
                    access_token: state.userData.access_token,
                    refresh_token: state.userData.refresh_token,
                    confirmedByEmail: true,
                    roles: state.userData.roles,
                    hasTemporaryPassword: state.userData.hasTemporaryPassword,
                },
            }
        default:
            return state;
    }

}