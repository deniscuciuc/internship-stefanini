import { userActions } from "../actions/user";
import {loginActions} from "../actions/login";


const initialState = {
    userList : { },
    newUser: { },
    updatedUserData : { },
    numberOfUsers: 0
};

export const user = (state = initialState, action) => {
    switch(action.type) {
        case userActions.USER_LIST:
            return {
                ...state,
                userList : action.payload
            };
        case userActions.GET_NUMBER_OF_USERS:
            return {
                ...state,
                numberOfUsers: action.payload
            };
        case userActions.DELETE_USER:
            return {
                ...state,
            };
        case userActions.UPDATE_USER:
            return {
                ...state,
                updatedUserData: action.payload
            };
        case userActions.USERS_BY_CRITERIA:
            return {
                ...state,
               userList: action.payload
            };
        case userActions.UPDATE_PASSWORD:
            return {
                ...state,
            };
        case userActions.CREATE_NEW_USER:
            return {
                ...state,
                newUser: action.payload
            };
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;
        default:
            return state;
    }
}