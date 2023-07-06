import {useLocation, Navigate, Outlet} from "react-router-dom";
import {useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";


const RequireAuth = ({allowedRoles}) => {
    const location = useLocation();
    const userInfo = useSelector(getUserData);

    return (
        userInfo?.roles?.find(role => allowedRoles?.includes(role))
            ? <Outlet/>
            : userInfo
                ? <Navigate to="/unauthorized" state={{from: location}} replace/>
                : <Navigate to="/login" state={{from: location}} replace/>
    );
}

export default RequireAuth;