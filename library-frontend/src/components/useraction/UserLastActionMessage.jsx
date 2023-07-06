import '../../assets/styles/last-action.css';
import {useDispatch, useSelector} from "react-redux";
import {getLastUserAction} from "../../redux/selectors/login";
import {useEffect, useState} from "react";
import {setLastUserAction} from "../../redux/actions/login";


const UserLastActionMessageComponent = () => {
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
        <>
            {
                displayAction ?
                        <div className="last-action-top-left__message"  >
                            <p>{lastAction}</p>
                        </div>
                    :
                    <></>
            }
        </>
    );
}

export default UserLastActionMessageComponent;