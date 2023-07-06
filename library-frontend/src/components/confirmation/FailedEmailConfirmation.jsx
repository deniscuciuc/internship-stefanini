import {useLocation, useNavigate, useParams} from "react-router-dom";
import React, {useState} from "react";
import {ClipLoader} from "react-spinners";
import {sendNewConfirmationToken} from "../../redux/actions/emailConfirmation";
import {useDispatch, useSelector} from "react-redux";
import {getConfirmationToken} from "../../redux/selectors/emailConfirmation";
import {setLastUserAction} from "../../redux/actions/login";

const FailedEmailConfirmationComponent = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const dispatch = useDispatch();
    const params = useParams();
    const from = location.state?.from?.pathname || "/";
    const [spinnerLoaded, setSpinnerLoaded] = useState(false);
    const confirmationToken = useSelector(getConfirmationToken);

    const handleOnClick = () => {
        setSpinnerLoaded(true);
        setTimeout(() => {
            dispatch(sendNewConfirmationToken(params.token)).then(() => {
                if (confirmationToken === 'CONFIRMED') {
                    dispatch(setLastUserAction("Email was sent with a new link"));
                } else {
                    dispatch(setLastUserAction("Confirmation token not found"));
                }
                setTimeout(() => {
                    setSpinnerLoaded(false);
                    navigate(from, {replace: true});
                }, 100)
            })
        }, 1000)
    }

    return (
        <div className="email-confirmation-page">
            <div className="email-confirmation-page__message">
                <h1>SOMETHING WENT WRONG</h1>
                <p>
                    Something went wrong and we were unable to confirm your email. The link may have expired or may not
                    exist. Try to reconfirm your email by clicking the button below which will send you a new link and
                    take you back to the home page.
                </p>
                <div className="email-confirmation-page__button">
                    <button onClick={handleOnClick}>
                        {
                            spinnerLoaded ?
                                <ClipLoader
                                    color="black"
                                    size={30}
                                    speedMultiplier={0.6}
                                />
                                :
                                <>Try again</>
                        }
                    </button>
                </div>
            </div>
        </div>
    )
}

export default FailedEmailConfirmationComponent;