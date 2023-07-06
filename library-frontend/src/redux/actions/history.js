import {routes} from "../../config/routes";
import {HttpService} from "../../services/httpService";



export const historyActions = {

    GET_USER_HISTORY:"GET_USER_HISTORY",
};

export const getUserHistory = (userId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_USER_HISTORY  + userId;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : historyActions.GET_USER_HISTORY,
            payload : response
        });
    });
};
