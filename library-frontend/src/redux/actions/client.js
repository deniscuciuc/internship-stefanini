import {routes} from "../../config/routes";
import {HttpService} from "../../services/httpService";


export const clientActions = {
    GET_CLIENT_DATA : "GET_CLIENT_DATA",
}

export const searchClientData = (email) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.CLIENT_DATA + email;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: clientActions.GET_CLIENT_DATA,
            payload: response
        })
    })
}
