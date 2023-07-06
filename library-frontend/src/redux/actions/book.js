import {routes} from "../../config/routes";
import {HttpService} from "../../services/httpService";



export const bookActions = {
    BOOK_LIST : "BOOK_LIST",
    GET_NUMBER_OF_BOOKS: "GET_NUMBER_OF_BOOKS",
    RESERVED_BOOK : "RESERVED_BOOK",
    BOOKS_BY_CRITERIA : "BOOKS_BY_CRITERIA",
    GET_BOOKS_BY_CATEGORY:"GET_BOOKS_BY_CATEGORY",
    GET_BOOKS_BY_AUTHOR :"GET_BOOKS_BY_AUTHOR",
    DELETE_BOOK: "DELETE_BOOK",
    INSERT_BOOK: "INSERT_BOOK",
    GET_USER_BOOKS:"GET_USER_BOOKS",
    GIVE_BOOK : "GIVE_BOOK",
    RETURN_BOOK : "RETURN_BOOK",
    INSERT_BOOK_WITH_EXISTING_CATEGORY_AND_DATA: "INSERT_BOOK_WITH_EXISTING_CATEGORY_AND_DATA"

};

export const fetchBookList = (pageNumber, pageSize, sortBy, sortOrder) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.SORTED_AND_PAGINATED_BOOKS + pageNumber + "/" + pageSize + "/" + sortBy + "/" + sortOrder;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : bookActions.BOOK_LIST,
            payload : response
        });
    });
};

export const getNumberOfBooks = () => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_NUMBER_OF_BOOKS;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: bookActions.GET_NUMBER_OF_BOOKS,
            payload: response,
        })
    })
}

export const reserveTheBook = (bookId, userId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.BOOK_THE_BOOK + "/" + bookId
        + "/" + userId;

    return HttpService.put(url).then(response => {
        return dispatch({
            type: bookActions.RESERVED_BOOK,
            payload: response
        });
    });
}


export const searchBooks = (criteria) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_BOOKS_BY_CRITERIA + "/" + criteria;

    return HttpService.get(url).then(response => {
        return dispatch({
            type: bookActions.BOOKS_BY_CRITERIA,
            payload: response
        });
    });
}

export const getBooksByCategory = ( categoryId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_BOOKS_BY_CATEGORY + "/" + categoryId;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : bookActions.GET_BOOKS_BY_CATEGORY,
            payload : response
        });
    });
};
export const getBooksByAuthor = (authorId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_BOOKS_BY_AUTHOR + "/" + authorId;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : bookActions.GET_BOOKS_BY_AUTHOR,
            payload : response
        });
    });
};

export const deleteBook  = (id) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.DELETE_BOOK + id;

    return HttpService.delete(url).then(response => {
        return dispatch({
            type: bookActions.DELETE_BOOK,
            payload: response
        });
    });
};
export const insertBook  = (bookData) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.INSERT_BOOK ;

    return HttpService.post(url, bookData).then(response => {
        return dispatch({
            type: bookActions.INSERT_BOOK,
            payload: response
        });
    });

};
export const getUserBooks = (userId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GET_USER_BOOKS  + userId;

    return HttpService.get(url).then(response => {
        return dispatch({
            type : bookActions.GET_USER_BOOKS,
            payload : response
        });
    });
};


export const giveTheBook = (bookId, userId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.GIVE_THE_BOOK + "/" + bookId
        + "/" + userId;

    return HttpService.put(url).then(response => {
        return dispatch({
            type : bookActions.GIVE_BOOK,
            payload : response
        });
    });
};

export const returnTheBook = (bookId) => (dispatch) => {

    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.RETURN_THE_BOOK + "/" + bookId

    return HttpService.put(url).then(response => {
        return dispatch({
            type : bookActions.RETURN_BOOK,
            payload : response
        });
    });
};
export const insertBookWithExistingCategoryAndAuthor = (bookData, categoryId, authorId) => (dispatch) => {
    const url = routes.BASIC_URL + routes.BASIC_PATH + routes.INSERT_BOOK_WITH_EXISTING_CATEGORY_AND_DATA + "/" + categoryId + "/" + authorId;

    return HttpService.post(url, bookData).then(response => {
        return dispatch({
            type: bookActions.INSERT_BOOK_WITH_EXISTING_CATEGORY_AND_DATA,
            payload: response
        });
    });

};
