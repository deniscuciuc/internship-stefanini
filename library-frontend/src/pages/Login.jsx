import '../assets/styles/login.css';
import LoginFormComponent from "../components/login/LoginForm";
import SideContentComponent from "../components/login/SideContent";
import {useEffect, useState} from "react";
import {setLastUserAction} from "../redux/actions/login";
import {useDispatch, useSelector} from "react-redux";
import {getLastUserAction} from "../redux/selectors/login";
import '../assets/styles/last-action.css';


const LoginPage = () => {
    const lastAction = useSelector(getLastUserAction);
    const dispatch = useDispatch();
    const [displayAction, setDisplayAction] = useState(false);

    useEffect(() => {
        if (JSON.stringify(lastAction) !== JSON.stringify('')) {
            setDisplayAction(true);
            setTimeout(() => {
                setDisplayAction(false);
                dispatch(setLastUserAction(''));
            }, 3000)
        }
    }, [lastAction])


    return (
        <div className="login-page">
            <SideContentComponent />
            <LoginFormComponent />
            {
                displayAction ?
                    <div className="last-action-top-left__message_top">
                        <p>{lastAction}</p>
                    </div>
                    :
                    <></>
            }
        </div>
    )
}

export default LoginPage;