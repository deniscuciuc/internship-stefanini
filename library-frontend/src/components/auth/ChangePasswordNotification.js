import {useDispatch, useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";
import React, {useEffect, useState} from "react";
import {Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {changePassword} from "../../redux/actions/login";
import validatePassword from "../../util/passwordValid";


const ChangePasswordNotification = ({children}) => {
    const userInfo = useSelector(getUserData);
    const [password, setPassword] = useState('');
    const [confirmedPassword, setConfirmedPassword] = useState('');
    const [errors, setErrors] = useState({});
    const [showpopup, setShowpopup] = useState(false);
    const dispatch = useDispatch();
    const handleShowPopUp = () => {
        setShowpopup(true)
    };
    const handleHidePopUp = () => {
        setShowpopup(false)
    };

    useEffect(() => {
        if (userInfo.hasTemporaryPassword) {
            handleShowPopUp()
        }
    }, [])


    const handleOnChangeValidating = () => {
        setErrors((validatePassword({password, confirmedPassword})));
    }
    useEffect(() => {
        handleOnChangeValidating();
    }, [password, confirmedPassword])

    const changeTemporaryPassword = () => {
        const newUserData = {
            'email' : userInfo.email,
            'password': password
        };
        dispatch(changePassword(userInfo.id, newUserData))
        console.log(newUserData)
    }

    return (
        <>
            <div>
                {children}
            </div>

            <Modal show={showpopup}>
                <Modal.Header>
                    <Modal.Title>You've been signed in with a temporary password </Modal.Title>
                </Modal.Header>
                <Modal.Body>Please change your password to continue</Modal.Body>
                <section className="form__password">
                    <input id="password"
                           name="password"
                           type="password"
                           placeholder="Password"
                           aria-describedby="password-constraints"
                           onChange={event => {
                               setPassword(event.target.value);
                               handleOnChangeValidating();
                           }}
                           onFocus={handleOnChangeValidating}
                    />
                    {errors.password && <p className="error-message">{errors.password}<i>*</i></p>}
                </section>
                <section className="form__repeat-password">
                    <input id="confirmed-password"
                           name="confirmedPassword"
                           type="password"
                           placeholder="Confirm your password"
                           onChange={event => {
                               setConfirmedPassword(event.target.value);
                               handleOnChangeValidating();
                           }}
                           onFocus={handleOnChangeValidating}
                    />
                    {errors.confirmedPassword &&
                        <p className="error-message">{errors.confirmedPassword}<i>*</i></p>}
                </section>
                <Modal.Footer>
                    <Button variant="primary" disabled={errors} onClick={() => {
                        changeTemporaryPassword();
                        handleHidePopUp()
                    }}>Save new password</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default ChangePasswordNotification;