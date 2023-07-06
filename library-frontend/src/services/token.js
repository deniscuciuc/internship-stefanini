import {store} from "../store";
import {routes} from "../config/routes";
import {finishSession, receiveRefreshToken} from "../redux/actions/login";


export function checkIfTokenValid(token) {
    if (token) {
        let parsedToken = parseJwt(token);
        return parsedToken.exp * 1000 > new Date().getTime() + 1000;
    }
}

const parseJwt = (token) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};

export async function getToken() {
    const state = store.getState();
    const {userData} = state.login;
    const token = userData?.access_token;
    const refresh_token = userData?.refresh_token;
    if (!checkIfTokenValid(token)) {
        if (checkIfTokenValid(refresh_token)) {
            const config = {
                body: undefined,
                headers: {},
                method: "GET",
                credentials: "same-origin"
            }

            const HEADERS = {
                "Content-Type": "application/json",
                "Accept": "application/json"
            };
            HEADERS[`Authorization`] = 'Bearer ' + refresh_token;
            config.headers = HEADERS;
            const response = await fetch(routes.BASIC_URL + routes.BASIC_PATH + routes.REFRESH_TOKEN, config);
            if (!response.ok) {
                store.dispatch(finishSession());
                return "";
            }
            const result = await response.json();
            store.dispatch(receiveRefreshToken(result));
            return result.access_token;
        } else {
            store.dispatch(finishSession())
            return "";
        }
    } else {
        return token;
    }
}

