import {categoryActions} from "../actions/category";
import {loginActions} from "../actions/login";


const initialState = {
    categoryList : { },
    categoryData : { }
};

export const allCategories = (state = initialState, action) => {
    switch(action.type) {
        case categoryActions.CATEGORY_LIST:
            return {
                ...state,
                categoryList : action.payload
            };
        case categoryActions.GET_CATEGORY_BY_ID:
            return {
                ...state,
                categoryData : action.payload
            };
        case categoryActions.ASSIGN_BOOK_TO_CATEGORY:
            return {
                ...state,
                categoryData : action.payload
            };
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;
        case categoryActions.INSERT_CATEGORY:
            return {
                ...state,
                categoryData: action.payload
            };
        default:
            return state;
    }

}