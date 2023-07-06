import {routes} from "../../config/routes";
import {HttpService} from "../../services/httpService";

export const authorActions = {
    GET_ALL_AUTHORS: "GET_ALL_AUTHORS",
    GET_PAGINATED_AND_SORTED_AUTHORS: "GET_PAGINATED_AND_SORTED_AUTHORS",
    INSERT_AUTHOR: "INSERT_AUTHOR",
    GET_NUMBER_OF_AUTHORS: "GET_NUMBER_OF_AUTHORS"
};

export const fetchAuthorList = (pageNumber, pageSize, sortBy, sortOrder) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.SORTED_AND_PAGINATED_AUTHORS + pageNumber + "/" + pageSize + "/" + sortBy + "/" + sortOrder;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: authorActions.GET_PAGINATED_AND_SORTED_AUTHORS,
            payload: response
        });
    });
};

export const fetchAllAuthors = () => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.ALL_AUTHORS;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: authorActions.GET_ALL_AUTHORS,
            payload: response
        });
    });
};

export const getNumberOfAuthors = () => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_NUMBER_OF_AUTHORS;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: authorActions.GET_NUMBER_OF_AUTHORS,
            payload: response,
        })
    })
}

export const insertAuthor = (authorData) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.INSERT_AUTHOR;

    return HttpService.post(url, authorData).then(response => {
        return dispatch({
            type: authorActions.INSERT_AUTHOR,
            payload: response
        });
    });

};