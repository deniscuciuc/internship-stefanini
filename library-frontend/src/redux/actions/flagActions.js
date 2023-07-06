
export const flagActions = {
    RETRY_REGISTER_PROCESS: "RETRY_REGISTER_PROCESS",
    HIDE_MODAL_SESSION_FINISHED: "HIDE_MODAL_SESSION_FINISHED"
    }

export const retryRegistration = () => (dispatch) => {
    return dispatch({
        type: flagActions.RETRY_REGISTER_PROCESS,
        payload: {},
    })
};
export const hideModalSessionFinished = () => (dispatch) => {
    return dispatch({
        type: flagActions.HIDE_MODAL_SESSION_FINISHED,
        payload: {},
    })
};