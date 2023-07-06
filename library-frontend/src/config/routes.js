export const routes = {
    BASIC_URL: process.env.REACT_APP_API_BASE_URL,
    BASIC_PATH: "api/",
    LOGIN_URL: "login",
    REGISTRATION_URL: "sign-up",
    REFRESH_TOKEN: "token/refresh",
    PROFILE_DATA: "profile/get/",
    DELETE_USER: "user/delete/",
    UPDATE_USER: "user/update/",
    CREATE_USER : "user/create",
    SORTED_AND_PAGINATED_USERS: "user/users/",
    SORTED_AND_PAGINATED_BOOKS: "book/books/",
    SORTED_AND_PAGINATED_AUTHORS: "author/authors/",
    ALL_AUTHORS: "author/all-authors",
    ALL_CATEGORIES: "category/categories",
    GET_NUMBER_OF_USERS: "user/numberOf",
    GET_NUMBER_OF_BOOKS: "book/numberOf",
    GET_NUMBER_OF_AUTHORS: "author/numberOf",
    BOOK_THE_BOOK: "book/bookTheBook",
    GET_BOOKS_BY_CRITERIA: "book/find_books_by_criteria",
    GET_BOOKS_BY_CATEGORY: "book/bookByCategory",
    GET_BOOKS_BY_AUTHOR: "book/bookByAuthor",
    DELETE_BOOK: "book/delete/",
    INSERT_BOOK: "book/create",
    GET_CATEGORY_BY_ID: "category/get/",
    ASSIGN_BOOK_TO_CATEGORY: "category/assignBook/",
    GET_USER_BOOKS: "user/usersBooks/",
    GET_USER_HISTORY: "user/usersHistory/",
    GIVE_THE_BOOK: "book/giveTheBook",
    RETURN_THE_BOOK: "book/returnTheBook",
    CLIENT_DATA: "user/find-by-email/",
    CONFIRM_EMAIL_BY_TOKEN: "email-confirmation/confirm/",
    CHANGE_PASSWORD : "user/change-password/",
    FORGOT_PASSWORD: "user/forgotPassword",
    GET_USERS_BY_CRITERIA: "user/find_users_by_criteria",
    UPDATE_PASSWORD: "user/forgotPassword/changePassword/",
    SEND_NEW_CONFIRMATION_TOKEN: "email-confirmation/send-new-token/",
    INSERT_BOOK_WITH_EXISTING_CATEGORY_AND_DATA: "book/addBookWithExistingCategoryAndAuthor",
    INSERT_AUTHOR: "author/create",
    INSERT_CATEGORY: "category/create",

}