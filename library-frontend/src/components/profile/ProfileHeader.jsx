import profileUserIcon from '../../assets/images/icons/profile/person-circle.svg';
import LogoutComponent from "../logout/Logout";

const ProfileHeaderComponent = (props) => {

    return (
        <>
            <div className="profile-header">
                <div className="profile-header__info align-left">
                    <div className="user-icon">
                        <img src={profileUserIcon} alt="Profile Icon"/>
                    </div>
                    <div className="user-fullname">
                        <h3>{props.userFullName}</h3>
                        <p><small>Your personal account</small></p>
                    </div>
                    <div className="logout">
                        <LogoutComponent/>
                    </div>
                </div>
            </div>
            <div className="profile-header__underline"></div>
        </>
    )
}

export default ProfileHeaderComponent;