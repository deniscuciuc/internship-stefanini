import { routes } from "../../config/routes";
import { HttpService } from "../../services/httpService";

export const profileActions = {
    GET_PROFILE_DATA : "GET_PROFILE_DATA"
}

export const profileData = (id) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.PROFILE_DATA + id;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: profileActions.GET_PROFILE_DATA,
            payload: response
        })
    })
}