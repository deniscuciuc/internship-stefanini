import {routes} from "../../config/routes";
import {HttpService} from "../../services/httpService";
import {bookActions} from "./book";
import {authorActions} from "./author";


export const userActions = {
    USER_LIST: "USER_LIST",
    GET_NUMBER_OF_USERS: "GET_NUMBER_OF_USERS",
    DELETE_USER: "DELETE_USER",
    UPDATE_USER: "UPDATE_USER",
    CREATE_NEW_USER : "CREATE_NEW_USER",
    USERS_BY_CRITERIA : "USERS_BY_CRITERIA",
    UPDATE_PASSWORD: "UPDATE_PASSWORD",
};


export const userList = (pageNumber, pageSize, sortBy, sortOrder) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.SORTED_AND_PAGINATED_USERS + pageNumber + "/" + pageSize + "/" + sortBy + "/" + sortOrder;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: userActions.USER_LIST,
            payload: response
        });
    });
};

export const getNumberOfUsers = () => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_NUMBER_OF_USERS;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: userActions.GET_NUMBER_OF_USERS,
            payload: response,
        })
    })
}

export const deleteUser = (id) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.DELETE_USER + id;

    return HttpService.delete(url).then(response => {
        return dispatch({
            type: userActions.DELETE_USER,
            payload: response
        });
    });
};
export const updateUser = (id, userData) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.UPDATE_USER + id;

    return HttpService.put(url, userData).then(response => {
        return dispatch({
            type: userActions.UPDATE_USER,
            payload: response
        });
    });
};
export const createUser = (userData) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.CREATE_USER;

    return HttpService.post(url, userData).then(response => {
        return dispatch({
            type: userActions.CREATE_NEW_USER,
            payload: response
        });
    });

};
export const searchUsers = (criteria) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_USERS_BY_CRITERIA + "/" + criteria;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: userActions.USERS_BY_CRITERIA,
            payload: response
        });
    });
}
export const updatePassword = (id, userData) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.UPDATE_PASSWORD + id;

    return HttpService.put(url, userData).then(response => {
        return dispatch({
            type: userActions.UPDATE_PASSWORD,
            payload: response
        });
    });

};