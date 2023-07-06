import {persistReducer} from "redux-persist";
import {combineReducers} from "redux";
import storage from "redux-persist/lib/storage";

import {login} from "./login";
import {user} from "./user";
import {allBooks} from "./allBooks";
import {allAuthors} from "./author";
import {allCategories} from "./category";
import {userProfileData} from './profile';
import {history} from "./history";
import {clientData} from "./client";
import {emailConfirmationToken} from "./emailConfirmation";
import {flag} from "./flagReducers";
import {getNumberOfBooks} from "../actions/book";
import {getNumberOfAuthors} from "../actions/author";
import {getNumberOfUsers} from "../actions/user";


const persistConfig = {
    key: "root",
    storage,
    whitelist: ["login"]

};


const rootReducer = combineReducers({
    login,
    user,
    getNumberOfUsers,
    allBooks,
    getNumberOfBooks,
    allAuthors,
    getNumberOfAuthors,
    allCategories,
    userProfileData,
    history,
    clientData,
    flag,
    emailConfirmationToken
});

export default persistReducer(persistConfig, rootReducer);