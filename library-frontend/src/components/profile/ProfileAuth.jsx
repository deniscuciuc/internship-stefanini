import {useDispatch, useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";


const ProfileAuthComponent = (props) => {
    const userData = useSelector(getUserData);
    const dispatch = useDispatch();

    return (
        <>
            <div className="auth-email-password">
                <h4>Email: <strong>{userData.email}</strong></h4>
                <div className="reset-password-button">

                </div>
            </div>
        </>
    )
}

export default ProfileAuthComponent;