import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";
import {getConfirmationToken} from "../../redux/selectors/emailConfirmation";
import {ClipLoader} from "react-spinners";

const SuccessfullyEmailConfirmationComponent = () => {
    const navigate = useNavigate();
    const [userLoggedIn, setUserLoggedIn] = useState(true);
    const [spinnerLoaded, setSpinnerLoaded] = useState(false);
    const userData = useSelector(getUserData);
    const confirmationToken = useSelector(getConfirmationToken);

    useEffect(() => {
        if (userData.email) {
            setUserLoggedIn(true);
        } else {
            setUserLoggedIn(false);
        }
    }, [userData.email]);

    return (
        <div className="email-confirmation-page">
            <div className="email-confirmation-page__message">
                {
                    confirmationToken === 'WAS_ALREADY_CONFIRMED' ?
                        <>
                            <h1>Email was already confirmed</h1>
                            <p>Apparently you tried the same activation link twice. You are already a full-featured user
                                of our library. And have the possibility to reserve books, as well as without any
                                problems to use the password retrieval functionality.
                            </p>
                        </>
                        :
                        <>
                            <h2>Congratulations!</h2>
                            <h3>Your email now is confirmed</h3>
                            <p>You are now a full-featured user of our library. By confirming your email you have opened
                                yourself
                                the possibility to reserve books, as well as without any problems to use the password
                                retrieval
                                functionality.
                            </p>
                        </>
                }
                {
                    userLoggedIn ?
                        <>
                            <p>Now you can continue to use the site if you click <Link to={"/"}>here</Link> or follow
                                the
                                button below</p>
                            <div className="email-confirmation-page__button">
                                <button onClick={() => {
                                    setSpinnerLoaded(true);
                                    setTimeout(() => {
                                        setSpinnerLoaded(false);
                                        navigate("/");
                                    }, 1000)
                                }}>
                                    {
                                        spinnerLoaded ?
                                            <ClipLoader
                                                color="black"
                                                size={30}
                                                speedMultiplier={0.6}
                                            />
                                            :
                                            <>Continue to explore</>
                                    }
                                </button>
                            </div>
                        </>
                        :
                        <>
                            <p>We noticed that you are not yet logged in. You can do that by clicking here or by using
                                the
                                button below or the link on the top menu.</p>
                            <div className="email-confirmation-page__button">
                                <button onClick={() => {
                                    setSpinnerLoaded(true);
                                    setTimeout(() => {
                                        setSpinnerLoaded(false);
                                        navigate("/login");
                                    }, 1000)
                                }}>
                                    {
                                        spinnerLoaded ?
                                            <ClipLoader
                                                color="black"
                                                size={30}
                                                speedMultiplier={0.6}
                                            />
                                            :
                                            <>Login</>
                                    }
                                </button>
                            </div>
                        </>
                }
            </div>
        </div>
    );
}

export default SuccessfullyEmailConfirmationComponent;