import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { profileData } from "../redux/actions/profile";
import { getUserData } from "../redux/selectors/login";
import { getUserProfileData } from "../redux/selectors/profile";
import NavigationComponent from "../components/navigation/Navigation";
import HeaderProfileComponent from "../components/profile/ProfileHeader";
import { PulseLoader } from "react-spinners";
import '../assets/styles/profile.css';
import ProfileSideMenuComponent from "../components/profile/ProfileSideMenu";
import UserLastActionMessageComponent from "../components/useraction/UserLastActionMessage";



const ProfilePage = (props) => {

    const [loaded, setLoaded] = useState(false);
    const dispatch = useDispatch();

    const userAccountData = useSelector(getUserData);
    const userProfileData = useSelector(getUserProfileData);

    const userFullName = userProfileData.firstName + " " + userProfileData.lastName;

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
                    <NavigationComponent />
                    <UserLastActionMessageComponent/>
                    <div className="profile-page">
                        <HeaderProfileComponent userFullName = {userFullName} />
                        <ProfileSideMenuComponent rightSideComponent = {props.renderComponent}/>
                    </div>

                </>
                : 
                <>
                    <PulseLoader cssOverride={{
                        textAlign: "center",
                        paddingTop: "20%"
                    }} size={25} />
                </>
            }
        </>
    )
}

export default ProfilePage;