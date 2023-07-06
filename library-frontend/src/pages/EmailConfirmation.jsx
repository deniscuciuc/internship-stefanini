import '../assets/styles/email-confirmation.css';
import NavigationComponent from "../components/navigation/Navigation";
import {PulseLoader} from "react-spinners";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {getConfirmationToken} from "../redux/selectors/emailConfirmation";
import {useParams} from "react-router-dom";
import {confirmEmailByToken} from "../redux/actions/emailConfirmation";
import SuccessfullyEmailConfirmationComponent from "../components/confirmation/SuccessfullyEmailConfirmation";
import FailedEmailConfirmationComponent from "../components/confirmation/FailedEmailConfirmation";
import {getUserData} from "../redux/selectors/login";


const EmailConfirmationPage = () => {
    const [loaded, setLoaded] = useState(false);
    const emailConfirmationToken = useSelector(getConfirmationToken);
    const dispatch = useDispatch();
    const params = useParams();


    useEffect(() => {
        dispatch(confirmEmailByToken(params.token)).then(() => {
            setLoaded(true);
        })
    }, [])

    return (
        <>
            {
                loaded ?
                    <>
                        <NavigationComponent/>
                        <div>
                            {
                                emailConfirmationToken === 'CONFIRMED' || emailConfirmationToken === 'WAS_ALREADY_CONFIRMED'
                                    ?
                                    <>
                                        <SuccessfullyEmailConfirmationComponent/>
                                    </>
                                    :
                                    <>
                                        <FailedEmailConfirmationComponent/>
                                    </>
                            }
                        </div>
                    </>
                    :
                    <PulseLoader cssOverride={{
                        textAlign: "center",
                        paddingTop: "20%"
                    }} size={25}/>
            }
        </>
    )
}
export default EmailConfirmationPage;