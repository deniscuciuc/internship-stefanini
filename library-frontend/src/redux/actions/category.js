import {routes} from "../../config/routes";
import {HttpService} from "../../services/httpService";

export const categoryActions = {
    CATEGORY_LIST : "CATEGORY_LIST",
    GET_CATEGORY_BY_ID :"GET_CATEGORY_BY_ID",
    ASSIGN_BOOK_TO_CATEGORY: "ASSIGN_BOOK_TO_CATEGORY",
    INSERT_CATEGORY: "INSERT_CATEGORY"
};

export const fetchCategoryList = () => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.ALL_CATEGORIES;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : categoryActions.CATEGORY_LIST,
            payload : response
        });
    });
};

export const getCategoryById = (id) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_CATEGORY_BY_ID + id;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : categoryActions.GET_CATEGORY_BY_ID,
            payload : response
        });
    });
};
export const assignBookToACategory = (bookId, categoryId) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.ASSIGN_BOOK_TO_CATEGORY + bookId +"/"+categoryId;

    return HttpService.put(url).then(response => {
        return dispatch({
            type : categoryActions.ASSIGN_BOOK_TO_CATEGORY,
            payload : response
        });
    });
};
export const insertCategory = (categoryData) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.INSERT_CATEGORY;

    return HttpService.post(url, categoryData).then(response => {
        return dispatch({
            type: categoryActions.INSERT_CATEGORY,
            payload: response
        });
    });
};