
import {authorActions} from "../actions/author";
import {loginActions} from "../actions/login";

const initialState = {
    authorList : { },
    authorBookList : { },
    authorData: {},
    numberOfAuthors: 0
};

export const allAuthors = (state = initialState, action) => {
    switch(action.type) {
        case authorActions.GET_ALL_AUTHORS:
            return {
                ...state,
                authorList: action.payload
            }
        case authorActions.GET_PAGINATED_AND_SORTED_AUTHORS:
            return {
                ...state,
                authorList : action.payload
            };
        case authorActions.GET_NUMBER_OF_AUTHORS:
            return {
                ...state,
                numberOfAuthors: action.payload,
            };
        case loginActions.LOGOUT:
        case loginActions.FINISH_SESSION:
            return initialState;
        case authorActions.INSERT_AUTHOR:
            return {
                ...state,
                authorData: action.payload
            };
        default:
            return state;
    }

}