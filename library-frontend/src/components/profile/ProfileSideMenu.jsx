import profileIcon from '../../assets/images/icons/profile/person.svg';
import bookIcon from '../../assets/images/icons/profile/book.svg';
import historyIcon from '../../assets/images/icons/profile/clock-history.svg';
import authIcon from '../../assets/images/icons/profile/shield-lock.svg';
import adminIcon from '../../assets/images/icons/profile/bug.svg';
import {useNavigate} from 'react-router-dom';
import {useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";
import {useEffect, useState} from "react";


const ProfileSideMenuComponent = (props) => {
    const navigate = useNavigate();
    const userData = useSelector(getUserData)
    const allowedRoles = ['ADMIN', 'LIBRARIAN'];
    const [admin, setAdmin] = useState(false);

    useEffect(() => {
        if (userData?.roles?.find(role => allowedRoles?.includes(role))) {
            setAdmin(true);
        }
    }, []);

    return (
        <div className="profile-menu-content-flex">
            <div className="profile-side-menu">
                <div className="profile-side-menu__user">
                    <div className="profile" onClick={() => navigate("/profile")}>
                        <img src={profileIcon} alt="Book Icon"/>
                        <h5>Profile</h5>
                    </div>
                    <div className="books" onClick={() => navigate("/profile/mybooks")}>
                        <img src={bookIcon} alt="Book Icon"/>
                        <h5>My Books</h5>
                    </div>
                    <div className="history" onClick={() => navigate("/profile/myhistory")}>
                        <img src={historyIcon} alt="History Icon"/>
                        <h5>My History</h5>
                    </div>
                </div>
                <div className="profile-side-menu__break-horizontal-line "></div>
                <div className="profile-side-menu__access">
                    <div className="section-name">
                        <h6>Access</h6>
                    </div>
                    <div className="authorization" onClick={() => navigate("/profile/authorization")}>
                        <img src={authIcon} alt="Auth Icon"/>
                        <h5>Authorization</h5>
                    </div>
                </div>
                {
                    admin ?
                    <>
                        <div className="profile-side-menu__break-horizontal-line "></div>
                        <div className="profile-side-menu__administration">
                            <div className="section-name">
                                <h6>Administration</h6>
                            </div>
                            <div className="admin-panel" onClick={() => navigate("/profile/admin")}>
                                <img src={adminIcon} alt="Admin Icon"/>
                                <h5>Admin Panel</h5>
                            </div>
                        </div>
                    </>
                    : <></>
                }


            </div>

            <div className="profile-side-content">
                {props.rightSideComponent}
            </div>
        </div>
    )
}


export default ProfileSideMenuComponent;