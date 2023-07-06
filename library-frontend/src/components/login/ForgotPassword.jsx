import Form from "react-bootstrap/Form";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {forgotPassword, loginUser} from "../../redux/actions/login";
import React from "react";
import passwordIcon from "../../assets/images/icons/profile/password.svg";
import {getForgotPasswordUserData} from "../../redux/selectors/login";

const ForgotPasswordComponent = () => {

    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const userInfo = useSelector(getForgotPasswordUserData);


    const dispatch = useDispatch();

    const handleSubmit = (event) => {
        event.preventDefault();
        verifyError();

        dispatch(forgotPassword(email))
    }

    const verifyError = () => {
        if (userInfo === 403 ) {
            setMessage("No user with this email!");
        } else if (userInfo.email === email) {
            setMessage("Email sent to " + email + "!");
        }

        if(!userInfo){
            setMessage("");
        }
    }

    useEffect(() => {
        verifyError();
    }, [userInfo])


    return (<div className="page">
        <div className="header-with-img">
            <img src={passwordIcon} alt="Password Icon"/>
            <h1>Forgot password?</h1>
        </div>
        <div className="page__horizontal-line"></div>
        <br/>
        <h5>Find your account</h5>
        <p>
            Please enter your email, and we will send you a link
            You can access your email and click on the link to reset your password
        </p>

        <Form.Group className="mb-3" controlId="email">
            <Form.Label>Email</Form.Label>
            <Form.Control type="text" placeholder="email"
                          onChange={event => setEmail(event.target.value)}/>
        </Form.Group>
        <div className="error-message">
                <p>{message}</p>
        </div>

        <div className="login-form__login-btn">
            <button type="button" onClick={handleSubmit}>
                Send link
            </button>
        </div>


    </div>)

}
export default ForgotPasswordComponent;