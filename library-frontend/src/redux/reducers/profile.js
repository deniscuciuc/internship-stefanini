import { profileActions } from "../actions/profile"
import {loginActions} from "../actions/login";

const initialState = {
    profileData : { }
}

export const userProfileData = (state = initialState, action) => {
    switch (action.type) {
        case profileActions.GET_PROFILE_DATA:
            return {
                ...state,
                profileData : action.payload
            }
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;
        default:
            return state;    
    }
}