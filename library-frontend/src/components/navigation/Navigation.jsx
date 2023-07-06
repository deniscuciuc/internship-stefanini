import React, {useEffect, useState} from "react";
import "../../assets/styles/navigation.css";
import {NavLink} from "react-router-dom";
import {useSelector} from "react-redux";
import {getUserData} from "../../redux/selectors/login";

const NavigationComponent = () => {
    const userData = useSelector(getUserData)
    const [logged, setLogged] = useState(false);

    useEffect(() => {
        if (userData.email) {
            setLogged(true);
        }
    }, [userData.email]);

    return (
        <header>
            <div className="nav">
                <div className="nav-link">
                    <NavLink to={"/"}>Home</NavLink>
                </div>
                <div className="nav-link">
                    <NavLink to={"/categories"}>Categories</NavLink>
                </div>
                <div className="nav-link">
                    <NavLink to={"/authors"}>Authors</NavLink>
                </div>
                <div className="nav-link">
                    {<NavLink to={"/books"}>Books</NavLink>}
                </div>


                {
                    logged ?
                        <div className="nav-link">
                            <NavLink to={"/profile"}>Profile</NavLink>
                        </div>
                        :
                        <div className="nav-link auth-link">
                            <NavLink to={"/login"}>Login /</NavLink>
                            <NavLink to={"/registration"}>Sign-Up</NavLink>
                        </div>
                }


            </div>
            <div className="line-horizontal-xxxl"></div>
        </header>
    );
};

export default NavigationComponent;