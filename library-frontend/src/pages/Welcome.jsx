import '../assets/styles/welcome.css';
import React, {useEffect, useState} from "react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getUserData} from "../redux/selectors/login";
import {getUserProfileData} from "../redux/selectors/profile";
import {profileData} from "../redux/actions/profile";
import NavigationComponent from "../components/navigation/Navigation";
import {ClipLoader, PulseLoader} from "react-spinners";
import UserLastActionMessageComponent from "../components/useraction/UserLastActionMessage";


const WelcomePage = () => {
    const [loaded, setLoaded] = useState(false);
    const [spinnerLoaded, setSpinnerLoaded] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";
    const userAccountData = useSelector(getUserData);
    const userProfileData = useSelector(getUserProfileData);
    const dispatch = useDispatch();

    const handleOnClick = () => {
        setSpinnerLoaded(true);
        setTimeout(() => {
            setSpinnerLoaded(false);
            navigate(from, {replace: true});
        }, 1000)
    }

    useEffect(() => {
        dispatch(profileData(userAccountData.id)).then(() => {
            setLoaded(true);
        });
    }, [userAccountData])

    return (
        <>
            {
                loaded ?
                    <>
                        <NavigationComponent/>
                        <div className="welcome-page">
                            <div className="welcome-page__container">
                                <div className="welcome-page__message">
                                    <h1>Hi {userProfileData.firstName}!</h1>
                                    <h2>Welcome to Stefanini Library</h2>
                                    <p>You are now successfully registered in our library. You will be able to use the
                                        functions of the site if you click <Link to={"/"}>here</Link> or the button
                                        below the text or click one of the
                                        buttons in the top menu, but before that we will tell you that <strong>your
                                            email was
                                            sent with a link</strong> to complete registration and confirm your
                                        email.<br/> <br/>
                                        Thank you for being with us!</p>
                                </div>
                                <div className="welcome-page__explore-button">
                                    <button onClick={handleOnClick}>
                                        {
                                            spinnerLoaded ?
                                                <ClipLoader
                                                    color="black"
                                                    size={30}
                                                    speedMultiplier={0.6}
                                                />
                                                :
                                                <>Explore Stefanini Library</>
                                        }
                                    </button>
                                </div>
                            </div>
                        </div>
                        <UserLastActionMessageComponent/>
                    </>
                    :
                    <>
                        <PulseLoader cssOverride={{
                            textAlign: "center",
                            paddingTop: "20%"
                        }} size={25}/>
                    </>
            }
        </>
    );
}

export default WelcomePage;